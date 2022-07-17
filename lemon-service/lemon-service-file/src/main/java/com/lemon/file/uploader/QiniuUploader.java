package com.lemon.file.uploader;

import com.lemon.file.config.AbstractUploader;
import com.lemon.file.config.FileProperties;
import com.lemon.file.constant.FileConstant;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName QiniuUploader
 **/
@Slf4j
public class QiniuUploader extends AbstractUploader {

    @Autowired
    private FileProperties fileProperties;

    @Value("${lemon.file.qiniuyun.access-key}")
    private String accessKey;

    @Value("${lemon.file.qiniuyun.secret-key}")
    private String secretKey;

    @Value("${lemon.file.qiniuyun.bucket}")
    private String bucket;

    private UploadManager uploadManager;

    private String upToken;

    public void initUploadManager() {
        Configuration cfg = new Configuration(Region.region2());
        uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        upToken = auth.uploadToken(bucket);
    }

    @Override
    protected FileProperties getFileProperties() {
        return fileProperties;
    }

    @Override
    protected String getStorePath(String newFilename) {
        return fileProperties.getDomain() + newFilename;
    }

    @Override
    protected String getFileType() {
        return FileConstant.REMOTE;
    }

    /**
     * 处理一个文件数据
     *
     * @param bytes       文件数据，比特流
     * @param newFilename 新文件名称
     * @return 处理是否成功，如果出现异常则返回 false，避免把失败的写入数据库
     */
    @Override
    protected boolean handleOneFile(byte[] bytes, String newFilename) {
        initUploadManager();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        try {
            Response response = uploadManager.put(byteInputStream, newFilename, upToken, null, null);
            log.info(response.toString());
            return response.isOK();
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("qiniuyun upload file error: {}", r.error);
            return false;
        }
    }
}
