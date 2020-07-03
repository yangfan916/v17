package com.yangfan.v17smsservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.yangfan.sms.api.ISMS;
import com.yangfan.sms.pojo.SMSResponse;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Service
public class SMSService implements ISMS {
    @Override
    public SMSResponse sendCodeMessage(String phone, String code) {

        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", "LTAI4G6sXemrCzU7kGUKCrvv", "fB0eTCPTeSsVbPCEEbZExj8lQB1r1A");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "杨帆");
        request.putQueryParameter("TemplateCode", "SMS_140120703");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);

            Gson gson = new Gson();
            return gson.fromJson(response.getData(), SMSResponse.class);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*public static void main(String[] args) {
        String str = "{\n" +
                "\t\"Message\": \"OK\",\n" +
                "\t\"RequestId\": \"873043ac-bcda-44db-9052-2e204c6ed20f\",\n" +
                "\t\"BizId\": \"607300000000000000^0\",\n" +
                "\t\"Code\": \"OK\"\n" +
                "}";
        Gson gson = new Gson();
        System.out.println(gson.fromJson(str, SMSResponse.class));
    }*/
}
