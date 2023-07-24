package com.open.library.jwt;

import com.open.library.service.security.UserDetailsCustom;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {

    Claims extractClaims(String token);

    Key getKey();

    String generateToken(UserDetailsCustom userDetailsCustom);

    boolean isValidToken(String token);
    boolean idAdmin();
    boolean isUser();
    String getCurrentUser();
}
