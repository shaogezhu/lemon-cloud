package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.mapper.SpuKeyMapper;
import com.lemon.goods.pojo.SpuKey;
import com.lemon.goods.service.SpuKeyService;
import org.springframework.stereotype.Service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuKeyServiceImpl
 **/
@Service
public class SpuKeyServiceImpl extends ServiceImpl<SpuKeyMapper, SpuKey> implements SpuKeyService {
}

