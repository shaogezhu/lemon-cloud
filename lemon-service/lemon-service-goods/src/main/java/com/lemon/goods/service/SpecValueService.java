package com.lemon.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.dto.SpecValueDTO;
import com.lemon.goods.pojo.Spec;
import com.lemon.goods.pojo.SpecValue;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecValueService
 **/
public interface SpecValueService extends IService<SpecValue> {

    void create(SpecValueDTO dto);

    void update(SpecValueDTO dto, Long id);

    void delete(Long id);

    Spec getSpecKeyAndValueById(Long keyId, Long valueId);
}
