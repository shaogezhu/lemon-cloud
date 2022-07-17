package com.lemon.goods.controller;

import com.lemon.advice.mybatis.Page;
import com.lemon.goods.bo.SkuMessageBO;
import com.lemon.goods.dto.SkuDTO;
import com.lemon.goods.pojo.Sku;
import com.lemon.goods.service.SkuBackService;
import com.lemon.goods.service.SkuService;
import com.lemon.goods.service.SkuSpecService;
import com.lemon.goods.vo.SkuDetailVO;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SkuController
 **/
@RequestMapping("/sku")
@RestController
@PermissionModule("SKU")
public class SkuController {
    @Autowired
    private SkuService skuService;
    @Autowired
    private SkuBackService skuBackService;

    @GetMapping("")
    public ResponseEntity<List<Sku>> getSkuListInIds(@RequestParam(name = "ids", required = false) String ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        List<Sku> skus = skuService.listByIds(idList);

        return ResponseEntity.ok(skus);
    }


    @RequestMapping(value = "/list")
    public ResponseEntity<List<Sku>> getSkuListByIds(Long[] skuIdList) {
        if (skuIdList == null){
            return ResponseEntity.ok(Collections.emptyList());
        }
        if (CollectionUtils.isEmpty(Arrays.asList(skuIdList))) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Sku> skus = skuService.listByIds(Arrays.asList(skuIdList));
        return ResponseEntity.ok(skus);
    }

    @RequestMapping("/reduce")
    public ResponseEntity<Integer> reduceStore(Long sid, Integer count) {
        Integer res = skuService.reduceStockBySkuId(sid, count);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/return/back")
    public void returnBack(@RequestBody SkuMessageBO skuMessageBO){
        skuBackService.returnBack(skuMessageBO);
    }


    @Autowired
    private SkuSpecService skuSpecService;

    @PostMapping("")
    @PermissionMeta("创建SKU")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@RequestBody @Validated SkuDTO dto) {
        skuService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SKU")
    @GroupRequired
    public ResponseEntity<UpdatedVO> update(@RequestBody @Validated SkuDTO dto,
                                            @PathVariable @Positive(message = "{id.positive}") Long id) {
        skuService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SKU")
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        skuService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ResponseEntity<SkuDetailVO> getDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return ResponseEntity.ok(skuService.getDetail(id));
    }

    @GetMapping("/by/spu/{id}")
    @LoginRequired
    public ResponseEntity<List<Sku>> getBySpuId(@PathVariable(value = "id") @Positive Long spuId) {
        return ResponseEntity.ok(this.skuService.lambdaQuery().eq(Sku::getSpuId, spuId).list());
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Sku>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        System.out.println(page);
        System.out.println(count);
        Page<Sku> paging = skuService.page(new Page<Sku>(page, count));
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/spec-value-id")
    @LoginRequired
    public ResponseEntity<Map<String, Long>> getSpecValueId(
            @RequestParam(name = "key_id", required = false)
            @Positive(message = "{id}") Long keyId,
            @RequestParam(name = "sku_id", required = false)
            @Positive(message = "{id}") Long skuId
    ) {
        // 在spu下选择 spec_key 后，在相关 sku 在spec_key下选择 spec_value
        Long specValueId = skuSpecService.getSpecValueId(keyId, skuId);
        HashMap<String, Long> result = new HashMap<>(1);
        result.put("value_id", specValueId);
        return ResponseEntity.ok(result);
    }

}
