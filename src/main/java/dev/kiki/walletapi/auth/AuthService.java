package dev.kiki.walletapi.auth;

import dev.kiki.walletapi.user.User;
import dev.kiki.walletapi.user.UserRepository;
import dev.kiki.walletapi.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public User authenticateUser(LoginDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        return userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
