package com.lemon.goods.controller;

import com.lemon.exception.NotFoundException;
import com.lemon.goods.pojo.SaleExplain;
import com.lemon.goods.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SaleExplainController
 **/
@RequestMapping("/sale_explain")
@RestController
public class SaleExplainController {

    @Autowired
    private SaleExplainService saleExplainService;

    @GetMapping("/fixed")
    public ResponseEntity<List<SaleExplain>> getFixed() {
        List<SaleExplain> saleExplains = saleExplainService.list();
        if (saleExplains.isEmpty()) {
            throw new NotFoundException(30011);
        }
        return ResponseEntity.ok(saleExplains);
    }
}