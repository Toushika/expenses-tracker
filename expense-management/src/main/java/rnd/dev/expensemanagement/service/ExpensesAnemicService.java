package rnd.dev.expensemanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;
import rnd.dev.expensemanagement.entity.Expense;
import rnd.dev.expensemanagement.repository.ExpenseRepository;

import java.util.List;

@Slf4j
@Service
public class ExpensesAnemicService {

    private final ExpenseRepository expenseRepository;

    public ExpensesAnemicService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    protected Expense saveExpense(Expense expense) {
        log.info("ExpensesAnemicService :: saveExpense :: expense : {}", expense);
        return expenseRepository.save(expense);
    }

    protected List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    protected List<Expense> getAllExpensesByUserId(String userId) {
        return expenseRepository.findAllByUserId(userId);
    }

    protected Expense findByUserIdAndExpenseId(String userId, String expenseId) {
        return expenseRepository.findByUserIdAndExpenseId(userId, expenseId);
    }

    protected Expense findByExpenseId(String expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow();
    }

    protected boolean deleteAll() {
        expenseRepository.deleteAll();
        return true;
    }

    protected long deleteByUserIdAndExpenseId(String userId, String expenseId) {
        return expenseRepository.deleteByUserIdAndExpenseId(userId, expenseId);

    }

    protected long deleteByExpenseId(String expenseId) {
        return expenseRepository.deleteByExpenseId(expenseId);
    }

    protected Page<Expense> getExpensesPage(ExpenseCategory category, Pageable pageable) {
        return expenseRepository.findByCategory(category, pageable);
    }

    protected Page<Expense> getExpensesPageByUserId(ExpenseCategory category, String userId, Pageable pageable) {
        return expenseRepository.findByCategoryAndUserId(category, userId, pageable);
    }

}
