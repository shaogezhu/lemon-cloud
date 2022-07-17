package com.lemon.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.lemon.entity.PageCounter;
import com.lemon.entity.PagingDozer;
import com.lemon.exception.NotFoundException;
import com.lemon.goods.dto.SpuDTO;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.service.SpuService;
import com.lemon.goods.vo.SpuDetailVO;
import com.lemon.goods.vo.SpuSimpleVO;
import com.lemon.goods.vo.SpuSimplifyVO;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import com.lemon.util.CommonUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuController
 **/
@RestController
@RequestMapping("/spu")
@Validated
@PermissionModule("SPU")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @ScopeLevel
    @GetMapping("/id/{id}/detail")
    public ResponseEntity<Spu> getSpuById(@PathVariable @Positive(message = "{id.positive}") Long id){
        Spu spu = spuService.getSpuDetailById(id);
        if (spu==null){
            throw new NotFoundException(30012);
        }
        return ResponseEntity.ok(spu);
    }

    @GetMapping("/id/{id}/simplify")
    public ResponseEntity<SpuSimpleVO> getSpuSimpleVoById(@PathVariable @Positive(message = "{id.positive}") Long id){
        Spu spu = spuService.getSpuDetailById(id);
        if (spu==null){
            throw new NotFoundException(30012);
        }
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return ResponseEntity.ok(mapper.map(spu, SpuSimpleVO.class));
    }

    @GetMapping("/latest")
    public ResponseEntity<PagingDozer<Spu, SpuSimpleVO>> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                                                          @RequestParam(defaultValue = "20") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        System.out.println(pageCounter.getPage());
        System.out.println(pageCounter.getCount());
        Page<Spu> spuList = spuService.getLatestSpuList(pageCounter.getPage(),pageCounter.getCount());
        return ResponseEntity.ok(new PagingDozer<>(spuList, SpuSimpleVO.class));
    }

    @GetMapping("/by/category/{id}")
    public ResponseEntity<PagingDozer<Spu, SpuSimplifyVO>> getByCategoryId(@PathVariable @Positive Long id,
                                                                           @RequestParam(name = "is_root", defaultValue = "false") Boolean isRoot,
                                                                           @RequestParam(name = "start", defaultValue = "0") Integer start,
                                                                           @RequestParam(name = "count", defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = this.spuService.getByCategory(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        return ResponseEntity.ok(new PagingDozer<>(page, SpuSimplifyVO.class));
    }

    @ScopeLevel
    @GetMapping("/my/foot")
    public ResponseEntity<List<SpuSimpleVO>> getMyFootGoods(){
        List<Spu> spuList = spuService.getMyFootGoods();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<SpuSimpleVO> res = new ArrayList<>();
        spuList.forEach(spu -> res.add(mapper.map(spu, SpuSimpleVO.class)));
        return ResponseEntity.ok(res);
    }

    @PostMapping("")
    @PermissionMeta("创建SPU")
    @GroupRequired
    public ResponseEntity<CreatedVO> create(@RequestBody @Validated SpuDTO dto) {
        System.out.println(dto.toString());
        this.spuService.create(dto);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SPU")
    @GroupRequired
    public ResponseEntity<UpdatedVO> update(@RequestBody @Validated SpuDTO dto,
                                            @PathVariable @Positive(message = "{id.positive}") Long id) {
        spuService.update(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SPU")
    @GroupRequired
    public ResponseEntity<DeletedVO> delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        spuService.delete(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ResponseEntity<SpuDetailVO> getDetail(@PathVariable(value = "id") @Positive Long id) {
        SpuDetailVO detail = this.spuService.getDetail(id);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/key")
    @LoginRequired
    public ResponseEntity<List<Long>> getSpecKeys(@RequestParam(name = "id") @Positive(message = "{id}") Long id) {
        return ResponseEntity.ok(spuService.getSpecKeys(id));
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Spu>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Spu> pager = new Page<>(page, count);
        IPage<Spu> paging = spuService.getBaseMapper().selectPage(pager, null);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/list")
    @LoginRequired
    public ResponseEntity<List<Spu>> getList() {
        return ResponseEntity.ok(spuService.list());
    }

}