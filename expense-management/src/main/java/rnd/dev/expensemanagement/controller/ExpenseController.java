package rnd.dev.expensemanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import rnd.dev.expensemanagement.dto.request.CreateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.UpdateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;
import rnd.dev.expensemanagement.dto.response.CreateExpenseResponse;
import rnd.dev.expensemanagement.dto.response.DeleteResponse;
import rnd.dev.expensemanagement.dto.response.ExpenseResponse;
import rnd.dev.expensemanagement.dto.response.UpdateExpenseResponse;
import rnd.dev.expensemanagement.service.ExpensesService;

import java.util.List;

import static rnd.dev.expensemanagement.constant.ApiUrlConstants.*;

@Slf4j
@RestController
public class ExpenseController extends AbstractController {

    private final ExpensesService expensesService;

    public ExpenseController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }


    @GetMapping(INDEX_BASE_URL)
    public String sayHello() {
        return "Hello Expenses!!";
    }

    @PostMapping(SAVE_URL)
    public CreateExpenseResponse saveExpense(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody CreateExpenseRequest createExpensesRequest) {
        return expensesService.saveExpense(authHeader, createExpensesRequest);

    }

    @GetMapping(GET_ALL_URL)
    public List<ExpenseResponse> getAllExpenses(@RequestHeader("Authorization") String authHeader) {
        return expensesService.getAllExpenses(authHeader);

    }

    @GetMapping(GET_BY_CATEGORY_URL)
    public Page<ExpenseResponse> getExpenseByCategory(@RequestHeader("Authorization") String authHeader,
                                                      @RequestParam(name = "category") ExpenseCategory category,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return expensesService.getExpensesByCatalogue(authHeader, category, page, size);
    }

    @PutMapping(UPDATE_EXPENSE_URL)
    public UpdateExpenseResponse updateExpense(@RequestHeader("Authorization") String authHeader, @PathVariable String expenseId, @RequestBody UpdateExpenseRequest updateExpenseRequest) {
        return expensesService.updateExpense(authHeader, expenseId, updateExpenseRequest);
    }

    @DeleteMapping(DELETE_ALL_URL)
    public DeleteResponse deleteAllExpenses(@RequestHeader("Authorization") String authHeader) {
        return expensesService.deleteAllExpense(authHeader);
    }

    @DeleteMapping(DELETE_EXPENSE_ID_URL)
    public DeleteResponse deleteByExpenseId(@RequestHeader("Authorization") String authHeader, @PathVariable String expenseId) {
        return expensesService.deleteExpenseByExpenseId(authHeader, expenseId);
    }

}
