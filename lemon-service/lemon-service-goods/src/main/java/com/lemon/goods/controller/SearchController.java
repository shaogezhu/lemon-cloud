package com.lemon.goods.controller;

import com.lemon.advice.mybatis.Page;
import com.lemon.entity.PageCounter;
import com.lemon.entity.PagingDozer;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.service.SpuService;
import com.lemon.goods.vo.SpuSimplifyVO;
import com.lemon.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SearchController
 **/
@RequestMapping("/search")
@RestController
public class SearchController {

    @Autowired
    private SpuService spuService;

    @GetMapping("")
    public ResponseEntity<PagingDozer<Spu, SpuSimplifyVO>> search(@RequestParam String q,
                                                                  @RequestParam(defaultValue = "0") Integer start,
                                                                  @RequestParam(defaultValue = "10") Integer count) {
        PageCounter counter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = this.spuService.search(q, counter.getPage(), counter.getCount());
        return ResponseEntity.ok(new PagingDozer<>(page, SpuSimplifyVO.class));
    }
}