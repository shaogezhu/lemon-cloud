package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.mapper.TagMapper;
import com.lemon.goods.pojo.Tag;
import com.lemon.goods.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TagServiceImpl
 **/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private  TagMapper tagMapper;

    @Override
    public List<Tag> getTagsByType(Integer type) {
        return tagMapper.selectList(new QueryWrapper<Tag>().lambda().eq(Tag::getType, type));
    }
}
