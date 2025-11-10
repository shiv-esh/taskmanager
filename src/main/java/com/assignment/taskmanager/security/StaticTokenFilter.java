package com.assignment.taskmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * A custom security filter that validates a static authorization token
 * before allowing requests to proceed further into the application.
 */
 @Component
public class StaticTokenFilter extends OncePerRequestFilter {

    @Value("${security.static-token}")
    private String staticToken;

    /**
     * This method intercepts each incoming HTTP request once per request cycle.
     * It checks whether the request contains a valid static token in the
     * "Authorization" header. If validation fails, the request is terminated
     * with a 401 Unauthorized response.
     *
     * @param request  the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the chain of filters to be executed
     * @throws ServletException if any servlet-specific error occurs
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        //Validate the static token
        if (header == null || !header.equals(staticToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized access.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
