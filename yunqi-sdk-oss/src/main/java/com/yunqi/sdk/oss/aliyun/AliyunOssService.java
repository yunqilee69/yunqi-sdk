package com.yunqi.sdk.oss.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.yunqi.sdk.oss.OssService;
import com.yunqi.sdk.oss.exception.ObjNameInvalidException;
import com.yunqi.sdk.oss.exception.OssException;
import com.yunqi.sdk.oss.exception.UploadFileException;
import com.yunqi.sdk.oss.utils.FileTypeUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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
    public URL upload(InputStream inputStream) {
        return upload(generateKey(inputStream), inputStream);
    }

    @Override
    public URL upload(String objName, InputStream inputStream) {
        if (!checkObjName(objName)) {
            throw new ObjNameInvalidException();
        }

        // 上传文件
        String eTag = null;
        try {
            PutObjectResult result = ossClient.putObject(this.bucketName, objName, inputStream);
            eTag = result.getETag();
        } catch (OssException | ClientException oe) {
            throw new UploadFileException(oe);
        }

        // 没有eTag值也说明，没有上传成功
        if (eTag == null) {
            throw new UploadFileException();
        }

        return getCommonUrl(objName);
    }

    @Override
    public InputStream download(String objName) {
        try (OSSObject ossObject = ossClient.getObject(bucketName, objName);
             InputStream ossInputStream = ossObject.getObjectContent();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            // 将原始 InputStream 的内容复制到 ByteArrayOutputStream
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = ossInputStream.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            buffer.flush();

            // 返回一个新的 ByteArrayInputStream
            return new ByteArrayInputStream(buffer.toByteArray());
        } catch (IOException e) {
            System.err.println("Error reading object content: " + e.getMessage());
            return null;
        }
    }

    @Override
    public URL getCommonUrl(String objName) {
        String format = "https://%s.%s/%s";
        String urlStr = String.format(format, bucketName, endpoint, objName);
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            throw new OssException(e);
        }
    }

    @Override
    public URL getPresignedUrl(String objName) {
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        return getPresignedUrl(objName, expiration);
    }

    @Override
    public URL getPresignedUrl(String objName, Date expiration) {
        return ossClient.generatePresignedUrl(bucketName, objName, expiration);
    }

    /**
     * 使用UUID生成器进行生成唯一id
     * @return
     */
    private String generateKey(InputStream inputStream){
        return UUID.randomUUID().toString().replace("-", "") + "." +  FileTypeUtil.getFileType(inputStream);
    }

    /**
     * 校验objName是否符合规范
     * @param objName
     * @return
     */
    private boolean checkObjName(String objName) {
        List<String> fileTypeList = FileTypeUtil.getFileTypeList();

        int indexOf = objName.lastIndexOf('.');
        if (indexOf == -1) {
            return false;
        }

        String fileType = objName.substring(indexOf + 1);
        return fileTypeList.contains(fileType);
    }
}
