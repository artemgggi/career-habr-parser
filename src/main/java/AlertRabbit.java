import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    public static void main(String[] args) {
        try {
            String path = "src/main/resources/rabbit.properties";
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(readConfig(path))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println("Data store " + store);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int readConfig(String path) {
        ru.job4j.io.Config config = new ru.job4j.io.Config(path);
        config.load();
        return Integer.parseInt(config.value("rabbit.interval"));
    }

    public static void writeRunTimeSql() throws ClassNotFoundException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/aggregator";
        String login = "postgres";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            System.out.println("Connection sql database successfully");
            try (Statement statement = connection.createStatement()) {
                String addTimeQuery = "INSERT INTO logs.rabbit(created_date)"
                        + String.format("VALUES('%s')",date);
                statement.execute(addTimeQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println("Hash code " + hashCode());
        }
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs...");
            try {
                writeRunTimeSql();
                System.out.println("Time recorded to database");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            List<Long> store = (List<Long>) context
                    .getJobDetail()
                    .getJobDataMap()
                    .get("store");
            store.add(System.currentTimeMillis());
        }
    }
}
