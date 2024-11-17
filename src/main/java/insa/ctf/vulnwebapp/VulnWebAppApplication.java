package insa.ctf.vulnwebapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VulnWebAppApplication {

    public static void main(String[] args) {
        // Charger le fichier .env
        Dotenv dotenv = Dotenv.load();

        // Passer les variables d'environnement à Spring Boot
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));

        SpringApplication.run(VulnWebAppApplication.class, args);
    }

}
