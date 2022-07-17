package com.lemon.coupon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.coupon.bo.BannerWithItemsBO;
import com.lemon.coupon.dto.BannerDTO;
import com.lemon.coupon.pojo.Banner;
import com.lemon.coupon.service.BannerService;
import com.lemon.exception.NotFoundException;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName BannerController
 **/
@RestController
@RequestMapping("/banner")
@PermissionModule(value = "Banner")
@Validated
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Banner> getByName(@PathVariable(name = "name") @NotBlank String name) {
        Banner banner = bannerService.getByName(name);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return ResponseEntity.ok(banner);
    }

    @PostMapping("")
    @PermissionMeta(value = "创建Banner")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@RequestBody @Validated BannerDTO dto) {
        Banner bannerDO = new Banner();
        BeanUtils.copyProperties(dto, bannerDO);
        this.bannerService.save(bannerDO);
        return ResponseEntity.ok(new CreatedVO());
    }


    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive Long id) {
        bannerService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    @PermissionMeta(value = "查询Banner",mount = false)
    public ResponseEntity<BannerWithItemsBO> getWithItems(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(bannerService.getWithItems(id));
    }


    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner")
    @GroupRequired
    @Logger(template = "{user.username}更新了Banner数据")
    public ResponseEntity<UpdatedVO> update(@RequestBody @Validated BannerDTO dto,
                                            @PathVariable @Positive Long id) {
        bannerService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Banner>> getBanners(@RequestParam(required = false, defaultValue = "0")
                                             @Min(value = 0) Integer page,
                                             @RequestParam(required = false, defaultValue = "10")
                                             @Min(value = 1) @Max(value = 30) Integer count) {

        Page<Banner> pager = new Page<>(page, count);
        IPage<Banner> paging = this.bannerService.getBaseMapper().selectPage(pager, null);

        return ResponseEntity.ok(new PageResponseVO<>((int)paging.getTotal(), paging.getRecords(), (int)paging.getCurrent(), (int)paging.getSize()));
    }
}
