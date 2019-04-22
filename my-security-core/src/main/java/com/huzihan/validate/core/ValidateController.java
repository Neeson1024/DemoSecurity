package com.huzihan.validate.core;

import com.huzihan.code.properties.SecurityConstants;
import com.huzihan.code.properties.SecurityProperties;
import com.huzihan.validate.core.image.ImageCode;
import com.huzihan.validate.core.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateController {

    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void craeteCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
       validateCodeProcessors.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(request,response));
    }

}
