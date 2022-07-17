package com.lemon.oauth.dto.token;

import com.lemon.enumeration.LoginType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TokenGetDTO
 **/
@Getter
@Setter
public class TokenGetDTO {
    @NotBlank(message = "account不允许为空")
    private String account;
    @Length(min = 6,max=18, message = "{token.password}")
    private String password;

    private LoginType type;
}