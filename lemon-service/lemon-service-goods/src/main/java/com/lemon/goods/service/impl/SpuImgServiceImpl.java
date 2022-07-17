package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.mapper.SpuImgMapper;
import com.lemon.goods.pojo.SpuImg;
import com.lemon.goods.service.SpuImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuImgServiceImpl
 **/
@Service
public class SpuImgServiceImpl extends ServiceImpl<SpuImgMapper, SpuImg> implements SpuImgService {
    @Autowired
    private SpuImgMapper spuImgMapper;
    @Override
    public void deleteImgsBySpuId(Long id) {
        spuImgMapper.hardDeleteImgsBySpuId(id);
    }
}
