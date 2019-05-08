package com.lonely.beetlstudy.customer.function;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.io.IOException;

/**
 * @author ztkj
 * @Date 2019/5/8 10:39
 * @Description 自定义 打印方法
 */
public class DgPrintFunction implements Function {

    /**
     * 自定义打印方法实现
     * @param objects 传入的参数
     * @param context 模板上下文
     * @return
     */
    @Override
    public Object call(Object[] objects, Context context) {

        //获取要打印的字符串
        Object param = objects[0];

        if(param != null){
            try {
                context.byteWriter.write(("Dg Print : "  + param.toString()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
