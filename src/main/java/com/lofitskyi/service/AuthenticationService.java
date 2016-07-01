package com.lofitskyi.service;

import com.lofitskyi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = null;
        try {
            user = dao.findByLogin(login);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

            return new org.springframework.security.core.userdetails.User(user.getLogin(),
                    user.getPassword(), authorities);
        } catch (PersistentException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }

    }
}
