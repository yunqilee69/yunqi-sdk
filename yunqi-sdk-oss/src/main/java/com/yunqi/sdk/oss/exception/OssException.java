package com.yunqi.sdk.oss.exception;

/**
 * 将OSS发生的异常进行包装一下
 */
public class OssException extends RuntimeException {

    public OssException() {
        super();
    }

    public OssException(Throwable cause) {
        super(cause);
    }
}
