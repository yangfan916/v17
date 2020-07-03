package com.yangfan.sms.pojo;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class SMSResponse {

    private String Message;
    private String RequestId;
    private String BizId;
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        BizId = bizId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @Override
    public String toString() {
        return "SMSResponse{" +
                "Message='" + Message + '\'' +
                ", RequestId='" + RequestId + '\'' +
                ", BizId='" + BizId + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }
}
