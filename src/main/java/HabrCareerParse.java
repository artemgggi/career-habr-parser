import utils.DateTimeParser;
import utils.Post;
import java.util.List;

public class HabrCareerParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        return null;
    }
}
