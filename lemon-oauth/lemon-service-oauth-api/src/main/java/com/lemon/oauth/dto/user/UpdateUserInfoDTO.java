package com.lemon.oauth.dto.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UpdateUserInfoDTO
 **/
@Data
public class UpdateUserInfoDTO {

    @NotEmpty(message = "{group.ids.not-empty}")
    private List<@Min(1) Long> groupIds;

}
