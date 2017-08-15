package com.tw.apistack.base.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistack.base.security.authrization.ApiStackAuthenticationSuccessHandler;
import com.tw.apistack.base.security.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private ApiStackAuthenticationSuccessHandler successHandler;

    public JWTLoginFilter(String url, AuthenticationManager authManager, AuthenticationFailureHandler authenticationFailureHandler) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        User user = new ObjectMapper()
                .readValue(req.getInputStream(), User.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(req, res, auth);
    }
}
