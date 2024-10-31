package bo.com.jvargas.veterinaria.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Project    onboarding
 * Package    :bo.com.mc4.onboarding.authenticate.filter
 * Date       :18/12/2018
 * Created by :fmontero
 */

@Slf4j
@Component
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[init], CORS Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");


        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");

        response.setHeader("x-ua-compatible", "IE=8");
        response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("X-Powered-By", "none");
        response.setHeader("Server", "none");
        response.setDateHeader("Expires", 0); // Proxies
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Strict-Transport-Security", "max-age=15552000; includeSubDomains; preload");
        response.setDateHeader("Last-Modified", new Date().getTime());
        response.setHeader("X-Firefox-Spdy", "3.1");
        response.setHeader("x-content-customerPhone-options", "nosniff");
        response.setHeader("Options", "-Indexes");
        response.setHeader("Public-Key-Pins", "pin-sha256 = YWxlcGFjby5tYXRvbg==; max-age=15768000; includeSubDomains");
        response.setHeader("X-Content-Type-Options", "nosniff");

        response.addHeader("Content-Security-Policy",
                "default-src 'self' https: data: blob:; child-src 'self' https: data: blob:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https: blob:; img-src 'self' https: data:; style-src 'self'  'unsafe-inline' https: ; font-src 'self' https: ");
        response.addHeader("X-Content-Security-Policy",
                "default-src 'self' https: data: blob:; script-src 'self' 'unsafe-inline' https: 'unsafe-eval' blob:; img-src 'self' https: data:;  style-src 'self' 'unsafe-inline' https:; font-src 'self' https:");
        response.addHeader("X-Webkit-CSP",
                "default-src 'self' https: data: blob:; child-src 'self' https: data: blob:; script-src 'self' 'unsafe-inline' https: 'unsafe-eval' blob:; img-src 'self' https: data:; style-src 'self' 'unsafe-inline' https:; font-src 'self' https:");
        response.addHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
