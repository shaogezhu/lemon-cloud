package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.mapper.SpuDetailImgMapper;
import com.lemon.goods.pojo.SpuDetailImg;
import com.lemon.goods.service.SpuDetailImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDetailImgServiceImpl
 **/
@Service
public class SpuDetailImgServiceImpl extends ServiceImpl<SpuDetailImgMapper, SpuDetailImg> implements SpuDetailImgService {

    @Autowired
    private SpuDetailImgMapper spuDetailImgMapper;

    @Override
    public void deleteImgsBySpuId(Long id) {
        spuDetailImgMapper.hardDeleteImgsBySpuId(id);
    }
}
