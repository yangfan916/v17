package com.yangfan.v17order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangfan.v17order.config.AlipayProperties;
import com.yangfan.v17order.pojo.AlipayBizContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.alipay.api.AlipayConstants.APP_ID;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Controller
@RequestMapping("pay")
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping("generate")
    public void generatePay(String orderNo, HttpServletRequest httpRequest,
                         HttpServletResponse httpResponse)   throws ServletException, IOException {

        //获得初始化的AlipayClient
        /*AlipayClient alipayClient =  new DefaultAlipayClient(
                " https://openapi.alipaydev.com/gateway.do" ,
                "2016102700770935",
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCerpayBnfhFcmd7B8d2BPaVTe1anFm+R5/9xeZiWENOyQnIOzP+l8D8yFHKP+ngOgtX1I254A91VW9v2VqAWdCvfDbt4s9q3H61SzQ/7ygJmh7UFeD6HtUa73BN3XK6U6JQkbleJ7NiWGT3HnB+XUav2trbeMOZMzWCZ9twix7mxHr+2u0m+eONq0fYKA8SWLHXTsMEpl5pTn0ySBR/ThvDSJ5gV6EvxlBQr3EfsCFMK+HaOg1RkitdwPAKtxWc1FeeOoE5SAMY+IeeDJ/ljPRdbLnCQy4DYKuL1jluQ6+xyZd1Mxu+vorbsw9a4PA1YiF70ZTUA72ofKAquMosGXPAgMBAAECggEBAJc9MbXlwlZOjMYuqY2pR2q99AW9uO37HB285MpajJyutItyryKer/a0wQ/wvJHqo9+yzUo1JR5J+ZmLFV6Ojdun6yuO6XBSuqTmjvt381D0qnp1naPEbd+NfOLuOB+4dDkQaUAG0dx2RwFFjurZFsryjLBsoI80daK+syxKeFkpduHlSOpX8jgLP2k00mTKjf0OsQwGqNO+p+g3CXkgTRCBMr3cV+dnFzwigv4nrQWsyrtI3zi6+ho7rcZC210y0yCtEFZc3wpuTYyzlxsowRSMvyoYa5fvYP8Be5fWcjztU6dectQjki0wZDqIxxVBkzH4TwDmqFshGVM4SHd6euECgYEA2loSz00Kbc3QPnCXpq9yl9XpL3s3+cJ1icRJl0nJ8wICAm5jhNrHVtEZgIpF8GaA+Ct+uInvDcdUq9A36rLpZCrZywdYdZck3o7+oqyInWTpeseFOZyfwK22z3hv9fjoRnafsVq5YZqhHVy1WA4sxgTJ1O2SLvqIeidxJypWnT8CgYEAugq4qxhH2m74foqzmVuKDgHfdum4rFIXr2g8rMHhYkRyjVLYzyLYyPyKmt6gp3EJbduIgJLowfdBEcKcEBopsKlny91JKs6isRqsWKC6pIV3hO2ie22U8XKfUmZtv6nVxhN8hi5O7DlEVb7ZsopCIiKKTBFvMkcm8JUv5j/Sw3ECgYEAs6TNuu44eVSOUwDRVBse3Zrpid4HlJ4Pk/QBU84qr5rkSlnF2QUiYeQQ5jddCUdcLlGipXMeUajAmcbqalcePAIhQ6/ccIqUk+LzzqTClTPz/8btbhPVP3HsaM8+1hEeEPMBuanZwVq12XFv5W1sGtLPtzZqQepvpIiUyNNFhYMCgYEAtozcpa4+mzy2gtyw3HNxJMt7raArAJPQ/+IlX1MjSBxn1vqLGb1UwOrlSo/XTlXhbbWz3I3K7MEkTTwLjNVPQTUJT4ZETEfUZjoJETIt0k/QdlQDUl+1fbH+WYl+eUo6JJFsZ/G6qZqLziNmVAXMXNhNodSdqtUchYeuJ51Kv1ECgYBJHtwmcmAA9AZa/27PWZNrVHAuj735We2RpCx+j+to+xSY/CVOkHXg1bh147jAE5jkexMl7ceXPuTWNkAwW/a5rOxZDfb5ylCv3RHdjV3VrV38dvM8lHEOdXioxCQLXYd3mlxzW1POShnzIoMmtRdfsUkAFnBt2MO7+I1CuoFyCw==",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjm2eq+rUhrFIwqeHCohlQXag3UIODhaVzQPv+oxK8LMajFN9CeJBUFciyvhS85hpt1KJfyHBA1kzhRbh1jfP5Sr6q9t/n0LoDIoKO7/qG8A+zYmKV5mhgvdqtX66HHsIS+DAzdoT+TIRLu27Tcxqa8Q8t8fipNhbaGnUCPg6HaNDZPEbyLUrP7ShEnK4gS/Ic3CHtQqWI1Ts47OMnFVzCT7EYCeF62E8FjV4UXByIodoDgSCZX9DiNcIb/j3rTxu7vcdZflgaqgUZXep4ga6giu86+FioWmexeiF/YiESBhqsbd8d3yBegs4+hfZbzzNEoKVMsYLv1rtthyJB2eAYwIDAQAB",
                "RSA2");*/

        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl( "https://www.baidu.com" );
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl( "http://localhost:9097/pay/notifyPayResult" );
        //alipayRequest.putOtherTextParam("app_auth_token", "201611BB8xxxxxxxxxxxxxxxxxxxedcecde6");//如果 ISV 代商家接入电脑网站支付能力，则需要传入 app_auth_token，使用第三方应用授权；自研开发模式请忽略

        AlipayBizContent bizContent = new AlipayBizContent(
                orderNo,
                "FAST_INSTANT_TRADE_PAY",
                "3999",
                "小米10 Pro",
                "双模5G 骁龙865 1亿像素8K电影相机 50倍变焦 拍照智能新品手机 珍珠白 12G+512G(12期免息)");
        //填充业务参数
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bizContent);
        alipayRequest.setBizContent(json);
        String form= "" ;
        try  {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType( "text/html;charset=utf-8");
        //直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("notifyPayResult")
    @ResponseBody
    public void notifyPayResult(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {

        log.info("支付宝回调我们的接口 notifyPayResult");

        //将异步通知中收到的所有参数都存放到map中
        Map<String, String[]> sourceMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        Set<Map.Entry<String, String[]>> entries = sourceMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String[] values = entry.getValue();
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < values.length - 1; i++){
                value.append(values[i] + ",");
            }
            value.append(values[values.length-1]);
            paramsMap.put(entry.getKey(), value.toString());
        }
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(
                paramsMap,
                alipayProperties.getAlipayPulicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());
        if(signVerified){
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            log.info("验签成功，表明是支付宝发送过来的请求");
            //再进一步核对业务数据是否正确
            String trade_status = request.getParameter("trade_status");
            //判断交易状态是否为支付成功
            if("TRADE_SUCCESS".equals(trade_status)){
                //跟我们订单相关的业务数据
                String out_trade_no = request.getParameter("out_trade_no");
                String total_amount = request.getParameter("total_amount");
                String receipt_amount = request.getParameter("receipt_amount");
                //需要跟我们的订单数据做比较
                //如果匹配成功，更新订单的状态为“已支付”

                String trade_no = request.getParameter("trade_no");
                String app_id = request.getParameter("app_id");
                String buyer_id = request.getParameter("buyer_id");
                String seller_id = request.getParameter("seller_id");
                //记录支付流水（表），状态为“未对账”

                //搞定之后，给支付宝反馈success
                response.getWriter().write("success");
                response.getWriter().flush();
                response.getWriter().close();
            }
        }else{
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            log.info("验签失败，请确认是否为支付宝发送的");
            response.getWriter().write("failure");
            response.getWriter().flush();
            response.getWriter().close();
        }

    }
}
