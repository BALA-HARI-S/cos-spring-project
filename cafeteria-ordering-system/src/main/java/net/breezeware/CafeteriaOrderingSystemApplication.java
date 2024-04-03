package net.breezeware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "OpenApi specification",
        description = "OpenApi specification for cafeteria ordering system spring project",
        contact = @Contact(name = "Someone", url = "some_url.com", email = "someone@email.com"), version = "1.0"),
        servers = { @Server(description = "Local ENV", url = "http://localhost:8080") })
@SpringBootApplication
public class CafeteriaOrderingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeteriaOrderingSystemApplication.class, args);
    }

}
