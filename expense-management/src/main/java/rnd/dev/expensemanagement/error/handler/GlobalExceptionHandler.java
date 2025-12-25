package rnd.dev.expensemanagement.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rnd.dev.expensemanagement.error.exception.InvalidAuthorizationHeaderException;
import rnd.dev.expensemanagement.error.exception.NoExpenseFoundException;
import rnd.dev.expensemanagement.error.exception.NoUserFoundException;
import rnd.dev.expensemanagement.error.response.ApiErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoUserFound(NoUserFoundException noUserFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), noUserFoundException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAuthorization(InvalidAuthorizationHeaderException invalidAuthorizationHeaderException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidAuthorizationHeaderException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NoExpenseFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoExpenseFound(NoExpenseFoundException noExpenseFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), noExpenseFoundException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }
}
