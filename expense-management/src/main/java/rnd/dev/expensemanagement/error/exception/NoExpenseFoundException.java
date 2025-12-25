package rnd.dev.expensemanagement.error.exception;

public class NoExpenseFoundException extends RuntimeException {

    public NoExpenseFoundException() {
    }

    public NoExpenseFoundException(String message) {
        super(message);
    }

    public NoExpenseFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExpenseFoundException(Throwable cause) {
        super(cause);
    }

    public NoExpenseFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
