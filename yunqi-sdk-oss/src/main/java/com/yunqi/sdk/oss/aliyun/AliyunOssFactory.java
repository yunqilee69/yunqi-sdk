package com.yunqi.sdk.oss.aliyun;

import com.yunqi.sdk.oss.OssAbstractFactory;
import com.yunqi.sdk.oss.OssEnvParam;
import com.yunqi.sdk.oss.OssService;

/**
 * aliyun工厂类，用于获取Oss服务类
 */
public class AliyunOssFactory extends OssAbstractFactory {

    private OssService ossService;

    /**
     * 返回AliyunOssService
     * @return
     */
    @Override
    public OssService getOssService(OssEnvParam envParam) {
        if (this.ossService != null) {
            return this.ossService;
        }
        // 单例模式，防止重复创建
        synchronized (this) {
            if (this.ossService == null) {
                this.ossService = new AliyunOssService(envParam.getEndpoint(),
                        envParam.getAccessKey(),
                        envParam.getSecretKey(),
                        envParam.getBucket());
            }
            return this.ossService;
        }
    }
}
