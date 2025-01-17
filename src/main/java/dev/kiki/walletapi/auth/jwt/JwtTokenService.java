package dev.kiki.walletapi.auth.jwt;

import dev.kiki.walletapi.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenService {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = (User) userDetails;
        return Jwts.builder()
                .subject(username)
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("full-name", user.getFirstName() + " " +user.getLastName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME ))
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long expirationTime() {
        return EXPIRATION_TIME;
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

}
