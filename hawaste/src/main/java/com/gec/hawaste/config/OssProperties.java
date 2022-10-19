package com.gec.hawaste.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS配置
 *
 * @author gec
 * @version 1.0
 * @since 2020/6/2
 */
@Data
@Component
@ConfigurationProperties(prefix="minio")
public class OssProperties {

    private String endpoint;  //桶所在节点ip

    private Integer port;//web管理服务访问端口

    private Boolean secure;//false为http   true为 http

    private String accessKey;//登录账号

    private String secretKey;//密码

    private String bucketName;//存储桶名字

}
