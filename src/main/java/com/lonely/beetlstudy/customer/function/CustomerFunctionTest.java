package com.lonely.beetlstudy.customer.function;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/8 10:57
 * @Description
 */
public class CustomerFunctionTest {


    /**
     * 加载和转换输出
     *
     * @return
     * @throws IOException
     */
    public static String loadAndConvert(String filePath, Map<String, Object> paramsMap) throws IOException {

        File file = ResourceUtils.getFile("classpath:templates/");
        //需要先在resource目录下建立 templates 目录，以及目录下的模板文件
        String root = file.getPath();
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        //注册自定义函数
        gt.registerFunction("dgPrint",new DgPrintFunction());

        Template t = gt.getTemplate(filePath);
        paramsMap.forEach((key, value) -> {
            t.binding(key, value);
        });

        String str = t.render();
        System.out.println(str);
        return str;
    }

    /**
     * 测试格式化函数的使用
     * @throws IOException
     */
    public static void loadFileTestFormat() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("message","天有多高，手有多骚~~~");
        loadAndConvert("自定义函数.txt", paramsMap);
    }


    public static void main(String[] args) throws IOException {
        loadFileTestFormat();
    }

}
