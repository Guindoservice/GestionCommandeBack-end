package com.example.cmd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
//la configurateur de swagger
@OpenAPIDefinition(
		info = @Info(
				title = "Bienvenu sur Gestion de commande en ligne",
				version = "1.0.0",
				description = "Gestion de commande en ligne",
				termsOfService = "Commande",
				contact = @Contact(
						name = "Groupe3",
						email = "guindo8.com@gmail.com"
				),
				license = @License(
						name = "APiCommande",
						url = "Commande en ligne"
				)
		)
)
public class CmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmdApplication.class, args);
	}

}
