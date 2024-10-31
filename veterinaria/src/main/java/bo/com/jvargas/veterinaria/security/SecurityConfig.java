package bo.com.jvargas.veterinaria.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.SessionManagementFilter;

@Slf4j
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").permitAll()

                .antMatchers(HttpMethod.POST,"/auth/signin").permitAll()
//                .antMatchers(HttpMethod.POST,"/auth/recuperar-password").permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger*/**","/webjars/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/about/version").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

        //@formatter:on
    }


}

