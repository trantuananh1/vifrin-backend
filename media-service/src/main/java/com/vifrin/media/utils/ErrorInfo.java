package com.vifrin.media.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ErrorInfo {
    private static final Logger logger = LoggerFactory.getLogger(ErrorInfo.class);

    public static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream is = ErrorInfo.class.getResourceAsStream("/error_info.properties");
            properties.load(is);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static final int UNKNOWN_ERROR_CODE = 1001;

    private int code;
    private List<String> messages = new ArrayList<>();

    public static ErrorInfo newInstance(int code, String message) {
        return new ErrorInfo(code, message);
    }

    public ErrorInfo(int code, String... messages) {
        this.code = code;
        for (String message : messages) {
            this.messages.add(message);
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return messages.size() > 0 ? messages.get(0) : null;
    }

    public static final ErrorInfo INTERNAL_SERVER_ERROR = new ErrorInfo(UNKNOWN_ERROR_CODE,
            properties.getProperty("server.error"));

    public static final ErrorInfo NOT_SUPPORT_ERROR = new ErrorInfo(1048, properties.getProperty("file.not.support.error"));
}
