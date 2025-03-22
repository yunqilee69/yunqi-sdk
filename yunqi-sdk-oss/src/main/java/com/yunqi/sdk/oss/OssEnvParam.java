package com.yunqi.sdk.oss;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 构建Oss服务所需要的参数
 */
@Data
@Builder
public class OssEnvParam {

    /**
     * 公钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 上传域名
     */
    private String endpoint;

    /**
     * 上传桶
     */
    private String bucket;

    /**
     * 额外字段
     */
    private Map<String, String> extra;
}
