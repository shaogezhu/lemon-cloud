package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.dto.SpecValueDTO;
import com.lemon.goods.mapper.SkuMapper;
import com.lemon.goods.mapper.SkuSpecMapper;
import com.lemon.goods.mapper.SpecValueMapper;
import com.lemon.goods.pojo.Sku;
import com.lemon.goods.pojo.SkuSpec;
import com.lemon.goods.pojo.Spec;
import com.lemon.goods.pojo.SpecValue;
import com.lemon.goods.service.SpecValueService;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecValueServiceImpl
 **/
@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValue> implements SpecValueService {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void create(SpecValueDTO dto) {
        SpecValue specValue = new SpecValue();
        BeanUtils.copyProperties(dto, specValue);
        this.save(specValue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SpecValueDTO dto, Long id) {
        SpecValue specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        BeanUtils.copyProperties(dto, specValue);
        this.updateById(specValue);

        List<Long> skuIds = skuSpecMapper.getSkuIdsByValueId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<Sku> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getValueId, id);
            wrapper.lambda().eq(SkuSpec::getSkuId, sku.getId());
            List<SkuSpec> skuSpecs = skuSpecMapper.selectList(wrapper);
            List<Spec> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                Spec specKeyAndValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(), skuSpec.getValueId());
                specs.add(specKeyAndValue);
            });
            sku.setSpecs(specs);
            skuMapper.updateById(sku);
        });
    }

    @Override
    public void delete(Long id) {
        SpecValue specValue = this.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public Spec getSpecKeyAndValueById(Long keyId, Long valueId) {
        return specValueMapper.getSpecKeyAndValueById(keyId, valueId);
    }
}
