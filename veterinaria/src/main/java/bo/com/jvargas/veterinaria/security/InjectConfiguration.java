package bo.com.jvargas.veterinaria.security;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serializable;
import java.util.Optional;


@Configuration
@EnableJpaAuditing
public class InjectConfiguration implements WebMvcConfigurer, Serializable{
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("ADMIN");
            }

            try {
                Optional<User> principal = Optional.of((User) authentication.getPrincipal());
                User user = principal.get();
                return Optional.ofNullable(user.getUsername());
            }catch (Exception e){
                return Optional.of("ADMIN");
            }
        };


    }

    @Bean
    public AuthUser auditorAwareUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }
            Optional<AuthUser> principal = Optional.of((AuthUser) authentication.getPrincipal());
            return principal.get();
    }
}
