package utils;

import java.time.LocalDateTime;

public class HarbCareerDateTimeParser implements DateTimeParser{

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime date = LocalDateTime.parse(parse);
        return date;
    }
}
