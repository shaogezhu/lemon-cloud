package com.lemon.oauth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.oauth.dto.log.QueryLogDTO;
import com.lemon.oauth.dto.querry.BasePageDTO;
import com.lemon.oauth.pojo.cms.LogDO;
import com.lemon.oauth.service.LogService;
import com.lemon.oauth.util.PageUtil;
import com.lemon.oauth.vo.PageResponseVO;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LogController
 **/
@RestController
@RequestMapping("/cms/log")
@PermissionModule(value = "日志")
@Validated
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    @GroupRequired
    @PermissionMeta(value = "查询所有日志")
    public ResponseEntity<PageResponseVO<LogDO>> getLogs(QueryLogDTO dto) {
        IPage<LogDO> iPage = logService.getLogPage(
                dto.getPage(), dto.getCount(),
                dto.getName(), dto.getStart(),
                dto.getEnd()
        );
        return ResponseEntity.ok(PageUtil.build(iPage));
    }

    @GetMapping("/search")
    @GroupRequired
    @PermissionMeta(value = "搜索日志")
    public ResponseEntity<PageResponseVO<LogDO>> searchLogs(QueryLogDTO dto) {
        IPage<LogDO> iPage = logService.searchLogPage(
                dto.getPage(), dto.getCount(),
                dto.getName(), dto.getKeyword(),
                dto.getStart(), dto.getEnd()
        );
        return ResponseEntity.ok(PageUtil.build(iPage));
    }

    @GetMapping("/users")
    @GroupRequired
    @PermissionMeta(value = "查询日志记录的用户")
    public ResponseEntity<PageResponseVO<String>> getUsers(@Validated BasePageDTO dto) {
        IPage<String> iPage = logService.getUserNamePage(dto.getPage(), dto.getCount());
        return ResponseEntity.ok(PageUtil.build(iPage));
    }

    @RequestMapping("/create")
    public void createLog(String template, String permission, Long userId, String username, String method, String path, Integer status){
        logService.createLog(template, permission, userId, username, method, path, status);
    }

}
