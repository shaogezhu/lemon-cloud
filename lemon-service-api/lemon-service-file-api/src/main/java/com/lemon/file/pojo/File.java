package com.lemon.file.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lemon.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName File
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("lemon_file")
public class File extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -320329365635201256L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 真实url
     */
    @TableField(exist = false)
    private String url;

    /**
     * 前端上传的key
     */
    @TableField(exist = false)
    private String key;

    /**
     * 若 local，表示文件路径
     */
    private String path;

    /**
     * LOCAL REMOTE
     */
    private String type;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 扩展名，例：.jpg
     */
    private String extension;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * md5值，防止上传重复文件
     */
    private String md5;
}
