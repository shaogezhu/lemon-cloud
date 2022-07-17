package com.lemon.file.core;

import com.lemon.file.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WebConfiguration
 **/
@Configuration(proxyBeanMethods = false)
@Slf4j
public class WebConfiguration  implements WebMvcConfigurer {

    @Value("${lemon.file.serve-path:assets/**}")
    private String servePath;

    @Value("${lemon.file.store-dir:assets/}")
    private String dir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // classpath: or file:
        registry.addResourceHandler(this.servePath)
                .addResourceLocations("file:" + getAbsDir() + "/");
    }

    /**
     * 获得文件夹的绝对路径
     */
    private String getAbsDir() {
        if (FileUtil.isAbsolute(this.dir)) {
            return this.dir;
        }
        String cmd = System.getProperty("user.dir");
        Path path = FileSystems.getDefault().getPath(cmd, this.dir);
        return path.toAbsolutePath().toString();
    }
}
