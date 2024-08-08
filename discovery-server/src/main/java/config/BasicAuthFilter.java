package config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Base64;

public class BasicAuthFilter implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";

    @Value("${eureka.server.username}")
    private String USERNAME;

    @Value("${eureka.server.password}")
    private String PASSWORD;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader(AUTH_HEADER);

        if (isAuthorized(authHeader)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isAuthorized(String authHeader) {
        if (authHeader != null && authHeader.startsWith(BASIC_PREFIX)) {
            String credentials = new String(Base64.getDecoder().decode(authHeader.substring(BASIC_PREFIX.length())));
            String[] values = credentials.split(":", 2);
            return values.length == 2 && USERNAME.equals(values[0]) && PASSWORD.equals(values[1]);
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No resources to clean up
    }
}
