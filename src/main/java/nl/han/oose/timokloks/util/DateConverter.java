package nl.han.oose.timokloks.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    private final DateFormat FORMAT_TO_DATABASE = new SimpleDateFormat("yyyy/MM/dd");
    private final DateFormat FORMAT_TO_CLIENT = new SimpleDateFormat("MM-dd-yyyy");

    private Calendar cal;

    public DateConverter() {
        cal = Calendar.getInstance();
    }

    public String getConvertedDateToDatabase(int durationOfTokenInMinutes) {
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, durationOfTokenInMinutes);
        return FORMAT_TO_DATABASE.format(cal.getTime());
    }

    public String getConvertedDateToClient(Date date) {
        return FORMAT_TO_CLIENT.format(date);
    }
}
