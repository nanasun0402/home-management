package com.caring.wxrs;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author james
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private final static Logger log = Logger.getLogger(TongxinWxRSApplication.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api() {
        log.info("Swagger startup");
        Parameter headerParam = new ParameterBuilder().name("X-MG-AUTH-TOKEN").parameterType("header")
                .modelRef(new ModelRef("string")).description("Authentication").required(false).build();

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Arrays.asList(headerParam))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.caring"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
