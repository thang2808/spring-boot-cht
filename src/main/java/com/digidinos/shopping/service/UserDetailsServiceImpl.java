package com.digidinos.shopping.service;

import com.digidinos.shopping.entity.Account;
import com.digidinos.shopping.serviceWithRepo.AccountService;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findAccount(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                account.getUserName(),
                account.getEncrytedPassword(),
                account.isActive(),
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(account.getUserRole()))
        );
    }
}
