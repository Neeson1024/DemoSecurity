package com.huzihan.validate.core;

import com.huzihan.code.properties.SecurityConstants;
import com.huzihan.code.properties.SecurityProperties;
import com.huzihan.validate.core.image.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String,ValidateCodeType> urls = new HashMap();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 系统中的校验码处理器
     */
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getValidateCode().getImageCode().getUrls(),ValidateCodeType.IMAGE);

        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getValidateCode().getSms().getUrls(),ValidateCodeType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString,ValidateCodeType type){
        if(StringUtils.isNotBlank(urlString)){
            String[] configUrls = securityProperties.getValidateCode().getImageCode().getUrls().split(",");
            for(String url : configUrls){
                urls.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(httpServletRequest);
        if(type != null){
            logger.info("校验请求(" + httpServletRequest.getRequestURI() + ")中的验证码,验证码类型" + type);

            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
            }catch(ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest httpServletRequest) {
        ValidateCodeType result = null;
        if(!StringUtils.equalsAnyIgnoreCase(httpServletRequest.getMethod(),"get")){
            Set<String> url_set = urls.keySet();
            for(String url : url_set){
                if(pathMatcher.match(url,httpServletRequest.getRequestURI())){
                    result = urls.get(url);
                }
            }

        }
        return null;
    }



}
