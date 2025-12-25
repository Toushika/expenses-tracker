package rnd.dev.expensemanagement.constant;

public class ApiUrlConstants {

    private ApiUrlConstants() {

    }

    public static final String EXPENSE_BASE_URL = "/expense";
    public static final java.lang.String INDEX_BASE_URL = "/index";
    public static final String SAVE_URL = "/save";
    public static final String GET_ALL_URL = "/getAll";
    public static final String GET_BY_CATEGORY_URL = "/get";
    public static final String DELETE_ALL_URL = "/delete";
    public static final String DELETE_EXPENSE_ID_URL = "/delete/{expenseId}";
    public static final String UPDATE_EXPENSE_URL = "/update/{expenseId}";
    public static final String AUTH_VALIDATE_SERVICE_URL = "http://localhost:8090/auth/validate";
}
