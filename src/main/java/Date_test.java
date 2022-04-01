import java.text.SimpleDateFormat;
import java.util.Date;

public class Date_test {
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        System.out.println("Date " + date);
        System.out.printf("VALUE(%s)%n",date);
    }
}
