package ch.elca.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main application class.
 */
@SpringBootApplication
@EnableSwagger2
public class BackendApplication {

    /**
     * Main application entry point.
     *
     * @param args argument array passed to <i>Spring</i>
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
