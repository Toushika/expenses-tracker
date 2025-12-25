package rnd.dev.authmanagement.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {


    private DateConverter() {

    }

    public static String getConvertedDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
