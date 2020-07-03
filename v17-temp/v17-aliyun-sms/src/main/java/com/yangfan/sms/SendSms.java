package com.yangfan.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
/**
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.0</version>
</dependency>
 * @author yangfan
 */
public class SendSms {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", "LTAI4G6sXemrCzU7kGUKCrvv", "fB0eTCPTeSsVbPCEEbZExj8lQB1r1A");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18192328390");
        request.putQueryParameter("SignName", "杨帆");
        request.putQueryParameter("TemplateCode", "SMS_140120703");
        request.putQueryParameter("TemplateParam", "{\"code\":\"1111\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}

