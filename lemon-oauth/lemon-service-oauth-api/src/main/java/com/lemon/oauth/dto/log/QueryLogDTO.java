package com.lemon.oauth.dto.log;

import com.lemon.oauth.dto.querry.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName QueryLogDTO
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryLogDTO extends BasePageDTO {

    protected static Integer defaultCount = 12;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    private String name;

    private String keyword;


}