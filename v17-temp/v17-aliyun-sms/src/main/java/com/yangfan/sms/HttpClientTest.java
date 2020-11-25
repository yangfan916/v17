package com.yangfan.sms;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class HttpClientTest {


    public static void doGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet("bai");
        //发送请求，并返回响应
        CloseableHttpResponse execute = client.execute(get);
        //处理相应
        //获取响应的状态码
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        //获取响应的内容
        HttpEntity entity = execute.getEntity();
        String content = EntityUtils.toString(entity, "utf-8");
        System.out.println(content);
        //关闭连接
        client.close();

    }

    public static void doGetParam() throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        //创建一个封装URI的对象。在该对象中可以给定请求参数
        URIBuilder bui = new URIBuilder("https://api.apiopen.top/searchAuthors");
        bui.addParameter("name", "李白");
        //创建一个Get请求对象
        HttpGet get = new HttpGet(bui.build());
        //发送请求，并返回响应
        CloseableHttpResponse execute = client.execute(get);
        //处理相应
        //获取响应的状态码
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        //获取响应的内容
        HttpEntity entity = execute.getEntity();
        String content = EntityUtils.toString(entity, "utf-8");
        System.out.println(content);
        //关闭连接
        client.close();
    }

    public static String doHttpGet(String url, String params){
        String result = null;
        //1.获取httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //接口返回结果
        CloseableHttpResponse response = null;
        //String paramStr = null;
        try {
            //paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("?");
            sb.append(params);
            System.out.println("=========" + sb.toString());
            //2.创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            //3.设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            /*此处可以添加一些请求头信息，例如：
            httpGet.addHeader("content-type","text/xml");*/
            //4.提交参数
            response = httpClient.execute(httpGet);
            //5.得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            //6.判断响应信息是否正确
            if(HttpStatus.SC_OK != statusCode){
                //终止并抛出异常
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            //7.转换成实体类
            HttpEntity entity = response.getEntity();
            if(null != entity){
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //8.关闭所有资源连接
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


}
