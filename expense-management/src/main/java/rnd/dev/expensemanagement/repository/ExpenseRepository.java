package rnd.dev.expensemanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;
import rnd.dev.expensemanagement.entity.Expense;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findAllByUserId(String userId);

    Expense findByUserIdAndExpenseId(String userId, String expenseId);

    long deleteByUserIdAndExpenseId(String userId, String expenseId);

    long deleteByExpenseId(String expenseId);

    Page<Expense> findByCategory(ExpenseCategory category, Pageable pageable);
    Page<Expense> findByCategoryAndUserId(ExpenseCategory category, String userId, Pageable pageable);
}
