package com.open.library.jwt.impl;

import com.open.library.exeption.BaseException;
import com.open.library.jwt.JwtConfig;
import com.open.library.jwt.JwtService;
import com.open.library.service.security.UserDetailsCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserDetailsService userDetailsService;
    private Claims claims = null;

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom) {

        log.info("User Detail: " + userDetailsCustom);
        Instant now = Instant.now();

        List<String> roles = new ArrayList<>();

        userDetailsCustom.getAuthorities().forEach(role -> {
            roles.add(role.getAuthority());
        });

        log.info("Roles: {} ", roles);
        return Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .claim("authorities" , userDetailsCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("roles" , roles)
                .claim("isEnable", userDetailsCustom.isEnabled())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        final String username = extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return !ObjectUtils.isEmpty(userDetails);
    }



    private String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        this.claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Mã token đã hết hạn.");
        }catch (UnsupportedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Mã token không được hỗ trợ.");
        }catch (MalformedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Mã token có định dạng 3 không hợp lệ.");
        }catch (SignatureException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Mã token có định dạng không hợp lệ.");
        }catch (Exception e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
        }

        return claims;
    }

    @Override
    public boolean isAdmin() {
        List<String> authorities = claims.get("authorities", List.class);
        if(authorities.contains("ADMIN")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUser() {
        List<String> authorities = claims.get("authorities", List.class);
        if(authorities.contains("USER")) {
            return true;
        }
        return false;
    }

    @Override
    public String getCurrentUser() {
        return (String) claims.get("sub");
    }
}
