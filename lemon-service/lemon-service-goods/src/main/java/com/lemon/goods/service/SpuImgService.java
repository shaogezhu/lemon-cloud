package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.pojo.SpuImg;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuImgService
 **/
public interface SpuImgService extends IService<SpuImg> {
    void deleteImgsBySpuId(Long id);
}
