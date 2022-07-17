package com.lemon.goods.controller;

import com.lemon.goods.dto.SpecValueDTO;
import com.lemon.goods.pojo.SpecValue;
import com.lemon.goods.service.SpecValueService;
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
 * @ClassName SpecValueController
 **/
@RestController
@RequestMapping("/spec-value")
@Validated
@PermissionModule("规格值")
public class SpecValueController {

    @Autowired
    private SpecValueService specValueService;


    @PostMapping("")
    @PermissionMeta("创建规格值")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody SpecValueDTO dto) {
        specValueService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新规格值")
    @GroupRequired
    public ResponseEntity<UpdatedVO> update(
            @Validated @RequestBody SpecValueDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        specValueService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除规格值")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        specValueService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ResponseEntity<SpecValue> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        SpecValue specValue = specValueService.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        return ResponseEntity.ok(specValue);
    }


    @GetMapping("/by/spec-key/{id}")
    public ResponseEntity<List<SpecValue>> getBySpecKeyId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return ResponseEntity.ok(this.specValueService.lambdaQuery().eq(SpecValue::getSpecId, id).list());
    }

}