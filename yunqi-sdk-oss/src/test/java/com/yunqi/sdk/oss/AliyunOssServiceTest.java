package com.yunqi.sdk.oss;

import com.yunqi.sdk.oss.aliyun.AliyunOssFactory;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AliyunOssServiceTest {

    private static final String FILE_PATH = "C:\\Users\\yunqi\\Desktop\\test\\测试.txt";

    private OssService getOssService() {
        AliyunOssFactory aliyunOssFactory = new AliyunOssFactory();
        OssEnvParam envParam = OssEnvParam.builder()
                .endpoint("oss-cn-shanghai.aliyuncs.com")
                .accessKey("")
                .secretKey("")
                .bucket("")
                .build();
        return aliyunOssFactory.getOssService(envParam);
    }

    @Test
    public void uploadTest() {
        OssService ossService = getOssService();
        try (InputStream inputStream = new FileInputStream(FILE_PATH)) {
            URL url = ossService.upload("123.txt", inputStream);
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadTest() throws IOException {
        OssService ossService = getOssService();

        InputStream download = ossService.download("123.txt");
        while (download.read() != -1) {
            byte[] bytes = new byte[1024];
            download.read(bytes);
            System.out.println(new String(bytes));
        }
    }

}
