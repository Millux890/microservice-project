package com.brozekdev.productservice.config;

import com.brozekdev.productservice.exceptions.AuthorizationException;
import com.brozekdev.productservice.exceptions.ProductError;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
@Component
public class BasicAuthFilter implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String API_GATEWAY_HEADER = "API-GATEWAY";
    private static final String BASIC_PREFIX = "Basic ";

    @Value("${eureka.server.username}")
    private String username;

    @Value("${eureka.server.password}")
    private String password;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String apiGatewayHeader = httpRequest.getHeader(API_GATEWAY_HEADER);
        String authHeader = httpRequest.getHeader(AUTH_HEADER);

        try {
            if ((apiGatewayHeader != null) || isAuthorized(authHeader)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAuthorized(String authHeader) throws AuthorizationException {
        try {
            if (authHeader != null) {
                String credentials = new String(Base64.getDecoder().decode(authHeader.substring(BASIC_PREFIX.length())));
                String[] values = credentials.split(":", 2);
                System.out.println(this.username);
                return values.length == 2 && username.equals(values[0]) && password.equals(values[1]);
            }
        }catch (Exception e){
            throw new AuthorizationException(ProductError.UNAUTHORIZED.getMessage());
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }
}
