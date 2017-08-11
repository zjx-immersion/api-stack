package com.tw.apistack.security.authentication;

import com.tw.apistack.security.authrization.ApiStackUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenAuthenticationService {
    @Value("${authentication.jwt.expire}")
    private long expirationTime; // 10 days
    @Value("${authentication.jwt.secret}")
    private String secret;
    @Value("${authentication.jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${authentication.jwt.header}")
    private String headerString;
    @Autowired
    private ApiStackUserDetailsService userDetailsService;

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
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
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
