package com.huzihan.web.config;

import com.huzihan.web.filter.TimeFilter;
import com.huzihan.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class webConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 把Interceptor(TimeInterceptor)加入到拦截器链上
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(timeInterceptor);
    }

    //@Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        filterRegistrationBean.setFilter(timeFilter);
        //用这种配置可以指定这个filter可以在哪些地址中生效
        List<String> urls = new ArrayList<>();
        //对所有请求进行生效
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }
}
