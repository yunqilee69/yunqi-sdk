package com.yunqi.sdk.oss;

import com.yunqi.sdk.oss.aliyun.AliyunOssFactory;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YunqiSdkOssApplicationTests {

    @Test
    public void contextLoads() throws FileNotFoundException {
        AliyunOssFactory aliyunOssFactory = new AliyunOssFactory();
        OssEnvParam envParam = OssEnvParam.builder()
                .endpoint("")
                .accessKey("")
                .secretKey("")
                .bucket("")
                .build();
        OssService ossService = aliyunOssFactory.getOssService(envParam);

        FileInputStream inputStream = new FileInputStream("C:\\Users\\yunqi\\Desktop\\测试.txt");
        String upload = ossService.upload(inputStream);
    }

}
