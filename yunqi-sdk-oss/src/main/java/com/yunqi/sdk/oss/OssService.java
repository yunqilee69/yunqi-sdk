package com.yunqi.sdk.oss;

import java.io.InputStream;

/**
 * Oss服务类，提供上传功能
 */
public interface OssService {

    /**
     * 上传文件，并返回访问地址，只支持公共读的方式
     * @param inputStream 文件流
     * @return 访问URL
     */
    String upload(InputStream inputStream);

    /**
     * 上传文件并指定文件名称，并返回访问地址，只支持公共读的方式
     * @param inputStream 文件流
     * @return 访问URL
     */
    String upload(String key, InputStream inputStream);
}
