package com.lemon.goods.controller;

import com.lemon.goods.pojo.Tag;
import com.lemon.goods.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TagsContoller
 **/
@RestController
@RequestMapping("/tag")
@Validated
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/type/{id}")
    public ResponseEntity<List<Tag>> getTagsByType(@PathVariable("id") Integer id){
        return ResponseEntity.ok(tagService.getTagsByType(id));
    }

}
