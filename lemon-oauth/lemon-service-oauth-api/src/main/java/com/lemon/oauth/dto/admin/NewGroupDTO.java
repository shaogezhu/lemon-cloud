package com.lemon.oauth.dto.admin;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName NewGroupDTO
 **/
@Data
public class NewGroupDTO {
    @NotBlank(message = "{group.name.not-blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    @Length(max = 255, message = "{group.info.length}")
    private String info;

    private List<Long> permissionIds;
}
