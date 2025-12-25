package rnd.dev.expensemanagement.error.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException {

    public InvalidAuthorizationHeaderException() {
    }

    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }

    public InvalidAuthorizationHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthorizationHeaderException(Throwable cause) {
        super(cause);
    }

    public InvalidAuthorizationHeaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
