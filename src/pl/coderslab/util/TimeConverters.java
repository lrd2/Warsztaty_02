package pl.coderslab.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverters {

    public static void main(String[] args) {

    }

    public static Timestamp getTimestamp(String dateString) {
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(dateString);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static Timestamp getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
}
