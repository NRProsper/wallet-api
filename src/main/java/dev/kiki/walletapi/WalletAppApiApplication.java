package dev.kiki.walletapi;

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
    public String welcomeMessage() {
        return("<h3>Welcome to Wallet API</h3>");
    }

}
