package com.lemon.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.file.pojo.File;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName FileMapper
 **/
public interface FileMapper extends BaseMapper<File> {
    File selectByMd5(String md5);

    Integer selectCountByMd5(String md5);
}
