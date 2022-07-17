package com.lemon.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PagingDozer
 **/
public class PagingDozer<T,K> extends Paging{
    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> pageT, Class<K> kClass) {
        this.initPageParameters(pageT);
        List<T> tList = pageT.getRecords();
        List<K> voList = new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        tList.forEach(t->{
            K vo = mapper.map(t,kClass);
            voList.add(vo);
        });
        this.setItems(voList);
    }
}