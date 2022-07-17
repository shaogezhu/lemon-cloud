package com.lemon.file.uploader;

import com.lemon.file.config.Uploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UploaderConfiguration
 * @Description 文件上传配置类
 **/
@Configuration(proxyBeanMethods = false)
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class UploaderConfiguration {
    /**
     * @return 本地文件上传实现类
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public Uploader uploader(){
        return new LocalUploader();
    }
}
