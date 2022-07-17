package com.lemon.goods.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Spec
 **/
@Setter
@Getter
@Data
public class Spec implements Serializable {
    private static final long serialVersionUID = 8891488565683643643L;

    @JSONField(name = "key_id")
    @TableField("key_id")
    private Long keyId;

    private String key;

    @JSONField(name = "value_id")
    @TableField("value_id")
    private Long valueId;

    private String value;
}
