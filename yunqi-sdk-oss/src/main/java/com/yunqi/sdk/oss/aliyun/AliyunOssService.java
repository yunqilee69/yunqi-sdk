package com.yunqi.sdk.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.PutObjectResult;
import com.yunqi.sdk.oss.OssService;
import com.yunqi.sdk.oss.utils.FileTypeUtil;


import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class AliyunOssService implements OssService {

    private final OSS ossClient;

    private final String endpoint;

    private final String bucketName;

    public AliyunOssService(String endpoint, String accessKeyId, String accessKeySecret, String bucket) {
        this.bucketName = bucket;
        this.endpoint = endpoint;
        OSSClientBuilder ossClientBuilder = new OSSClientBuilder();
        this.ossClient = ossClientBuilder.build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(generateKey(), inputStream);
    }

    @Override
    public String upload(String key, InputStream inputStream) {
        PutObjectResult result = ossClient.putObject(this.bucketName, key, inputStream);
        return generateURL(key, inputStream);
    }

    /**
     * 使用UUID生成器进行生成唯一id
     * @return
     */
    private String generateKey(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成访问URL
     * 只支持公共读的方式
     * @param key
     * @param inputStream
     * @return
     */
    private String generateURL(String key, InputStream inputStream) {
        String format = "https://%s.%s/%s.%s";
        return String.format(format, this.bucketName, this.endpoint, key, FileTypeUtil.getFileType(inputStream));
    }
}
