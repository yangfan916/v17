package com.yangfan.sms;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class SMSResponse {

    private String message;
    private String requestId;
    private String bizId;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SMSResponse{" +
                "message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
