package dev.kiki.walletapi;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WalletAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletAppApiApplication.class, args);
    }

    @GetMapping("/")
    @Hidden
    public String welcomeMessage() {
        return "<h3>Welcome to Wallet API</h3>" +
                "<p>Explore the API documentation: <a href='/swagger-ui.html' target='_blank'>Swagger UI</a></p>";
    }

}
