package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.ParameterException;
import com.lemon.goods.dto.SkuDTO;
import com.lemon.goods.dto.SkuSelector;
import com.lemon.goods.mapper.SkuMapper;
import com.lemon.goods.pojo.Sku;
import com.lemon.goods.pojo.SkuSpec;
import com.lemon.goods.pojo.Spec;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.service.SkuService;
import com.lemon.goods.service.SkuSpecService;
import com.lemon.goods.service.SpecValueService;
import com.lemon.goods.service.SpuService;
import com.lemon.goods.vo.SkuDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuServiceImpl
 **/
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Resource
    private SkuMapper skuMapper;
    @Override
    public Integer reduceStockBySkuId(Long sid, Integer count) {
        return skuMapper.reduceStockBySkuId(sid,count);
    }

    @Autowired
    private SpuService spuService;

    @Autowired
    private SpecValueService specValueService;

    @Autowired
    private SkuSpecService skuSpecService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SkuDTO dto) {
        // 1. 检测数据
        QueryWrapper<Sku> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Sku::getTitle, dto.getTitle());
        int count = this.count(wrapper);
        if (count > 0) {
            throw new ForbiddenException(80000);
        }
        Spu spu = spuService.getById(dto.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = dto.getSelectors();
        List<Spec> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        Sku sku = new Sku();
        BeanUtils.copyProperties(dto, sku);
        String code = this.generateSkuCode(selectors, dto.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.save(sku);
        // 3. 存储信息到关联表中
        this.insertSpecs(specs, dto.getSpuId(), sku.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SkuDTO dto, Long id) {
        // 1. 检测数据
        Spu spu = spuService.getById(dto.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = dto.getSelectors();
        List<Spec> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        Sku sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        BeanUtils.copyProperties(dto, sku);
        String code = this.generateSkuCode(selectors, dto.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.updateById(sku);
        // 3.先删除关联信息，再存储信息到关联表中
        skuSpecService.deleteSpecs(sku.getSpuId(), sku.getId());
        this.insertSpecs(specs, dto.getSpuId(), sku.getId());
    }

    @Override
    public void delete(Long id) {
        // 删除 sku
        Sku sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        this.getBaseMapper().deleteById(id);
        // 删除 sku_spec
        skuSpecService.deleteSpecs(sku.getSpuId(), sku.getId());
    }

    @Override
    public SkuDetailVO getDetail(Long id) {
        return skuMapper.getDetail(id);
    }

    private List<Spec> checkSelectors(List<SkuSelector> selectors) {
        List<Spec> specs = new ArrayList<>();
        for (SkuSelector selector : selectors) {
            Spec specKeyAndValue = specValueService.getSpecKeyAndValueById(selector.getKeyId(), selector.getValueId());
            if (specKeyAndValue == null) {
                return null;
            }
            specs.add(specKeyAndValue);
        }
        return specs;
    }

    /**
     * 向sku_specs 表中插入数据
     */
    private void insertSpecs(List<Spec> specs, Long spuId, Long skuId) {
        ArrayList<SkuSpec> skuSpecList = new ArrayList<>();
        specs.forEach(spec -> {
            SkuSpec skuSpec = new SkuSpec();
            skuSpec.setSpuId(spuId);
            skuSpec.setSkuId(skuId);
            skuSpec.setKeyId(spec.getKeyId());
            skuSpec.setValueId(spec.getValueId());
            skuSpecList.add(skuSpec);
        });
        skuSpecService.saveBatch(skuSpecList);
    }

    private String generateSkuCode(List<SkuSelector> selectors, Long spuId) {
        // 调整：sku的code 调整成$分隔spu和sku，#分隔sku片段
//        selectors.sort((o1, o2) -> (int) (o1.getKeyId() - o2.getKeyId()));
        StringBuilder builder = new StringBuilder();
        builder.append(spuId);
        builder.append("$");
        for (int i = 0; i < selectors.size(); i++) {
            SkuSelector skuSelector = selectors.get(i);
            builder.append(skuSelector.getKeyId());
            builder.append("-");
            builder.append(skuSelector.getValueId());
            if (i < selectors.size() - 1) {
                builder.append("#");
            }
        }
        // blob law
        return builder.toString();
    }
}
