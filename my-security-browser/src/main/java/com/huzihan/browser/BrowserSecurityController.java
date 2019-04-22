package com.huzihan.browser;

import com.huzihan.browser.support.SimpleResponse;
import com.huzihan.code.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //当引发跳转的时候，SpringSecurity会把请求放到HttpSessionRequestCache这里
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份验证时，我们跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //拿到引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                //跳转
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowserProperties().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份验证");
    }

}
