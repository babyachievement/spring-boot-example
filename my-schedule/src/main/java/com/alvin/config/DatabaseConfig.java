package com.alvin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Administrator on 2017/4/17.
 */
@Configuration
@PropertySource("classpath:server.properties")
public class DatabaseConfig {
}
