package com.yunqi.sdk.oss;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Oss服务类，提供上传、下载、获取url功能
 */
public interface OssService {

    /**
     * 上传文件，并返回访问地址，只支持公共读的方式
     * @param inputStream 文件流
     * @return 访问URL
     */
    URL upload(InputStream inputStream);

    /**
     * 上传文件并指定文件名称，并返回访问地址，只支持公共读的方式
     * @param inputStream 文件流
     * @return 访问URL
     */
    URL upload(String objName, InputStream inputStream);

    /**
     * 根据key获取文件的InputStream
     * @param objName
     * @return
     */
    InputStream download(String objName);

    /**
     * 获取公共读的url，公共读权限
     * @param objName
     * @return
     */
    URL getCommonUrl(String objName);

    /**
     * 获取预签名的url，过期时间默认为1天
     * @param objName
     * @return
     */
    URL getPresignedUrl(String objName);

    /**
     * 获取预签名的url，并设置过期时间
     * @param objName
     * @param expiration
     * @return
     */
    URL getPresignedUrl(String objName, Date expiration);
}
