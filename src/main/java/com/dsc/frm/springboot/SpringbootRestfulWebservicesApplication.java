package com.dsc.frm.springboot;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Doc formation rest spring boot",
				description = "Doc généré par Swagger sur la formation REST / microservice",
				version = "1.0",
				contact = @Contact(
						name = "Denis Schneider",
						email = "denis.schneider@cegedim.com",
						url = "https://fomration.rest.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://fomration.rest.com/mylicense"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot user doc perso par ex...",
				url = "https://fomration.rest.com/user_manual"
		)
)
public class SpringbootRestfulWebservicesApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestfulWebservicesApplication.class, args);
	}

}
