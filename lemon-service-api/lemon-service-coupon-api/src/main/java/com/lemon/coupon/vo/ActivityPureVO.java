package com.lemon.coupon.vo;

import com.lemon.coupon.pojo.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName ActivityPureVO
 * @author shaogezhu
 * @version 1.0.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVO {
    private Long id;
    private String title;
    private String entranceImg;
    private Integer online;
    private String remark;
    private String startTime;
    private String endTime;

    public ActivityPureVO(Activity activity) {
        BeanUtils.copyProperties(activity,this);
    }

    public ActivityPureVO(Object object){
        BeanUtils.copyProperties(object, this);
    }

}