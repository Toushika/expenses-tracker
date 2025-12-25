package rnd.dev.authmanagement.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rnd.dev.authmanagement.error.exception.InvalidAuthorizationHeaderException;
import rnd.dev.authmanagement.error.exception.NoUserFoundException;
import rnd.dev.authmanagement.error.exception.UserAlreadyExistException;
import rnd.dev.authmanagement.error.exception.WrongPasswordException;
import rnd.dev.authmanagement.error.response.ApiErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExist(UserAlreadyExistException userAlreadyExistException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), userAlreadyExistException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoUserFound(NoUserFoundException noUserFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), noUserFoundException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleWrongPassword(WrongPasswordException wrongPasswordException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), wrongPasswordException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAuthorization(InvalidAuthorizationHeaderException invalidAuthorizationHeaderException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), invalidAuthorizationHeaderException.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
