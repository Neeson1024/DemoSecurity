package com.huzihan.validate.core.sms;

import com.huzihan.validate.core.ValidateCode;
import com.huzihan.validate.core.impl.AbStractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbStractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 短信验证码发送器
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile,validateCode.getCode());
    }
}
