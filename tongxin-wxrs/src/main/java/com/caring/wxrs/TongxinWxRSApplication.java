package com.caring.wxrs;

import com.caring.wxrs.security.JwtFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author james
 */
@ComponentScan("com.caring")
@ServletComponentScan("com.caring")
@SpringBootApplication
public class TongxinWxRSApplication {

    private final static Logger log = Logger.getLogger(TongxinWxRSApplication.class);

    @Autowired
    private ApplicationContext context;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(context, "customer", "const", "wechat", "register", "login", "xtoken"));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(TongxinWxRSApplication.class, args);
        log.debug("Care Plus WX REST Server is completed");
    }

}
