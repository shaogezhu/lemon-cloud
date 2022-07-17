package com.lemon.coupon.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lemon.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityCover
 **/
@Getter
@Setter
public class ActivityCover extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1651143441L;
    @TableId
    private Long id;
    private String coverImg;
    private String internalTopImg;
    private String name;
    private String title;
    private String description;
    private Integer online;

    private List<Activity> activityList;
}