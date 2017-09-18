package ch.elca.hackathon.config;

import ch.elca.hackathon.BackendApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.util.Date;


/**
 * The {@link BackendSpecification} contains the <i>Swagger 2.0</i> specification for all exposed services.
 */
@Configuration
public class BackendSpecification {

    /**
     * Creates a {@code Docket} specifying how to document the <i>REST</i> interface.
     *
     * @return the configured <i>SpringFox</i> {@code Docket}
     */
    @Bean
    public Docket api(BackendConfiguration starterConfiguration) {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(BackendApplication.class.getPackage().getName()))
            .build()
            .directModelSubstitute(LocalDate.class, Date.class)
            .apiInfo(apiInfo(starterConfiguration));
    }

    private ApiInfo apiInfo(BackendConfiguration starterConfiguration) {
        String title = starterConfiguration.getTitle();
        String description = starterConfiguration.getDescription();
        String version = starterConfiguration.getVersion();

        return new ApiInfoBuilder().title(title).description(description).version(version).build();
    }

}
