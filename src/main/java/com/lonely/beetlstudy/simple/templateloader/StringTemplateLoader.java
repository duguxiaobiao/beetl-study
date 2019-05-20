package com.lonely.beetlstudy.simple.templateloader;

import com.lonely.beetlstudy.utils.StringTemplateSingleton;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/7 16:53
 * @Description 字符串模板加载使用
 */
public class StringTemplateLoader {

    /**
     * 加载和转换输出(最原始方式)
     *
     * @return
     * @throws IOException
     */
    public static String loadAndConvert() throws IOException {
        //获取字符串模板资源加载器
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        //获取默认beetl配置
        Configuration cfg = Configuration.defaultConfiguration();
        //设置模板
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        //根据传入的字符串内容构建模板信息
        //这里可以将传入的模板保存到数据库中，添加缓存。
        Template t = gt.getTemplate("hello,${name}");
        //绑定变量
        t.binding("name", "beetl");
        //解析转换
        String str = t.render();
        System.out.println(str);

        return str;
    }

    /**
     * 快速使用方式
     *
     * @throws IOException
     */
    public static void rapidUse() throws IOException {
        Map<String, Object> parasMap = new HashMap<>();
        parasMap.put("name", "beetl");

        //使用针对 字符串模板资源加载的使用快速使用
        String result = StringTemplateSingleton.convertToMessage("hello,${name}", parasMap);
        System.out.println(result);
    }


    public static void main(String[] args) throws IOException {
        //loadAndConvert();

        rapidUse();
    }
}
