package com.huzihan.validate.core.sms;

import com.huzihan.code.properties.SecurityProperties;
import com.huzihan.validate.core.ValidateCode;
import com.huzihan.validate.core.ValidateCodeException;
import com.huzihan.validate.core.ValidateCodeProcessor;
import com.huzihan.validate.core.image.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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
import java.util.HashSet;
import java.util.Set;

/**
 * OncePerRequestFilter:Spring里的工具类，保证我们的过滤器会被执行一次
 */
public class SmsValidateFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if(StringUtils.isNoneBlank(securityProperties.getValidateCode().getSms().getUrls())) {
            String[] configUrls = securityProperties.getValidateCode().getSms().getUrls().split(",");
            for (String url : configUrls) {
                urls.add(url);
            }
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for(String url : urls){
            if(pathMatcher.match(url,httpServletRequest.getRequestURI())){
                action = true;
                break;
            }
        }

        if(action){

            try {
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /**
     * 验证逻辑
     * @param request
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request,
                ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession.isExpired()){
            throw new ValidateCodeException("验证码已经过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("验证码不一致");
        }

        sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
