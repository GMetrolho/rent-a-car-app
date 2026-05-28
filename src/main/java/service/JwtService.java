package service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Chave lida do application.properties — não regenera em cada restart
    @Value("${jwt.secret}")
    private String segredo;

    @Value("${jwt.expiracao}")
    private long expiracaoMs;

    private Key getChave() {
        return Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(String email, String cargo, Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("cargo", cargo);
        claims.put("id", id);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracaoMs))
                .signWith(getChave())
                .compact();
    }

    public Claims extrairTodasClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChave())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token, String email) {
        Claims claims = extrairTodasClaims(token);
        return claims.getSubject().equals(email)
            && claims.getExpiration().after(new Date());
    }
}
