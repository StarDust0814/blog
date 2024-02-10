package com.sangeng.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    //连接地址
    private String endpoint;
    //用户名
    private String accessKey;
    //密码
    private String secretKey;
    //域名
    private String filHost;
    // 桶名
    private String bucketName;

}
