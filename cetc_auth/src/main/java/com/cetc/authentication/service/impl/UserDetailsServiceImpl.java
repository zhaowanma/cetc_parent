package com.cetc.authentication.service.impl;

import com.cetc.authentication.feign.UserClient;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginSysUser loginSysUser = userClient.findByUsername(username);
        if(loginSysUser==null){
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if(!loginSysUser.isEnabled()){
            throw new DisabledException("用户已作废");
        }else if(!loginSysUser.getType()){
            loginSysUser.setPassword(passwordEncoder.encode(loginSysUser.getUsername()));
        }

        return loginSysUser;
    }


}
