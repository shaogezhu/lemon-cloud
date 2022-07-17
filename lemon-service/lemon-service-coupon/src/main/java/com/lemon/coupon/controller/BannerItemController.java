package com.lemon.coupon.controller;

import com.lemon.coupon.dto.BannerItemDTO;
import com.lemon.coupon.pojo.BannerItem;
import com.lemon.coupon.service.BannerItemService;
import com.lemon.exception.NotFoundException;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.Logger;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerItemController
 **/
@RestController
@RequestMapping("/banner-item")
@PermissionModule("Banner item")
public class BannerItemController {

    @Autowired
    private BannerItemService bannerItemService;

    @PostMapping("")
    @PermissionMeta(value = "创建Banner item")
    @Logger(template = "{user.username}创建了 Banner item")
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody BannerItemDTO dto) {
        BannerItem bannerItem = new BannerItem();
        System.out.println(dto.toString());
        BeanUtils.copyProperties(dto, bannerItem);
        System.out.println(bannerItem.toString());
        bannerItemService.save(bannerItem);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner item")
    @Logger(template = "{user.username}更新了 Banner item")
    public ResponseEntity<UpdatedVO> update(
            @PathVariable @Positive(message = "{id.positive}") Long id,
            @Validated @RequestBody BannerItemDTO dto) {
        bannerItemService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner item")
    @Logger(template = "{user.username}删除了 Banner item")
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        bannerItemService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @PermissionMeta(value = "查询Banner item")
    public ResponseEntity<BannerItem> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        BannerItem bannerItem = bannerItemService.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        return ResponseEntity.ok(bannerItem);
    }

}
