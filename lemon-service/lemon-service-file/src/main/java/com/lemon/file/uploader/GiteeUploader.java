package com.lemon.file.uploader;

import cn.hutool.core.codec.Base64;
import com.lemon.file.config.AbstractUploader;
import com.lemon.file.config.FileProperties;
import com.lemon.file.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName GiteeUploader
 **/
@Slf4j
public class GiteeUploader extends AbstractUploader {

    @Autowired
    private FileProperties fileProperties;

    @Value("${lemon.file.gitee.owner}")
    private String owner;

    @Value("${lemon.file.gitee.repo}")
    private String repo;

    @Value("${lemon.file.gitee.access_token}")
    private String access_token;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新建(POST)、获取(GET)、删除(DELETE)文件：()中指的是使用对应的请求方式
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    private static final String API_CREATE_POST = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";

    /**
     * 查看图片的路径
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    private static final String API_CREATE_DOWN = "https://gitee.com/%s/%s/raw/master/%s";

    /**
     * 用于提交描述
     */
    private static final String ADD_MESSAGE = "add img";
    private static final String DEL_MESSAGE = "DEL img";


    @Override
    protected FileProperties getFileProperties() {
        return fileProperties;
    }

    @Override
    protected String getStorePath(String newFilename) {
        return this.createDownloadFileUrl(newFilename);
    }

    @Override
    protected String getFileType() {
        return FileConstant.REMOTE;
    }

    @Override
    protected boolean handleOneFile(byte[] bytes, String newFilename) {
        HashMap<String, Object> bodyMap = new HashMap<>(16);
        bodyMap.put("access_token",this.access_token);
        bodyMap.put("message", GiteeUploader.ADD_MESSAGE);
        bodyMap.put("content", Base64.encode(bytes));
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(this.createUploadFileUrl(newFilename), bodyMap, String.class);
            log.info(response.toString());
            return HttpStatus.CREATED.equals(response.getStatusCode());
        } catch (RestClientException e) {
            log.error("gitee upload file error: {}", e.toString());
            return false;
        }
    }

    /**
     * 生成创建(获取、删除)的指定文件路径
     */
    private String createUploadFileUrl(String fileName){
        //填充请求路径
        Date now = new Date();
        String format = new SimpleDateFormat("yyyy/MM").format(now);
        return String.format(GiteeUploader.API_CREATE_POST,
                this.owner,
                this.repo,
                format+"/"+fileName);
    }

    /**
     * 生成创建(获取、删除)的指定文件路径
     */
    private String createDownloadFileUrl(String fileName){
        //填充请求路径
        Date now = new Date();
        String format = new SimpleDateFormat("yyyy/MM").format(now);
        return String.format(GiteeUploader.API_CREATE_DOWN,
                this.owner,
                this.repo,
                format+"/"+fileName);
    }

}
