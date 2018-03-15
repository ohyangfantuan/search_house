package com.oywy.core.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置
 * Created by oywy on 2018/3/14.
 */
@Configuration
public class QiniuOssConfig {
    /**
     * 华东机房配置
     *
     * @return
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        //华东机房
        Zone zone = Zone.zone0();
        return new com.qiniu.storage.Configuration(zone);
    }

    /**
     * 上传工具
     *
     * @return
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    private String secretKey;

    /**
     * 认证
     *
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 七牛空间管理器
     *
     * @return
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
}
