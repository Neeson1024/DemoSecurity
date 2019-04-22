package com.huzihan.validate.core.image;

import com.huzihan.validate.core.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public ImageCode(BufferedImage image, String code, int expireTimeIn) {
        super(code,expireTimeIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


}
