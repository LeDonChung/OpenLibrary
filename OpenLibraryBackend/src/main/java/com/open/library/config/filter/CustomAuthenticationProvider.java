package com.open.library.config.filter;

import com.open.library.POJO.Role;
import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.exeption.BaseException;
import com.open.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Start actual authentication");
        final String username = authentication.getName();

        final String password = authentication.getCredentials().toString();

        Optional<User> user;
        try {
            user = userRepository.findByUsername(username);
        }catch (Exception e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
        }

        final List<GrantedAuthority> authorities = getAuthorities(user.get().getRoles().stream().toList());

        final Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

        log.info("End actual authentication");
        return auth;
    }

    private List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if(!ObjectUtils.isEmpty(roles)){
            roles.forEach( r-> {
                permissions.add(r.getName());
            });
        }

        permissions.forEach(p->{
            result.add(new SimpleGrantedAuthority(p));
        });
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
