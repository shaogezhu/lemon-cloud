package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.pojo.Tag;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TagServce
 **/
public interface TagService extends IService<Tag> {
    List<Tag> getTagsByType(Integer type);
}
