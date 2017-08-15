package com.tw.apistack.base.security.api;

import com.tw.apistack.base.security.api.dto.NewPassword;
import com.tw.apistack.base.security.authentication.JwtToken;
import com.tw.apistack.base.security.authentication.TokenAuthenticationService;
import com.tw.apistack.base.security.authrization.ApiStackAuthenticationSuccessHandler;
import com.tw.apistack.base.security.authrization.ApiStackUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    private final TokenAuthenticationService tokenAuthenticationService;
    private final ApiStackUserDetailsService apiStackUserDetailsService;

    @Autowired
    public LoginController(TokenAuthenticationService tokenAuthenticationService, ApiStackUserDetailsService apiStackUserDetailsService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.apiStackUserDetailsService = apiStackUserDetailsService;
    }

    @RequestMapping(value = "/secure/token/refresh", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> refreshToken(HttpServletRequest request) throws IOException, ServletException {
        Authentication authentication = tokenAuthenticationService.getAuthentication(request);
        JwtToken newToken = tokenAuthenticationService.createRefreshToken(authentication.getName());
        UserDetails userDetails = apiStackUserDetailsService.loadUserByUsername(authentication.getName());

        return ApiStackAuthenticationSuccessHandler.buildTokenResponse((User) userDetails, newToken);
    }

    @RequestMapping(value = "/secure/user/password", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updatePassword(@Valid @RequestBody NewPassword newPassword, HttpServletRequest request) throws IOException, ServletException {
        Authentication authentication = tokenAuthenticationService.getAuthentication(request);
        UserDetails userDetails = apiStackUserDetailsService.loadUserByUsername(authentication.getName());
        JwtToken newToken = tokenAuthenticationService.createRefreshToken(authentication.getName());

        apiStackUserDetailsService.changePassword(userDetails.getUsername(), newPassword);

        return ApiStackAuthenticationSuccessHandler.buildTokenResponse((User) userDetails, newToken);
    }
}
