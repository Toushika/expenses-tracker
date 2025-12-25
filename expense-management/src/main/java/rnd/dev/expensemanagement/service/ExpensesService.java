package rnd.dev.expensemanagement.service;

import org.springframework.data.domain.Page;
import rnd.dev.expensemanagement.dto.request.CreateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.UpdateExpenseRequest;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;
import rnd.dev.expensemanagement.dto.response.CreateExpenseResponse;
import rnd.dev.expensemanagement.dto.response.DeleteResponse;
import rnd.dev.expensemanagement.dto.response.ExpenseResponse;
import rnd.dev.expensemanagement.dto.response.UpdateExpenseResponse;

import java.util.List;

public interface ExpensesService {

    CreateExpenseResponse saveExpense(String authHeader, CreateExpenseRequest createExpenseRequest);

    List<ExpenseResponse> getAllExpenses(String authHeader);

    Page<ExpenseResponse> getExpensesByCatalogue(String authHeader, ExpenseCategory category, int page, int size);

    UpdateExpenseResponse updateExpense(String authHeader, String expenseId, UpdateExpenseRequest updateExpenseRequest);

    DeleteResponse deleteAllExpense(String authHeader);

    DeleteResponse deleteExpenseByExpenseId(String authHeader, String expenseId);
}
