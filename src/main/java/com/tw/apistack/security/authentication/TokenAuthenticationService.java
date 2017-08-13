package com.tw.apistack.security.authentication;

import com.tw.apistack.security.authrization.ApiStackUserDetailsService;
import com.tw.apistack.security.config.AuthenticationProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
@EnableConfigurationProperties(AuthenticationProperties.class)
public class TokenAuthenticationService {

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private ApiStackUserDetailsService userDetailsService;

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(authenticationProperties.getJwt().getHeader());
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(authenticationProperties.getJwt().getSecret())
                    .parseClaimsJws(token.replace(authenticationProperties.getJwt().getTokenPrefix(), ""))
                    .getBody()
                    .getSubject();

            UserDetails u = userDetailsService.loadUserByUsername(user);

            return new UsernamePasswordAuthenticationToken(u.getUsername(), null, u.getAuthorities());
        }
        return null;
    }

    public JwtToken createAccessJwtToken(String userContext) {
        String jwt = generateJWT(userContext);
        return new JwtToken(jwt);
    }

    public JwtToken createRefreshToken(String userContext) {
        String jwt = generateJWT(userContext);
        return new JwtToken(jwt);
    }

    private String generateJWT(String username) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + authenticationProperties.getJwt().getExpire()))
                .signWith(SignatureAlgorithm.HS512, authenticationProperties.getJwt().getSecret())
                .compact();
    }
}
