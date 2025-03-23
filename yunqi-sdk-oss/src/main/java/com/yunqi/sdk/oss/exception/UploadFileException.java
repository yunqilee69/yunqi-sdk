package com.yunqi.sdk.oss.exception;

public class UploadFileException extends RuntimeException {

    public UploadFileException() {
        super("Upload file failed");
    }

    public UploadFileException(Throwable cause) {
        super(cause);
    }
}
