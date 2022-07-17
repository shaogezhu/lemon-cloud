package com.lemon.file.controller;

import com.lemon.file.bo.FileBO;
import com.lemon.file.service.FileService;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName FileController
 **/
@RestController
@PermissionModule("文件")
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param multipartHttpServletRequest 携带文件的 request
     * @return 文件信息
     */
    @PostMapping("/upload")
    @PermissionMeta("图片上传")
    @GroupRequired
    public ResponseEntity<List<FileBO>> upload(MultipartHttpServletRequest multipartHttpServletRequest) {
        MultiValueMap<String, MultipartFile> fileMap =
                multipartHttpServletRequest.getMultiFileMap();
        return ResponseEntity.ok(fileService.upload(fileMap));
    }
}
