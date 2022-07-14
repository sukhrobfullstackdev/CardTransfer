package uz.transfer.cardtransfer.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    long expireDate = 36_000_000;
    String secretWord = "Shirin";

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expireDate)).signWith(SignatureAlgorithm.HS512, secretWord).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }
}
