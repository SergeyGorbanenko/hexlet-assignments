package exercise;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    int nowHour = LocalDateTime.now().getHour();

    @Bean
    @Scope("prototype")
    public Daytime get() {
        if (nowHour >= 6 && nowHour < 22)
            return new Day();
        else
            return new Night();
    }
    // END
}
