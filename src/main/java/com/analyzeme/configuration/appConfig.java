package com.analyzeme.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ольга on 28.02.2016.
 */
@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "controllers")
public class appConfig {
}

