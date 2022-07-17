package com.lemon.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.advice.mybatis.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.coupon.feign.CategoryFeign;
import com.lemon.coupon.pojo.Category;
import com.lemon.entity.LocalUser;
import com.lemon.goods.constant.GoodsConstant;
import com.lemon.goods.dto.SpuDTO;
import com.lemon.goods.mapper.SpuMapper;
import com.lemon.goods.pojo.Spu;
import com.lemon.goods.pojo.SpuDetailImg;
import com.lemon.goods.pojo.SpuImg;
import com.lemon.goods.pojo.SpuKey;
import com.lemon.goods.service.SpuDetailImgService;
import com.lemon.goods.service.SpuImgService;
import com.lemon.goods.service.SpuKeyService;
import com.lemon.goods.service.SpuService;
import com.lemon.goods.vo.SpuDetailVO;
import com.lemon.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName SpuServiceImpl
 **/
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper,Spu> implements SpuService {
    @Resource
    private SpuMapper spuMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${lemon.foot.limit-time}")
    private Long limit;


    @Override
    public Page<Spu> search(String q, Integer page, Integer count) {
        return spuMapper.selectPage(new Page<>(page,count),new QueryWrapper<Spu>().like("title",q));
    }

    @Override
    public Spu getSpuDetailById(Long id) {
        Spu spuDetail = spuMapper.getSpuDetailById(id);
        this.saveRedis(spuDetail);
        return spuDetail;
    }

    private void saveRedis(Spu spuDetail) {
        if (spuDetail !=null){
            String redisKey = GoodsConstant.USER_SPU_PRE+LocalUser.getUserId();
            Boolean flag = redisTemplate.opsForZSet().add( redisKey, "" + spuDetail.getId(), new Date().getTime());
            if (Boolean.TRUE.equals(flag)){
                redisTemplate.opsForZSet().removeRangeByScore(redisKey,0L,new Date().getTime()-limit);
            }
        }
    }

    @Override
    public Page<Spu> getLatestSpuList(Integer page, Integer count) {
        return spuMapper.selectPage(new Page<Spu>(page, count),new QueryWrapper<Spu>().orderByDesc("create_time"));
    }

    @Override
    public Page<Spu> getByCategory(Long id, Boolean isRoot, Integer num, Integer count) {
        Page<Spu> page = new Page<>(num,count);
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (isRoot){
            queryWrapper.eq("root_category_id",id);
        }else{
            queryWrapper.eq("category_id",id);
        }
        return spuMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<Spu> getMyFootGoods() {
        String redisKey = GoodsConstant.USER_SPU_PRE + LocalUser.getUserId();
        long now = new Date().getTime();
        Set<String> range = redisTemplate.opsForZSet().reverseRangeByScore(redisKey, now - limit, now);
        if (CollectionUtils.isEmpty(range)){
            return new ArrayList<>();
        }
        List<Spu> spuList = spuMapper.selectList(new QueryWrapper<Spu>().in("id", range));
        HashMap<String,Spu> map = new HashMap<>(16);
        for (Spu spu : spuList) {
            map.put(spu.getId()+"",spu);
        }
        List<Spu> res = new ArrayList<>();
        for (String s : range) {
            Spu spu = map.get(s);
            if (spu!=null) {
                res.add(spu);
            }
        }
        return res;
    }


    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SpuImgService spuImgService;

    @Autowired
    private SpuDetailImgService spuDetailImgService;

    @Autowired
    private SpuKeyService spuKeyService;

    @Override
    public SpuDetailVO getDetail(Long id) {
        SpuDetailVO detail = this.getBaseMapper().getDetail(id);
        Category category = categoryFeign.getCategoryById(id);
        detail.setCategoryName(category.getName());
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SpuDTO dto) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(dto, spu);
        Category category = categoryFeign.getCategoryById(dto.getCategoryId());
        spu.setRootCategoryId(category.getParentId());

        this.save(spu);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }

        this.insertSpuImgList(spuImgList, spu.getId());
        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spu.getId());
        }

        if (dto.getSpecKeyIdList() != null) {
            this.insertSpuKeyList(dto.getSpecKeyIdList(), spu.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SpuDTO dto, Long id) {
        Spu spu = this.getById(id);
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        BeanUtils.copyProperties(dto, spu);
        Category category = categoryFeign.getCategoryById(dto.getCategoryId());
        if (category.getParentId() != null) {
            spu.setRootCategoryId(category.getParentId());
        }
        this.updateById(spu);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }
        spuImgService.deleteImgsBySpuId(spu.getId());
        spuDetailImgService.deleteImgsBySpuId(spu.getId());

        this.insertSpuImgList(spuImgList, spu.getId());
        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spu.getId());
        }
        this.updateSpuKey(spu.getId(), dto.getSpecKeyIdList());
    }

    @Override
    public void delete(Long id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFoundException(70000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public List<Long> getSpecKeys(Long id) {
        return spuMapper.getSpecKeys(id);
    }

    private void insertSpuImgList(List<String> spuImgList, Long spuId) {
        List<SpuImg> spuImgDOList = spuImgList.stream().map(s -> {
            SpuImg spuImg = new SpuImg();
            spuImg.setImg(s);
            spuImg.setSpuId(spuId);
            return spuImg;
        }).collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgDOList);
    }

    private void insertSpuDetailImgList(List<String> spuDetailImgList, Long spuId) {
        List<SpuDetailImg> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0; i < spuDetailImgList.size(); i++) {
            SpuDetailImg spuDetailImg = new SpuDetailImg();
            spuDetailImg.setImg(spuDetailImgList.get(i))
                    .setSpuId(spuId)
                    .setIndex(i);
            spuDetailImgDOList.add(spuDetailImg);
        }
        this.spuDetailImgService.saveBatch(spuDetailImgDOList);
    }

    private void insertSpuKeyList(List<Long> spuKeyIdList, Long spuId) {
        List<SpuKey> spuKeyList = spuKeyIdList.stream()
                .map(sk -> {
                    SpuKey spuKey = new SpuKey();
                    spuKey.setSpuId(spuId)
                            .setSpecKeyId(sk);
                    return spuKey;
                }).collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyList);
    }

    /**
     * 更新spu_key表
     * @param spuId spu id
     * @param newSpecKeyIdList 前端传递过来的 spu_key id列表
     */
    private void updateSpuKey(Long spuId, List<Long> newSpecKeyIdList) {
        QueryWrapper<SpuKey> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKey::getSpuId, spuId);
        List<SpuKey> exists = spuKeyService.getBaseMapper().selectList(wrapper);
        List<Long> existsIds = new ArrayList<>();
        List<SpuKey> newSpuKeyList = new ArrayList<>();
        for (SpuKey exist: exists) {
            existsIds.add(exist.getId());
        }
        for (Long specKeyId: newSpecKeyIdList) {
            SpuKey spuKey = new SpuKey();
            spuKey.setSpecKeyId(specKeyId);
            spuKey.setSpuId(spuId);
            newSpuKeyList.add(spuKey);
        }
        spuKeyService.removeByIds(existsIds);
        spuKeyService.saveBatch(newSpuKeyList);
    }



}
