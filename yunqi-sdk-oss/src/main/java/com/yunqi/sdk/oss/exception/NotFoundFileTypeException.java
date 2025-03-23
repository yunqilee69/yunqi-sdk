package com.yunqi.sdk.oss.exception;

/**
 * 找不到文件对应的类型
 */
public class NotFoundFileTypeException extends RuntimeException {
    public NotFoundFileTypeException() {
        super("File type not found.");
    }
}
