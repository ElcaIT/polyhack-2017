package ch.elca.hackathon.impl.service;

import ch.elca.hackathon.BackendApplication;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Base integration test class firing up the service under a random port.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractBackendIT {

    @Getter
    @Value("${local.server.port}")
    private int port;

    @Getter
    @Value("http://localhost:${local.server.port}")
    private String host;

    /**
     * Configures the port to use by <i>RESTAssured</i>.
     */
    @Before
    public void configurePort() {
        RestAssured.port = port;
    }

    /**
     * Configures the default encoding to be used by <i>RESTAssured</i> to transfer objects to be <i>UTF-8</i>.
     */
    @Before
    public void configureContentEncoding() {
        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
    }

    /**
     * Convenience method to create a {@link RequestSpecification} for a given relative path.
     *
     * @param path the relative path
     * @return the {@link RequestSpecification}
     */
    public final RequestSpecification buildRequestSpecification(String... path) {
        return new RequestSpecBuilder().setBasePath(Arrays.stream(path).reduce((a, b) -> a + b).get()).build();
    }

}
