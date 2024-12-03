package bo.com.jvargas.veterinaria.security;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.negocio.admusuarios.UserService;
import bo.com.jvargas.veterinaria.negocio.admusuarios.impl.UserDetailsService;
import bo.com.jvargas.veterinaria.utils.OperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:changeit}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:28800000}")
    private int validityInMinutes = 480; // 8h
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, AuthUser authUser) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("cambiar-password", authUser.isGeneratedPassword());
        Date now = new Date();
        Date validity = DateUtils.addHours(now, 24);
        log.info("NOW: {}, EXPIRA EN: {}", now, validity);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createTokenApi(String username, Date fechaInicio, Date fechaExpiracion) {
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(fechaInicio)
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                return bearerToken.substring(7);
            }catch (OperationException e) {
                log.error("Usuario no encontrado, UsuarioId[{}]", req.getHeader("username"));
                return null;
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date fechaActual = new Date();
            if(claims.getBody().getExpiration().after(fechaActual)){
//                return this.tokenService.validateToken(token);
            }
            return true;
        } catch (ExpiredJwtException e){
            log.warn("La sesión del usuario a expirado");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error al validar el TOKEN",e);
            throw new OperationException("Token JWT caducado o no válido.");
        }
    }

    public String createToken2(Authentication auth, Date fechaExpiracion) throws JsonProcessingException {
        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put("adm-token", auth.getCredentials());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
