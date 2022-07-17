package com.lemon.coupon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.coupon.dto.CategoryDTO;
import com.lemon.coupon.pojo.Category;
import com.lemon.coupon.pojo.GridCategory;
import com.lemon.coupon.service.CategoryService;
import com.lemon.coupon.service.GridCategoryService;
import com.lemon.coupon.vo.CategoriesAllVO;
import com.lemon.enumeration.CategoryRootOrNotEnum;
import com.lemon.exception.NotFoundException;
import com.lemon.oauth.util.PageUtil;
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
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CategoryController
 **/
@RestController
@RequestMapping("/category")
@PermissionModule("分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public ResponseEntity<CategoriesAllVO> getCategoryAll(){
        Map<Integer, List<Category>> categoryAll = categoryService.getCategoryAll();
        return ResponseEntity.ok(new CategoriesAllVO(categoryAll));
    }

    @GetMapping("/grid/all")
    public ResponseEntity<List<GridCategory>> getGridCategoryList() {
        List<GridCategory> gridCategoryList = gridCategoryService.list();
        if (gridCategoryList.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return ResponseEntity.ok(gridCategoryList);
    }

    @RequestMapping("/id")
    public ResponseEntity<Category> getCategoryById(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("")
    @PermissionMeta(value = "创建分类")
    @GroupRequired
    @Logger(template = "{user.username}创建了 分类")
    public ResponseEntity<CreatedVO> create(@Validated @RequestBody CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        categoryService.save(category);
        return ResponseEntity.ok(new CreatedVO());
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新分类")
    @Logger(template = "{user.username}更新了 分类")
    public ResponseEntity<UpdatedVO> update(
            @RequestBody @Validated CategoryDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        categoryService.updateCategory(dto, id);
        return ResponseEntity.ok(new UpdatedVO());
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除分类")
    @GroupRequired
    @Logger(template = "{user.username}删除了 分类")
    public ResponseEntity<DeletedVO> delete(
            @PathVariable @Positive(message = "{id.positive}") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new DeletedVO());
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ResponseEntity<Category> get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Category>> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @Min(value = 0) @Max(value = 1) Long root
    ) {
        IPage<Category> paging = categoryService.getCategoriesByPage(count, page, root);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/sub-page")
    @LoginRequired
    public ResponseEntity<PageResponseVO<Category>> subPage(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @RequestParam(name = "id") @Positive(message = "{id}") Long id
    ) {
        IPage<Category> paging = categoryService.getSubCategoriesByPage(count, page, id);
        return ResponseEntity.ok(PageUtil.build(paging));
    }

    @GetMapping("/list")
    @LoginRequired
    public ResponseEntity<List<Category>> getList() {
        CategoryRootOrNotEnum notRoot = CategoryRootOrNotEnum.NOT_ROOT;
        return ResponseEntity.ok(this.categoryService.lambdaQuery().eq(Category::getIsRoot, notRoot.getValue()).list());
    }

}
