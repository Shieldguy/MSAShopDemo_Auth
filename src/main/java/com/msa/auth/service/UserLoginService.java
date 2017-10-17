package com.msa.auth.service;

import com.msa.auth.domain.LoginUser;
import com.msa.auth.domain.UserRole;
import com.msa.auth.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserLoginService implements UserDetailsService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = authUserRepository.findByUsernameEquals(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return new User(username, user.getPassword(), user.isEnabled(), true,
                true, true, getGrantedAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Collection<UserRole> roles) {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRoleName()))
                .collect(Collectors.toList());
    }
}
