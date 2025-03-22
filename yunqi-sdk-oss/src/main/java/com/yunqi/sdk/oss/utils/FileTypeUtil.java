package com.yunqi.sdk.oss.utils;

import com.yunqi.sdk.oss.exception.NotFoundFileTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FileTypeUtil {

    private static final Map<String, String> MAGIC_NUMBER_MAP = new HashMap<>();

    // 默认文件类型为txt
    private static final String DEFAULT_TYPE = "txt";
    
    static {
        // images
        MAGIC_NUMBER_MAP.put("FFD8FF", "jpeg");
        MAGIC_NUMBER_MAP.put("89504E47", "png");
        MAGIC_NUMBER_MAP.put("47494638", "gif");
        MAGIC_NUMBER_MAP.put("49492A00", "tif");
        MAGIC_NUMBER_MAP.put("424D", "bmp");
        //
        MAGIC_NUMBER_MAP.put("41433130", "dwg"); // CAD
        MAGIC_NUMBER_MAP.put("38425053", "psd");
        MAGIC_NUMBER_MAP.put("7B5C727466", "rtf"); // 日记本
        MAGIC_NUMBER_MAP.put("3C3F786D6C", "xml");
        MAGIC_NUMBER_MAP.put("68746D6C3E", "html");
        MAGIC_NUMBER_MAP.put("44656C69766572792D646174653A", "eml"); // 邮件
        MAGIC_NUMBER_MAP.put("D0CF11E0", "doc");
        MAGIC_NUMBER_MAP.put("D0CF11E0", "xls");//excel2003版本文件
        MAGIC_NUMBER_MAP.put("5374616E64617264204A", "mdb");
        MAGIC_NUMBER_MAP.put("252150532D41646F6265", "ps");
        MAGIC_NUMBER_MAP.put("255044462D312E", "pdf");
        MAGIC_NUMBER_MAP.put("504B0304", "ofd");
        MAGIC_NUMBER_MAP.put("504B0304", "docx");
        MAGIC_NUMBER_MAP.put("504B0304", "xlsx");//excel2007以上版本文件
        MAGIC_NUMBER_MAP.put("52617221", "rar");
        MAGIC_NUMBER_MAP.put("57415645", "wav");
        MAGIC_NUMBER_MAP.put("41564920", "avi");
        MAGIC_NUMBER_MAP.put("2E524D46", "rm");
        MAGIC_NUMBER_MAP.put("000001BA", "mpg");
        MAGIC_NUMBER_MAP.put("000001B3", "mpg");
        MAGIC_NUMBER_MAP.put("6D6F6F76", "mov");
        MAGIC_NUMBER_MAP.put("3026B2758E66CF11", "asf");
        MAGIC_NUMBER_MAP.put("4D546864", "mid");
        MAGIC_NUMBER_MAP.put("1F8B08", "gz");
    }

    public static String getFileType(InputStream inputStream) {
        String val = MAGIC_NUMBER_MAP.get(getFileHeader(inputStream));
        if (val == null) {
            throw new NotFoundFileTypeException();
        }
        for (Map.Entry<String, String> entry : MAGIC_NUMBER_MAP.entrySet()) {
            if (val.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return DEFAULT_TYPE;
    }

    /**
     * @return 文件头信息
     * 方法描述：根据输入流获取文件头信息
     */
    private static String getFileHeader(InputStream inputStream) {
        InputStream is = null;
        String value = null;
        is = inputStream;
        byte[] b = new byte[8];
        // 最多读取8个字节
        try {
            is.read(b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        value = bytesToHexString(b);
        return value;
    }

    /**
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}
