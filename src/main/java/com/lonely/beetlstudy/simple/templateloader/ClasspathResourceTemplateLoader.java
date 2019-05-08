package com.lonely.beetlstudy.simple.templateloader;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

/**
 * @author ztkj
 * @Date 2019/5/7 17:21
 * @Description classpath资源模板加载
 */
public class ClasspathResourceTemplateLoader {


    /**
     * 加载和转换输出
     * @return
     * @throws IOException
     */
    public static String loadAndConvert() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/templates/");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/fileTemplateLoader.txt");
        t.binding("name","duguxiaobiao");
        String str = t.render();
        System.out.println(str);

        return str;
    }


    public static void main(String[] args) throws IOException {
        loadAndConvert();
    }

}
