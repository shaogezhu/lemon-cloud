package com.lemon.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PageCounter
 **/
@Getter
@Setter
@Builder
public class PageCounter {
    private Integer page;
    private Integer count;
}
