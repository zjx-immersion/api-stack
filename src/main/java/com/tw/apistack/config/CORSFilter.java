package com.tw.apistack.config;

import com.tw.apistack.config.properties.CORSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by jxzhong on 2017/7/3.
 */


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(CORSProperties.class)
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Autowired
    private CORSProperties corsProperties;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;

        response.setHeader("Access-Control-Allow-Origin",
                String.join(",", corsProperties.getAllowedOrigins()));
        response.setHeader("Access-Control-Allow-Methods",
                String.join(",", corsProperties.getAllowedMethods()));
        response.setHeader("Access-Control-Max-Age",
                corsProperties.getMaxAge().toString());
        response.setHeader("Access-Control-Allow-Headers",
                String.join(",", corsProperties.getAllowedHeaders()));
        response.setHeader("Access-Control-Allow-Credentials",
                corsProperties.isAllowCredentials().toString());

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}

//@Configuration
//@EnableConfigurationProperties(CORSProperties.class)
//public class CORSFilter {
//
//    @Autowired
//    private CORSProperties corsProperties;
//
//
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = initCorsConfiguration();
//        System.out.println("==========================================================");
//        System.out.println(config.getAllowCredentials());
//        System.out.println(config.getAllowedHeaders());
//        System.out.println(config.getAllowedMethods());
//        System.out.println(config.getAllowedOrigins());
//        System.out.println(config.getMaxAge());
////        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
////            source.registerCorsConfiguration("/api/**", config);
////            source.registerCorsConfiguration("/v2/api-docs", config);
////            source.registerCorsConfiguration("/oauth/**", config);
////            System.out.println("Init CORS Configuration");
////            System.out.println("==========================================================");
////        }
//
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }
//
//    private CorsConfiguration initCorsConfiguration() {
//        CorsConfiguration cors = new CorsConfiguration();
//        cors.setAllowCredentials(corsProperties.isAllowCredentials());
//        cors.setAllowedHeaders(corsProperties.getAllowedHeaders());
//        cors.setAllowedMethods(corsProperties.getAllowedMethods());
//        cors.setAllowedOrigins(corsProperties.getAllowedOrigins());
//        cors.setMaxAge(corsProperties.getMaxAge());
//        return cors;
//    }
//
//}
