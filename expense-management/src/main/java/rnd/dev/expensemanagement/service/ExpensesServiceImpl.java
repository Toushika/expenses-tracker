package rnd.dev.expensemanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rnd.dev.expensemanagement.constant.DatePatternConstants;
import rnd.dev.expensemanagement.dto.request.CreateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.UpdateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;
import rnd.dev.expensemanagement.dto.response.*;
import rnd.dev.expensemanagement.entity.Expense;
import rnd.dev.expensemanagement.error.exception.InvalidAuthorizationHeaderException;
import rnd.dev.expensemanagement.error.exception.NoExpenseFoundException;
import rnd.dev.expensemanagement.error.exception.NoUserFoundException;
import rnd.dev.expensemanagement.utility.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static rnd.dev.expensemanagement.constant.ExceptionMessageConstants.MISSING_AUTHORIZATION_HEADER_MESSAGE;
import static rnd.dev.expensemanagement.constant.ExceptionMessageConstants.NO_USER_FOUND_MESSAGE;
import static rnd.dev.expensemanagement.constant.ResponseMessageConstants.*;
import static rnd.dev.expensemanagement.constant.RoleConstants.ADMIN_ROLE;
import static rnd.dev.expensemanagement.constant.RoleConstants.USER_ROLE;
import static rnd.dev.expensemanagement.constant.SecurityConstants.BEARER_INDEX;
import static rnd.dev.expensemanagement.constant.SecurityConstants.BEARER_PREFIX;

@Slf4j
@Service
public class ExpensesServiceImpl implements ExpensesService {

    private final AuthClientService authClientService;
    private final ExpensesAnemicService expensesAnemicService;

    public ExpensesServiceImpl(AuthClientService authClientService, ExpensesAnemicService expensesAnemicService) {
        this.authClientService = authClientService;
        this.expensesAnemicService = expensesAnemicService;
    }


    @Override
    public CreateExpenseResponse saveExpense(String authHeader, CreateExpenseRequest createExpenseRequest) {

        log.info("ExpensesServiceImpl :: saveExpense :: authHeader : {}", authHeader);
        log.info("ExpensesServiceImpl :: saveExpense :: createExpenseRequest : {}", createExpenseRequest);

        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);
        log.info("ExpensesServiceImpl :: saveExpense :: userInfoResponse : {}", userInfoResponse);

        Expense expense = expensesAnemicService.saveExpense(buildExpense(userInfoResponse.getUserId(), createExpenseRequest));
        return buildCreateExpenseResponse(expense);
    }

    @Override
    public List<ExpenseResponse> getAllExpenses(String authHeader) {

        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);
        log.info("ExpensesServiceImpl :: getAllExpenses :: role : {}", userInfoResponse.getRole());

        if (userInfoResponse.getRole().equals(ADMIN_ROLE)) {
            List<Expense> expenses = expensesAnemicService.getAllExpenses();
            return buildExpenseResponseList(expenses);
        } else {
            List<Expense> allExpensesByUserId = expensesAnemicService.getAllExpensesByUserId(userInfoResponse.getUserId());
            return buildExpenseResponseList(allExpensesByUserId);
        }
    }

    @Override
    public UpdateExpenseResponse updateExpense(String authHeader, String expenseId, UpdateExpenseRequest updateExpenseRequest) {

        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);

        if (userInfoResponse == null) {
            throw new NoUserFoundException(NO_USER_FOUND_MESSAGE);
        }

        Expense currentDbExpense;

        if (userInfoResponse.getRole().equals(ADMIN_ROLE)) {
            currentDbExpense = expensesAnemicService.findByExpenseId(expenseId);
        } else {
            currentDbExpense = expensesAnemicService.findByUserIdAndExpenseId(userInfoResponse.getUserId(), expenseId);
        }

        expensesAnemicService.saveExpense(buildExpenseForUpdate(updateExpenseRequest, currentDbExpense));

        return buildUpdateExpenseResponse(currentDbExpense);

    }

    @Override
    public DeleteResponse deleteAllExpense(String authHeader) {

        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);

        if (userInfoResponse == null) {
            throw new NoUserFoundException(NO_USER_FOUND_MESSAGE);
        }

        if (userInfoResponse.getRole().equals(ADMIN_ROLE)) {
            expensesAnemicService.deleteAll();
            return buildDeletionResponse(DELETION_SUCCESSFUL_RESPONSE);

        } else {
            return buildDeletionResponse(DELETION_UNSUCCESSFUL_RESPONSE);
        }

    }

    @Override
    public DeleteResponse deleteExpenseByExpenseId(String authHeader, String expenseId) {
        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);

        if (userInfoResponse == null) {
            throw new NoUserFoundException(NO_USER_FOUND_MESSAGE);
        }

        long deletedCount;

        if (userInfoResponse.getRole().equals(ADMIN_ROLE)) {
            deletedCount = expensesAnemicService.deleteByExpenseId(expenseId);

        } else if (userInfoResponse.getRole().equals(USER_ROLE)) {
            deletedCount = expensesAnemicService.deleteByUserIdAndExpenseId(userInfoResponse.getUserId(), expenseId);

        } else {
            return buildDeletionResponse("Expense - " + expenseId + " : " + DELETION_UNSUCCESSFUL_RESPONSE);
        }

        if (deletedCount > 0) {
            return buildDeletionResponse("Expense - " + expenseId + " : " + DELETION_SUCCESSFUL_RESPONSE);

        } else {
            return buildDeletionResponse("Expense - " + expenseId + " : " + DELETION_UNSUCCESSFUL_RESPONSE);
        }
    }

    @Override
    public Page<ExpenseResponse> getExpensesByCatalogue(String authHeader, ExpenseCategory category, int page, int size) {

        String token = getToken(authHeader);
        UserInfoResponse userInfoResponse = authClientService.validateToken(token);

        if (userInfoResponse == null) {
            throw new NoUserFoundException(NO_USER_FOUND_MESSAGE);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Expense> expensesPage;

        if (userInfoResponse.getRole().equals(ADMIN_ROLE)) {
            expensesPage = expensesAnemicService.getExpensesPage(category, pageable);
        } else {
            expensesPage = expensesAnemicService.getExpensesPageByUserId(category, userInfoResponse.getUserId(), pageable);
        }

        return expensesPage.map(this::buildExpenseResponse);
    }

    private static DeleteResponse buildDeletionResponse(String Deletion_Successful) {
        return DeleteResponse.builder()
                .message(Deletion_Successful)
                .build();
    }


    private Expense buildExpense(String userId, CreateExpenseRequest createExpenseRequest) {
        return Expense
                .builder()
                .userId(userId)
                .title(createExpenseRequest.getTitle())
                .description(createExpenseRequest.getDescription())
                .category(createExpenseRequest.getCategory())
                .amount(createExpenseRequest.getAmount())
                .createdAt(DateConverter.getConvertedDate(DatePatternConstants.DATE_PATTERN, new Date()))
                .build();

    }

    private CreateExpenseResponse buildCreateExpenseResponse(Expense expense) {
        return CreateExpenseResponse.builder()
                .expenseId(expense.getExpenseId())
                .title(expense.getTitle())
                .createdAt(expense.getCreatedAt())
                .build();
    }

    private String getToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new InvalidAuthorizationHeaderException(MISSING_AUTHORIZATION_HEADER_MESSAGE);
        }
        return authHeader.substring(BEARER_INDEX);

    }


    private List<ExpenseResponse> buildExpenseResponseList(List<Expense> expenses) {

        List<ExpenseResponse> expenseResponseList = new ArrayList<>();

        for (Expense expense : expenses) {
            expenseResponseList.add(buildExpenseResponse(expense));
        }
        return expenseResponseList;
    }

    private ExpenseResponse buildExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .expensesId(expense.getExpenseId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .build();
    }

    private UpdateExpenseResponse buildUpdateExpenseResponse(Expense expense) {

        return UpdateExpenseResponse
                .builder()
                .expenseId(expense.getExpenseId())
                .title(expense.getTitle())
                .updatedAt(expense.getUpdatedAt())
                .build();

    }

    private Expense buildExpenseForUpdate(UpdateExpenseRequest updateExpenseRequest, Expense currentDbExpense) {

        if (currentDbExpense == null) {
            throw new NoExpenseFoundException(NO_EXPENSE_FOUND_IN_DB_MESSAGE);
        }

        if (updateExpenseRequest.getTitle() != null) {
            currentDbExpense.setTitle(updateExpenseRequest.getTitle());
        }
        if (updateExpenseRequest.getDescription() != null) {
            currentDbExpense.setDescription(updateExpenseRequest.getDescription());
        }

        if (updateExpenseRequest.getCategory() != null) {
            currentDbExpense.setCategory(updateExpenseRequest.getCategory());
        }

        if (updateExpenseRequest.getAmount() != null) {
            currentDbExpense.setAmount(updateExpenseRequest.getAmount());
        }

        currentDbExpense.setUpdatedAt(DateConverter.getConvertedDate(DatePatternConstants.DATE_PATTERN_TIMESTAMPS, new Date()));
        return currentDbExpense;
    }

}
