package com.lemon.file.service;

import com.lemon.file.bo.FileBO;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName FileService
 **/
public interface FileService {

    List<FileBO> upload(MultiValueMap<String, MultipartFile> fileMap);

    /**
     * 通过md5检查文件是否存在
     *
     * @param md5 md5
     * @return true 表示已存在
     */
    boolean checkFileExistByMd5(String md5);
}
