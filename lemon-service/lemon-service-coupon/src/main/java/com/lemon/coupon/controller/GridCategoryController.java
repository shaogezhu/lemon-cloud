package com.lemon.coupon.controller;

import com.lemon.coupon.dto.GridCategoryDTO;
import com.lemon.coupon.pojo.GridCategory;
import com.lemon.coupon.service.GridCategoryService;
import com.lemon.exception.NotFoundException;
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

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GridCategoryController
 **/
@RestController
@RequestMapping("/grid-category")
@Validated
@PermissionModule("六宫格")
public class GridCategoryController {

    @Autowired
    private GridCategoryService gridCategoryService;

    @PostMapping("")
    @PermissionMeta(value = "创建六宫格")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody GridCategoryDTO dto) {
        gridCategoryService.createGridCategory(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新六宫格")
    @GroupRequired
    public ResponseEntity<UpdatedVO> update(
            @Validated @RequestBody GridCategoryDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        gridCategoryService.updateGridCategory(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除六宫格")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        gridCategoryService.deleteGridCategory(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ResponseEntity<GridCategory> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        GridCategory gridCategory = gridCategoryService.getById(id);
        if (gridCategory == null) {
            throw new NotFoundException(50000);
        }
        return ResponseEntity.ok(gridCategory);
    }

    @GetMapping("/list")
    @LoginRequired
    public ResponseEntity<List<GridCategory>> getList() {
        return ResponseEntity.ok(gridCategoryService.list());
    }
}