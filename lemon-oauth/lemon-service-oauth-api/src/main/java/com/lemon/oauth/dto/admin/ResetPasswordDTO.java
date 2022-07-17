package com.lemon.oauth.dto.admin;

import com.lemon.oauth.validator.EqualField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ResetPasswordDTO
 **/
@EqualField(srcField = "newPassword", dstField = "confirmPassword", message = "{password.equal-field}")
@Data
public class ResetPasswordDTO {

    @NotBlank(message = "{password.new.not-blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.new.pattern}")
    private String newPassword;

    @NotBlank(message = "{password.confirm.not-blank}")
    private String confirmPassword;
}