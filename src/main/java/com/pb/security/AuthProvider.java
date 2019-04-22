package com.pb.security;

import com.pb.entity.User;
import com.pb.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * 自定义认证实现
 * @Author pengbin
 * @date 2019/4/17 16:58
 */
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    IUserService userService;

    private final BCryptPasswordEncoder paasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 验证逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();
        User user = userService.findUserByName(userName);
        if(user == null){
            throw new AuthenticationCredentialsNotFoundException("认证失败！");
        }
        if(user.getPassword().equals(inputPassword)){
            return new PreAuthenticatedAuthenticationToken(user,null,user.getAuthorities());
        }
        throw new BadCredentialsException("authError");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
