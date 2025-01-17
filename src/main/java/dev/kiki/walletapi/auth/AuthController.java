package dev.kiki.walletapi.auth;

import dev.kiki.walletapi.auth.jwt.JwtTokenService;
import dev.kiki.walletapi.user.User;
import dev.kiki.walletapi.user.UserService;
import dev.kiki.walletapi.user.dto.CreateUserDto;
import dev.kiki.walletapi.user.dto.CreateUserResponse;
import dev.kiki.walletapi.user.dto.LoginDto;
import dev.kiki.walletapi.user.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<CreateUserResponse> registerUser(
            @Valid @RequestBody CreateUserDto createUserDto
            ) {
        User createdUser = userService.createUser(createUserDto);

        var response = new CreateUserResponse(
                createdUser.getId(),
                createdUser.getFirstName(),
                createdUser.getLastName(),
                createdUser.getEmail()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginDto loginDto
            ) {

        var authenticatedUser = authService.authenticateUser(loginDto);
        String accessToken = jwtTokenService.generateToken(authenticatedUser);
        var response = new LoginResponse(
                "Logged successfully",
                accessToken,
                jwtTokenService.expirationTime()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

}
