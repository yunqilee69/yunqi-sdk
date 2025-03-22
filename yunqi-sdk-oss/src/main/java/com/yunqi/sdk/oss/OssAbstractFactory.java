package com.yunqi.sdk.oss;

/**
 * 抽象工厂类
 *
 */
public abstract class OssAbstractFactory {

    /**
     * 获取具体的OssService类
     * @return
     */
    public abstract OssService getOssService(OssEnvParam envParam);

}
