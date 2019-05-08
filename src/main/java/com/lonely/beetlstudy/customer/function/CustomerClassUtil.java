package com.lonely.beetlstudy.customer.function;

import org.beetl.core.Context;

import java.io.IOException;

/**
 * @author ztkj
 * @Date 2019/5/8 11:05
 * @Description 自定义类的使用
 */
public class CustomerClassUtil {

    /**
     * 测试输出静态方法
     * @param message
     * @param context
     * @return
     */
    public static String print(String message, Context context){

        try {
            context.byteWriter.write( ("CustomerClassUtil print : "  + message).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String getMessage(String message){
        return message;
    }
}
