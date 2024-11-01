package yellow.penguin.demo.security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.crypto.SecretKey;

public class TokenManager {

    private static final SecretKey SECRET_KEY =  loadSecretKey();
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    
    // Load the secret key from properties file
    private static SecretKey loadSecretKey() {
        Properties properties = new Properties();
        try (InputStream input = TokenManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            properties.load(input);
            String secret = properties.getProperty("jwt.secret");
            return Keys.hmacShaKeyFor(secret.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error loading secret key from properties file", e);
        }
    }
    
    // Method to generate the token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("name", username) // Add a specific claim
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);
            return true; // If the token is valid
        } catch (Exception e) {
            return false; // If an error occurs, the token is invalid
        }
    }

    // Method to get claims from the token
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Method to get the username from the token
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}
