package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HarbCareerDateTimeParser implements DateTimeParser{

    @Override
    public Date parse(String toparse) throws ParseException {
        Date date = new SimpleDateFormat("").parse(toparse);
        return date;
    }
}
