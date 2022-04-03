package utils;

import java.text.ParseException;
import java.util.Date;

public interface DateTimeParser {
    Date parse(String parse) throws ParseException;
}
