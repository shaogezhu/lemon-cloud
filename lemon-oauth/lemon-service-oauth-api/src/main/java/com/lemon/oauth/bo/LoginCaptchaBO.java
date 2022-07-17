package com.lemon.oauth.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoginCaptchaBO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCaptchaBO {
    private String captcha;
    private Long expired;
}
