package com.lemon.oauth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName PageResponseVO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseVO<T> {

    private Integer total;

    private List<T> items;

    private Integer page;

    private Integer count;
}
