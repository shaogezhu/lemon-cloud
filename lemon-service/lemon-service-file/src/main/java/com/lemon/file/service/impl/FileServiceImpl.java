package com.lemon.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.file.mapper.FileMapper;
import com.lemon.file.bo.FileBO;
import com.lemon.file.config.FileProperties;
import com.lemon.file.config.UploadHandler;
import com.lemon.file.config.Uploader;
import com.lemon.file.constant.FileConstant;
import com.lemon.file.pojo.File;
import com.lemon.file.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName FileServiceImpl
 **/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private Uploader uploader;

    /**
     * 文件上传配置信息
     */
    @Autowired
    private FileProperties fileProperties;

    @Override
    public List<FileBO> upload(MultiValueMap<String, MultipartFile> fileMap) {
        List<FileBO> res = new ArrayList<>();

        uploader.upload(fileMap, new UploadHandler() {
            @Override
            public boolean preHandle(File file) {
                File found = baseMapper.selectByMd5(file.getMd5());
                if (found == null) {
                    return true;
                }
                res.add(transformDoToBo(found, file.getKey()));
                return false;
            }

            @Override
            public void afterHandle(File file) {
                getBaseMapper().insert(file);
                res.add(transformDoToBo(file, file.getKey()));
            }
        });
        return res;
    }

    @Override
    public boolean checkFileExistByMd5(String md5) {
        return this.getBaseMapper().selectCountByMd5(md5) > 0;
    }

    private FileBO transformDoToBo(File file, String key) {
        FileBO bo = new FileBO();
        BeanUtils.copyProperties(file, bo);
        if (file.getType().equals(FileConstant.LOCAL)) {
            String s = fileProperties.getServePath().split("/")[0];

            // replaceAll 是将 windows 平台下的 \ 替换为 /
            if(System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS")){
                bo.setUrl(fileProperties.getDomain() + s + "/" + file.getPath().replaceAll("\\\\","/"));
            }else {
                bo.setUrl(fileProperties.getDomain() + s + "/" + file.getPath());
            }
        } else {
            bo.setUrl(file.getPath());
        }
        bo.setKey(key);
        return bo;
    }
}
