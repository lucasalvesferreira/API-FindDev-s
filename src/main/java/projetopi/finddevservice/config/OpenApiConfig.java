package projetopi.finddevservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("PROJECT-FIND-DEV-RESTFUL-API")
                        .version("v1")
                        .description("Api feito por integrantes colaborativos do findDev.")
                        .termsOfService("https://github.com/CarlosFelixxs/FindDev-s")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/CarlosFelixxs/FindDev-s")
                        )
                );


    }

}
