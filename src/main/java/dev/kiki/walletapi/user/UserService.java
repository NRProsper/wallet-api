package dev.kiki.walletapi.user;

import dev.kiki.walletapi.exception.ResourceNotFoundException;
import dev.kiki.walletapi.user.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserDto createUserDto) {
        var user = new User();
        user.setFirstName(createUserDto.firstName());
        user.setLastName(createUserDto.lastName());
        user.setEmail(createUserDto.email());
        user.setPassword(passwordEncoder.encode(createUserDto.password()));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            String userName = auth.getName();
            System.out.println(userName);
            return userRepository.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }else {
            throw new RuntimeException("User not found");
        }
    }

}
