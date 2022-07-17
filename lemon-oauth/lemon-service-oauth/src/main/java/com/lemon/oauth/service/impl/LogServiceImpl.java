package com.lemon.oauth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.advice.mybatis.Page;
import com.lemon.oauth.mapper.LogMapper;
import com.lemon.oauth.pojo.cms.LogDO;
import com.lemon.oauth.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LogServiceImpl
 **/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper,LogDO> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<LogDO> getLogPage(Integer page, Integer count, String name, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = logMapper.findLogsByUsernameAndRange(pager, name, start, end);
        return iPage;
    }

    @Override
    public IPage<LogDO> searchLogPage(Integer page, Integer count, String name, String keyword, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = logMapper.searchLogsByUsernameAndKeywordAndRange(pager, name, "%" + keyword + "%", start, end);
        return iPage;
    }

    @Override
    public IPage<String> getUserNamePage(Integer page, Integer count) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<String> iPage =logMapper.getUserNames(pager);
        return iPage;
    }

    @Override
    public Boolean createLog(String template, String permission, Long userId, String username, String method, String path, Integer status) {
        LogDO record = LogDO.builder()
                .message(template)
                .userId(userId)
                .username(username)
                .statusCode(status)
                .method(method)
                .path(path)
                .build();
        if (permission != null) {
            record.setPermission(permission);
        }
        return logMapper.insert(record) > 0;
    }
}
