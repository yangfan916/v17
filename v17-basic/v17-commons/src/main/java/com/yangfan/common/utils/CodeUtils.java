package com.yangfan.common.utils;

import java.util.Random;

/**
 * @author yangfan
 * @version 1.0
 * @description 生成验证码的方法
 */
public class CodeUtils {

    public static String generateCode(int len){
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }
}
