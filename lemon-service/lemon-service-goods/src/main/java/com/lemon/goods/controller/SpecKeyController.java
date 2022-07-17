package com.lemon.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.goods.bo.SpecKeyAndItemsBO;
import com.lemon.goods.dto.SpecKeyDTO;
import com.lemon.goods.pojo.SpecKey;
import com.lemon.goods.service.SpecKeyService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKeyController
 **/
@RestController
@RequestMapping("/spec-key")
@PermissionModule("规格名")
@Validated
public class SpecKeyController {

    @Autowired
    private SpecKeyService specKeyService;

    @PostMapping("")
    @PermissionMeta(value = "创建规格名")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody SpecKeyDTO dto) {
        specKeyService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新规格名")
    @GroupRequired
    public ResponseEntity<UpdatedVO> update(
            @Validated @RequestBody SpecKeyDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        specKeyService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除规格名")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        specKeyService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ResponseEntity<SpecKeyAndItemsBO> detail(@PathVariable @Positive(message = "{id}") Long id) {
        SpecKeyAndItemsBO specKeyAndItems = specKeyService.getKeyAndValuesById(id);
        return ResponseEntity.ok(specKeyAndItems);
    }

    @GetMapping("/by/spu/{id}")
    public ResponseEntity<List<SpecKey>> getBySpuId(@PathVariable(value = "id") @Positive Long spuId) {
        return ResponseEntity.ok(this.specKeyService.getBySpuId(spuId));
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponseVO<SpecKey>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<SpecKey> pager = new Page<>(page, count);
        IPage<SpecKey> paging = specKeyService.getBaseMapper().selectPage(pager, null);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/list")
    @LoginRequired
    public ResponseEntity<List<SpecKey>> getList() {
        return ResponseEntity.ok(specKeyService.list());
    }

}