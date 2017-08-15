package com.tw.apistack.base.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * Created by jxzhong on 2017/8/13.
 */

@ConfigurationProperties(prefix = "authentication", ignoreUnknownFields = false)
public class AuthenticationProperties {

    private Jwt jwt;

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    @ConfigurationProperties(prefix = "jwt")
    public static class Jwt {
        @NotNull
        private long expire;
        @NotNull
        private String secret;
        @NotNull
        private String tokenPrefix;
        @NotNull
        private String header;

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getTokenPrefix() {
            return tokenPrefix;
        }

        public void setTokenPrefix(String tokenPrefix) {
            this.tokenPrefix = tokenPrefix;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }
}
