package com.lemon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.advice.mybatis.Page;
import com.lemon.oauth.pojo.cms.LogDO;

import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LogMapper
 **/
public interface LogMapper extends BaseMapper<LogDO> {

    IPage<LogDO> findLogsByUsernameAndRange(Page<LogDO> pager, String name, Date start, Date end);

    IPage<LogDO> searchLogsByUsernameAndKeywordAndRange(Page<LogDO> pager, String name, String keyword, Date start, Date end);

    IPage<String> getUserNames(Page<LogDO> pager);
}
