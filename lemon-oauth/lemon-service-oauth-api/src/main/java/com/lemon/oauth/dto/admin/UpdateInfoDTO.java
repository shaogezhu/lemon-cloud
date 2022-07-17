package com.lemon.oauth.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UpdateInfoDTO
 **/
@NoArgsConstructor
@Data
public class UpdateInfoDTO {

    @Email(message = "{email}")
    private String email;

    @Length(min = 2, max = 10, message = "{nickname.length}")
    private String nickname;

    @Length(min = 2, max = 10, message = "{username.length}")
    private String username;

    @Length(max = 500, message = "{avatar.length}")
    private String avatar;
}
