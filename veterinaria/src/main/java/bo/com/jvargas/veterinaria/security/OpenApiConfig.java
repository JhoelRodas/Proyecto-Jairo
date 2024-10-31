package bo.com.jvargas.veterinaria.security;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "APIs Veterinaria",
                version = "v1",
                description = "",
                contact = @Contact(
                        name = "",
                        email = "mail@gmail.com"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8081",
                        description = " Servidor de desarrollo"
                ),
                @Server(
                        url = "http://localhost:8081",
                        description = " Ambiente Certificación"
                ),
                @Server(
                        url = "http://localhost:8081",
                        description = " Ambiente Productivo"
                ),
        }
)
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "jwt"
)
public class OpenApiConfig {
}
