package rnd.dev.authmanagement.error.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;


    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
