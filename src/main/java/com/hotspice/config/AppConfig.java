package com.hotspice.config;


import org.springframework.context.annotation.*;

/**
 * Created by Geetanshu on 29/04/16.
 */


@Configuration
@ComponentScan(basePackages = "com.hotspice.*")
@PropertySources({
        @PropertySource("classpath:es.properties"),
        @PropertySource("classpath:redis.properties"),
})
public class AppConfig {

}
