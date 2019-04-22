package com.huzihan.validate.core;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ImageCode {

    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public ImageCode(BufferedImage image, String code, int expireTimeIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTimeIn);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
