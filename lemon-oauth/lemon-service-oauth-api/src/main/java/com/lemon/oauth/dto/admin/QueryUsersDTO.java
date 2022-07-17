package com.lemon.oauth.dto.admin;

import com.lemon.oauth.dto.querry.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName QueryUsersDTO
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUsersDTO extends BasePageDTO {

    @Min(value = 1, message = "{group.id.positive}")
    private Long groupId;
}