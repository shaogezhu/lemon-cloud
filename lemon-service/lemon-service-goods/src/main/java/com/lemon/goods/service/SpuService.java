package com.lemon.goods.service;

import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.goods.dto.SpuDTO;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.vo.SpuDetailVO;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuService
 **/
public interface SpuService extends IService<Spu> {
    /**
     * 根据商品title,模糊搜索商品 并进行分页
     * @param q 商品title
     * @param page 页码
     * @param count 每页数量
     */
    Page<Spu> search(String q, Integer page, Integer count);

    /**
     * 根据spu的id获取spu的详细信息
     * @param id spu的id
     */
    Spu getSpuDetailById(Long id);

    /**
     * 分页获取最新的商品列表
     * @param page 页码
     * @param count 每页条数
     */
    Page<Spu> getLatestSpuList(Integer page, Integer count);

    /**
     * 分页获取某个分类下面的Spu商品信息
     * @param id 分类id
     * @param isRoot 分类是否为root
     * @param page 页码
     * @param count 每页条数
     */
    Page<Spu> getByCategory(Long id, Boolean isRoot, Integer page, Integer count);

    /**
     * 获取用户的浏览足迹
     */
    List<Spu> getMyFootGoods();


    SpuDetailVO getDetail(Long id);

    void create(SpuDTO dto);

    void update(SpuDTO dto, Long id);

    void delete(Long id);

    List<Long> getSpecKeys(Long id);


}
