package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LogDO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lemon_log")
public class LogDO implements Serializable {

    private static final long serialVersionUID = -7471474245124682011L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

    private String message;

    private Long userId;

    private String username;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

}
