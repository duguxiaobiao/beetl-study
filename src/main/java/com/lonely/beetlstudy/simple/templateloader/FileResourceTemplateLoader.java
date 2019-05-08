package com.lonely.beetlstudy.simple.templateloader;

import com.lonely.beetlstudy.pojo.User;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/7 16:58
 * @Description 文件资源路径模板加载
 */
public class FileResourceTemplateLoader {

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
        Template t = gt.getTemplate(filePath);
        paramsMap.forEach((key, value) -> {
            t.binding(key, value);
        });
        String str = t.render();
        System.out.println(str);
        return str;
    }

    /**
     * 针对普通文件,简单字符串处理
     *
     * @throws IOException
     */
    public static void loadFileSimple() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        //针对纯文件加载
        paramsMap.put("name", "beetl");
        loadAndConvert("/fileTemplateLoader.txt", paramsMap);
    }

    /**
     * 针对定界符和占位符测试
     *
     * @throws IOException
     */
    public static void loadFileTestDelimiter() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        loadAndConvert("定界符与占位符号.txt", paramsMap);
    }


    /**
     * 循环语句的使用
     *
     * @throws IOException
     */
    public static void loadFileTestLoop() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        //针对循环操作
        paramsMap.put("userList", new ArrayList<User>() {{
            this.add(new User("aa", "xxxxx"));
            this.add(new User("bb", "yyyyy"));
        }});
        loadAndConvert("循环语句.txt", paramsMap);
    }


    /**
     * 测试条件语句的使用
     */
    public static void loadFileTestCondition() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        loadAndConvert("条件语句.txt", paramsMap);
    }

    /**
     * 针对如何安全输出测试
     * @throws IOException
     */
    public static void loadFileTestSecurityPrint() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        loadAndConvert("安全输出.txt", paramsMap);
    }

    /**
     * 测试格式化函数的使用
     * @throws IOException
     */
    public static void loadFileTestFormat() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        loadAndConvert("格式化.txt", paramsMap);
    }

    public static void main(String[] args) throws IOException {

        //针对纯文件加载
        //loadFileSimple();

        //针对定界符he占位符号
        //loadFileTestDelimiter();

        //针对循环操作
        //loadFileTestLoop();

        //针对条件语句
        //loadFileTestCondition();

        //安全输出
        //loadFileTestSecurityPrint();

        //格式化的使用
        loadFileTestFormat();
    }

}
