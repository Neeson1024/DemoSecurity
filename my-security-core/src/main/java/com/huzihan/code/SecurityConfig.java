package com.huzihan.code;

import com.huzihan.code.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
}
