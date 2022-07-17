package com.lemon.file.config;

import com.lemon.file.pojo.File;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName Uploader
 * @Description 文件上传服务接口
 **/
public interface Uploader {

    /**
     * 上传文件
     *
     * @param fileMap 文件map
     * @return 文件数据
     */
    List<File> upload(MultiValueMap<String, MultipartFile> fileMap);

    /**
     * 上传文件
     *
     * @param fileMap    文件map
     * @param uploadHandler 预处理器
     * @return 文件数据
     */
    List<File> upload(MultiValueMap<String, MultipartFile> fileMap, UploadHandler uploadHandler);
}

