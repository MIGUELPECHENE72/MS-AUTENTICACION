package co.com.bancolombia.api.security;

import co.com.bancolombia.model.persona.Persona;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.tk.key}")
    private String secretKey;
    @Value("${jwt.tk.expiration}")
    private Long expiration;

    // Método para crear el token JWT
    public String createToken(Persona persona, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(persona.getIdentificacion());
        claims.put("roles", roles);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener el nombre de usuario desde el token
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Obtener los roles del token
    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    // Obtener los claims del token JWT
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
