package com.lemon.goods.bo;

import com.lemon.goods.pojo.SpecKey;
import com.lemon.goods.pojo.SpecValue;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpecKeyAndItemsBO
 **/
@Data
public class SpecKeyAndItemsBO {

    private Integer id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

    private List<SpecValue> items;

    public SpecKeyAndItemsBO(SpecKey specKey, List<SpecValue> items) {
        BeanUtils.copyProperties(specKey, this);
        this.setItems(items);
    }

}