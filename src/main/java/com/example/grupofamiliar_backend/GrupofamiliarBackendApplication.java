package com.example.grupofamiliar_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Grupo Familiar API",
                version = "1.0.0",
                description = "API REST para gestión de grupos familiares con autenticación JWT"
        )
)
public class GrupofamiliarBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrupofamiliarBackendApplication.class, args);
		System.out.println("\n====================================");
		System.out.println("Grupo Familiar Backend iniciado");
		System.out.println("API disponible en: http://localhost:8080/api");
		System.out.println("Swagger UI: http://localhost:8080/api/swagger-ui.html");
		System.out.println("====================================\n");
	}

}
