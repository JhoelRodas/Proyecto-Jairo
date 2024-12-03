package bo.com.jvargas.veterinaria;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.util.TimeZone;

import bo.com.jvargas.veterinaria.negocio.admusuarios.InitializerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = { "bo.com.jvargas.veterinaria.datos.repository" })
@EntityScan(basePackages = { "bo.com.jvargas.veterinaria.datos.model" })
@ComponentScan(basePackages = { "bo.com.jvargas", "bo.com.jvargas.veterinaria.api",
        "bo.com.jvargas.veterinaria.security", "bo.com.jvargas.veterinaria.api.admusuarios" })
@SpringBootApplication(scanBasePackages = { "bo.com.jvargas", "bo.com.jvargas.veterinaria",
        "bo.com.jvargas.veterinaria.api.admusuarios" })
@EnableAsync
@EnableWebSecurity
@Slf4j
public class VeterinariaApplication implements CommandLineRunner {

    private final InitializerService initializerService;

    public static void main(String[] args) {
        log.info("Main start");
        TimeZone.getTimeZone(ZoneId.of("America/La_Paz"));
        Environment env = SpringApplication.run(VeterinariaApplication.class, args).getEnvironment();
        logApplicationStartup(env);
        log.info("Main end");
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Run with profile");
        initializerService.initAll();
    }

    private static void logApplicationStartup(Environment env) {
        String profile = env.getProperty("spring.profiles.active");
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (!StringUtils.hasText(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n" +
                "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                profile);

    }

}
