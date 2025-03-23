package com.yunqi.sdk.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yunqi.sdk.oss.OssAbstractFactory;
import com.yunqi.sdk.oss.OssEnvParam;
import com.yunqi.sdk.oss.OssService;

/**
 * aliyun工厂类，用于获取Oss服务类
 */
public class AliyunOssFactory extends OssAbstractFactory {

    private static OssService ossService;

    /**
     * 返回AliyunOssService
     * @return
     */
    @Override
    public OssService getOssService(OssEnvParam envParam) {
        if (ossService != null) {
            return ossService;
        }
        // 单例模式，防止重复创建
        synchronized (this) {
            if (ossService == null) {
                OSSClientBuilder ossClientBuilder = new OSSClientBuilder();
                OSS ossClient = ossClientBuilder.build(envParam.getEndpoint(),
                        envParam.getAccessKey(),
                        envParam.getSecretKey());
                ossService = new AliyunOssService(envParam.getEndpoint(),
                        envParam.getBucket(),
                        ossClient);
            }
            return ossService;
        }
    }
}
