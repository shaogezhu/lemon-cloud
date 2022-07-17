package com.lemon.oauth.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName DispatchPermissionsDTO
 **/
@Data
public class DispatchPermissionsDTO {

    @Positive(message = "{group.id.positive}")
    @NotNull(message = "{group.id.not-null}")
    private Long groupId;

    private List<Long> permissionIds;
}

