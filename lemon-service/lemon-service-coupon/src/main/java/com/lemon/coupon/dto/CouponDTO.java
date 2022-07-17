package com.lemon.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CouponDTO
 **/
@Data
public class CouponDTO extends CouponTemplateDTO {

    @Positive
    @NotNull
    private Long activityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull
    private Date endTime;

    @NotNull
    private String remark;

    private List<Long> categoryIds;

}

