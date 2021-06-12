package br.com.forum.config.swagger

import br.com.forum.model.Usuario
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfigurations {

    @Bean
    fun forumApi(): Docket{
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.forum"))
            .paths(PathSelectors.ant("/**"))
            .build()
            .ignoredParameterTypes(Usuario::class.java)
            .globalOperationParameters(
                listOf(
                    ParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .modelRef(ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
                )
            )
    }

}