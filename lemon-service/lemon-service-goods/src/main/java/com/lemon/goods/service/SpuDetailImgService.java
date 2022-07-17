package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.pojo.SpuDetailImg;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuDetailImgService
 **/
public interface SpuDetailImgService extends IService<SpuDetailImg> {
    void deleteImgsBySpuId(Long id);
}
