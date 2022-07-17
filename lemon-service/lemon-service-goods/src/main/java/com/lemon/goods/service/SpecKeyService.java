package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.bo.SpecKeyAndItemsBO;
import com.lemon.goods.dto.SpecKeyDTO;
import com.lemon.goods.pojo.SpecKey;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKeyService
 **/
public interface SpecKeyService extends IService<SpecKey> {

    void create(SpecKeyDTO dto);

    void update(SpecKeyDTO dto, Long id);

    void delete(Long id);

    List<SpecKey> getBySpuId(Long spuId);

    SpecKeyAndItemsBO getKeyAndValuesById(Long id);
}
