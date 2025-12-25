package rnd.dev.expensemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rnd.dev.expensemanagement.dto.request.misc.ExpenseCategory;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {

    private String expensesId;
    private String title;
    private String description;
    private ExpenseCategory category;
    private double amount;
    private String createdAt;
}
