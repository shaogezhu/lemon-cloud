package com.lemon.oauth.dto.user;

import com.lemon.oauth.validator.EqualField;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ChangePasswordDTO
 **/
@Data
@NoArgsConstructor
@EqualField(srcField = "newPassword", dstField = "confirmPassword", message = "{password.equal-field}")
public class ChangePasswordDTO {

    @NotBlank(message = "{password.new.not-blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.new.pattern}")
    private String newPassword;

    @NotBlank(message = "{password.confirm.not-blank}")
    private String confirmPassword;

    @NotBlank(message = "{password.old.not-blank}")
    private String oldPassword;

}