package com.lemon.file.config;

import com.lemon.file.pojo.File;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UploadHandler
 * @Description 文件前预处理器
 **/
public interface UploadHandler {

    /**
     * 在文件写入本地或者上传到云之前调用此方法
     *
     * @return 是否上传，若返回false，则不上传
     */
    boolean preHandle(File file);

    /**
     * 在文件写入本地或者上传到云之后调用此方法
     */
    void afterHandle(File file);
}

