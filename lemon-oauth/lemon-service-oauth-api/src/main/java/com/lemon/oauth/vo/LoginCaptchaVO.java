package com.lemon.oauth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoginCaptchaVO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCaptchaVO {
    /**
     * 加密后的验证码
     */
    private String tag;
    /**
     * 验证码图片地址，可使用base64
     */
    private String image;
}
