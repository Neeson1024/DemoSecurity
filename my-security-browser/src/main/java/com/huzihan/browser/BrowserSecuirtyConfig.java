package com.huzihan.browser;

import com.huzihan.browser.authentication.BrowserAuthenticationFailureHandler;
import com.huzihan.browser.authentication.BrowserAuthenticationSuccessHanlder;
import com.huzihan.code.authentication.AbstractChannelSecurityConfig;
import com.huzihan.code.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.huzihan.code.properties.SecurityConstants;
import com.huzihan.code.properties.SecurityProperties;
import com.huzihan.validate.core.ValidateCodeSecurityConfig;
import com.huzihan.validate.core.sms.SmsValidateFilter;
import com.huzihan.validate.core.ValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecuirtyConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;

    @Autowired
    private BrowserAuthenticationSuccessHanlder browserAuthenticationSuccessHanlder;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //
    //    ValidateFilter validateFilter = new ValidateFilter();
    //    validateFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
    //    validateFilter.setSecurityProperties(securityProperties);
    //    validateFilter.afterPropertiesSet();
    //
    //    SmsValidateFilter smsValidateFilter = new SmsValidateFilter();
    //    smsValidateFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
    //    smsValidateFilter.setSecurityProperties(securityProperties);
    //    smsValidateFilter.afterPropertiesSet();
    //
    //
    //    http.addFilterBefore(validateFilter,UsernamePasswordAuthenticationFilter.class)
    //            .addFilterBefore(smsValidateFilter,UsernamePasswordAuthenticationFilter.class)
    //            .formLogin()
    //            .loginPage("/authentication/require")       //将请求页面地址变成处理服务地址
    //            .loginProcessingUrl("/authentication/form") //处理登录请求的url，让UsernamePasswordFilter去处理
    //            .successHandler(browserAuthenticationSuccessHanlder) //登录成功处理
    //            .failureHandler(browserAuthenticationFailureHandler) //登录失败处理
    //            .and()
    //            .authorizeRequests() //对请求做一个授权
    //            .antMatchers(securityProperties.getBrowserProperties().getLoginPage(),
    //                    "/authentication/require","/code/*").permitAll()
    //            .anyRequest()       //对任何请求
    //            .authenticated()   //都需要身份验证
    //            .and()
    //            .csrf().disable()//关闭csrf
    //            .apply(smsCodeAuthenticationSecurityConfig);
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowserProperties().getLoginPage(),
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE
                    ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

}
