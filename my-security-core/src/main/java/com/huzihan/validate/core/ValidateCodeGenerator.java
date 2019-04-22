package com.huzihan.validate.core;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

/**
 * 验证码生成
 */
public interface ValidateCodeGenerator {

    ImageCode generator(ServletWebRequest request);
}
