package com.open.library.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtConfig;
import com.open.library.jwt.JwtService;
import com.open.library.service.security.UserDetailsCustom;
import com.open.library.utils.HelperUtils;
import com.open.library.utils.response.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private JwtService jwtService;

    private final ObjectMapper objectMapper;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager,
                                                   JwtConfig jwtConfig,
                                                   JwtService jwtService){
        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        setAuthenticationManager(manager);
        this.objectMapper = new ObjectMapper();
        this.jwtService = jwtService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        log.info("Start attempt to authentication");
        User loginRequest = objectMapper.readValue(request.getInputStream(), User.class);
        log.info("End attempt to authentication");

        return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            Collections.emptyList()));


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetailsCustom);
        BaseResponse responseObject = BaseResponse
                .builder()
                .success(true)
                .code(String.valueOf(HttpStatus.OK.value()))
                .data(accessToken)
                .build();
        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseObject);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
        log.info("End success authentication: {}", accessToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaseResponse responseDTO = BaseResponse.builder()
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .data(SystemConstraints.INVALID_USERNAME_OR_PASSWORD)
                .success(false)
                .build();

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }


}
