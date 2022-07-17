package com.lemon.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.lemon.exception.NotFoundException;
import com.lemon.goods.dto.ThemeDTO;
import com.lemon.goods.dto.ThemeSpuDTO;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.Theme;
import com.lemon.goods.service.ThemeService;
import com.lemon.goods.vo.SpuSimplifyVO;
import com.lemon.goods.vo.ThemePureVO;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.vo.CreatedVO;
import com.lemon.vo.DeletedVO;
import com.lemon.vo.UpdatedVO;
import io.github.talelin.core.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ThemeController
 **/
@RestController
@RequestMapping("/theme")
@Validated
@PermissionModule("主题")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/by/names")
    public ResponseEntity<List<ThemePureVO>> getThemeGroupByNames(@RequestParam(name = "names") String names) {
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.listByIds(nameList);
        List<ThemePureVO> list = new ArrayList<>();
        themes.forEach(theme -> {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVO vo = mapper.map(theme, ThemePureVO.class);
            list.add(vo);
        });
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}/with_spu")
    public ResponseEntity<Theme> getThemeByNameWithSpu(@PathVariable(name = "name") String themeName){
        //Optional: 可能包含或不包含非空值的容器对象 提供依赖于存在或不存在包含值的其他方法，例如orElse() （如果值不存在则返回默认值）和ifPresent() （如果值存在则执行代码块）。
        Optional<Theme> optionalTheme = themeService.findByName(themeName);
        return ResponseEntity.ok(optionalTheme.orElseThrow(()-> new NotFoundException(30003)));
    }


    @PostMapping("")
    @PermissionMeta("创建主题")
    @GroupRequired
    @Logger(template = "{user.username}创建了，主题")
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody ThemeDTO dto) {
        Theme theme = new Theme();
        BeanUtils.copyProperties(dto, theme);
        themeService.save(theme);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新主题")
    @GroupRequired
    @Logger(template = "{user.username}更新了，主题")
    public ResponseEntity<UpdatedVO> update(
            @Validated @RequestBody ThemeDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        BeanUtils.copyProperties(dto, theme);
        themeService.updateById(theme);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除主题")
    @GroupRequired
    @Logger(template = "{user.username}删除了，主题")
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        themeService.getBaseMapper().deleteById(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ResponseEntity<Theme> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        return ResponseEntity.ok(theme);
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Theme>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Theme> pager = new Page<>(page, count);
        IPage<Theme> paging = themeService.getBaseMapper().selectPage(pager, null);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    /**
     * 选择 theme/spus?id=1 作为 规则
     * 而没有选择 theme/1/spus 作为路由规则的主要原因是
     * theme下的spus以后可能会通过其它的属性进行筛选，例如 name
     */
    @GetMapping("/spus")
    @LoginRequired
    public ResponseEntity<List<SpuSimplifyVO>> getSpus(@RequestParam(name = "id") @Positive(message = "{id}") Long id) {
        return ResponseEntity.ok(themeService.getSpus(id));
    }

    @GetMapping("/spu/list")
    @LoginRequired
    public ResponseEntity<List<Spu>> getSpuList(@RequestParam(name = "id") @Positive(message = "{id}") Long id) {
        return ResponseEntity.ok(themeService.getSimplifySpus(id));
    }

    @PostMapping("/spu")
    @PermissionMeta("创建主题下的spu")
    @GroupRequired
    public ResponseEntity<CreatedVO> addThemeSpu(@RequestBody @Validated ThemeSpuDTO dto) {
        themeService.addThemeSpu(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @DeleteMapping("/spu/{id}")
    @PermissionMeta("删除主题下的spu")
    @GroupRequired
    public ResponseEntity<DeletedVO> deleteThemeSpu(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        themeService.deleteThemeSpu(id);
        return ResponseEntity.ok(new DeletedVO());
    }


}
