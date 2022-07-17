package com.lemon.oauth.pojo.cms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import com.lemon.enumeration.GroupLevelEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GroupDO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "lemon_group")
@EqualsAndHashCode(callSuper = true)
public class GroupDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8994898895671436007L;
    @TableId(value = "id")
    private Long id;
    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    /**
     * 分组级别（root、guest、user，其中 root、guest 分组只能存在一个）
     */
    @TableField(value = "`level`")
    private GroupLevelEnum level;

}
