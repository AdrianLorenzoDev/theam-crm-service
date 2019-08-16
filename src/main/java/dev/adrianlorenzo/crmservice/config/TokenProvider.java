package dev.adrianlorenzo.crmservice.config;

import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidJwtTokenException;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {
    String getToken(String username, String role);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String getTokenFromRequest(HttpServletRequest req);

    boolean validateToken(String token) throws InvalidJwtTokenException;
}
