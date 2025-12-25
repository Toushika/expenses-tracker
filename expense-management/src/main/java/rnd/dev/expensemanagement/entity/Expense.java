package rnd.dev.expensemanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;

import static rnd.dev.expensemanagement.constant.CollectionConstants.EXPENSES_COLLECTION;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = EXPENSES_COLLECTION)
public class Expense {

    @Id
    private String expenseId;

    private String userId;

    private String title;

    private String description;

    private ExpenseCategory category;

    private double amount;

    private String createdAt;

    private String updatedAt;
}
