package com.huzihan.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huzihan.code.properties.LoginType;
import com.huzihan.code.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 */
@Component
public class BrowserAuthenticationSuccessHanlder extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");

        if(LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            //默认父类的方法是跳转
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
