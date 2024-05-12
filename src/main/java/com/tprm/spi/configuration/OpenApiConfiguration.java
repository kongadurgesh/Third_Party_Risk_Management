package com.tprm.spi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Durgesh Konga", email = "kongadurgesh20@gmail.com", url = "https://www.linkedin.com/in/durgeshkonga/"), description = "OpenApi Documentation for TPRM Application", title = "OpenApi Specification - TPRM", version = "1.0", license = @License(name = "Rights Owned by Durgesh Konga", url = "https://www.linkedin.com/in/durgeshkonga/"), termsOfService = "Terms of Service"), servers = {
                @Server(description = "Local Environment", url = "http://localhost:8071")
})
public class OpenApiConfiguration {

}
