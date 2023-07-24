package com.open.library.service.security;

import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.exeption.BaseException;
import com.open.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetailsCustom userDetailsCustom = getUserDetails(username);

        if(ObjectUtils.isEmpty(userDetailsCustom)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
        }

        return userDetailsCustom;
    }

    public UserDetailsCustom getUserDetails(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
        }
        User userDetail = user.get();
        return new UserDetailsCustom(
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList())
        );
    }
}
