package com.lemon.oauth.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Client
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lemon_client_info")
public class ClientInfo {

    @TableId
    private Long id;

    @TableField("client_id")
    private String clientId;

    private String secret;

    private String info;

}
