package ch.elca.hackathon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * The {@link BackendConfiguration} provides general configuration information.
 * <p>
 * <b>Note:</b> {@link ConfigurationProperties} only work if both the getters and setters are present.
 * </p>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "application.service.backend")
public class BackendConfiguration {

    /**
     * The title of the service.
     */
    private String title;

    /**
     * The description of the service.
     */
    private String description;

    /**
     * The version of the service.
     */
    private String version;

}
