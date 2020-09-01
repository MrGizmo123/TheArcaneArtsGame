package Game.Logging;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;


public class Logger {

    private static String getTimestamp()
    {
        Date d = new Date();

        String formattedDate = d.toString();
        return formattedDate;
    }

    public static void i(String tag, String message)
    {
        System.out.println(getTimestamp() + " INFO: " + message);
    }

    public static void w(String tag, String message)
    {
        Timestamp t = Timestamp.from(Instant.now());
        System.out.println(getTimestamp() + " WARN: " + message);
    }

    public static void e(String tag, String message)
    {
        Timestamp t = Timestamp.from(Instant.now());
        System.err.println(getTimestamp() + " ERROR: " + message);
    }

}
