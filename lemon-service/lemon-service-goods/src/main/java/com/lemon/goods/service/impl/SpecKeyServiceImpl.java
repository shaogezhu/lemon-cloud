package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.goods.bo.SpecKeyAndItemsBO;
import com.lemon.goods.dto.SpecKeyDTO;
import com.lemon.goods.mapper.SkuMapper;
import com.lemon.goods.mapper.SkuSpecMapper;
import com.lemon.goods.mapper.SpecKeyMapper;
import com.lemon.goods.mapper.SpecValueMapper;
import com.lemon.goods.pojo.*;
import com.lemon.goods.service.SpecKeyService;
import com.lemon.exception.ForbiddenException;
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
 * @ClassName SpecKeyServiceImpl
 **/
@Service
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKey> implements SpecKeyService {
    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecKeyMapper specKeyMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void create(SpecKeyDTO dto) {
        // 不可创建重复的规格名
        QueryWrapper<SpecKey> wrapper = new QueryWrapper();
        wrapper.lambda().eq(SpecKey::getName, dto.getName());
        SpecKey existed = this.getOne(wrapper);
        if (existed != null) {
            throw new ForbiddenException(60000);
        }
        SpecKey specKey = new SpecKey();
        BeanUtils.copyProperties(dto, specKey);
        this.save(specKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SpecKeyDTO dto, Long id) {
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        BeanUtils.copyProperties(dto, specKey);
        this.updateById(specKey);
        // 更新 sku 的 specs 字段
        List<Long> skuIds = skuSpecMapper.getSkuIdsByKeyId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<Sku> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getKeyId, id);
            wrapper.lambda().eq(SkuSpec::getSkuId, sku.getId());
            List<SkuSpec> skuSpecs = skuSpecMapper.selectList(wrapper);
            ArrayList<Spec> specs = new ArrayList<>();
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
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public SpecKeyAndItemsBO getKeyAndValuesById(Long id) {
        SpecKey specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        QueryWrapper<SpecValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpecValue::getSpecId, specKey.getId());
        List<SpecValue> items = specValueMapper.selectList(wrapper);
        SpecKeyAndItemsBO specKeyAndItems = new SpecKeyAndItemsBO(specKey, items);
        return specKeyAndItems;
    }

    @Override
    public List<SpecKey> getBySpuId(Long spuId) {
        return this.baseMapper.getBySpuId(spuId);
    }

}
