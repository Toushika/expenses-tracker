package rnd.dev.expensemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExpenseResponse {

    private String expenseId;
    private String title;
    private String updatedAt;
}
