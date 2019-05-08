package com.lonely.beetlstudy.utils;

import com.lonely.beetlstudy.enums.TemplateResourceTypeEnum;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author ztkj
 * @Date 2019/5/8 11:36
 * @Description 构建单例的 字符串类型模板
 */
public class StringTemplateSingleton {

    private static GroupTemplate groupTemplate;

    private static final Logger logger = LoggerFactory.getLogger(StringTemplateSingleton.class);

    private StringTemplateSingleton() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    private static StringTemplateSingleton stringTemplateSingleton = new StringTemplateSingleton();

    public static StringTemplateSingleton getInstance() {
        return stringTemplateSingleton;
    }

    /**
     * 模板配置初始化
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        StringTemplateResourceLoader resourceLoader = (StringTemplateResourceLoader) TemplateLoaderFactory.getResourceLoader(TemplateResourceTypeEnum.STRING_RESOURCE, null);

        //获取默认beetl配置
        Configuration cfg = Configuration.defaultConfiguration();

        //设置模板
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
    }


    /**
     * 根据传入的脚本进行校验以及生成Template对象
     *
     * @param templateMessage
     * @return
     * @throws IOException
     */

    public static Template generatorTemplate(String templateMessage) throws IOException {
        //根据传入的字符串内容构建模板信息
        //这里可以将传入的模板保存到数据库中，添加缓存。

        //校验模板
        String valiadteResult = TemplateValidateUtil.checkAndPrint(groupTemplate, templateMessage);
        if (!StringUtils.isEmpty(valiadteResult)) {
            //存在异常
            logger.error("校验模板出现异常，异常原因：{}", valiadteResult);
            throw new RuntimeException(valiadteResult);
        }
        Template t = groupTemplate.getTemplate(templateMessage);

        return t;
    }

    /**
     * 根据参数进行转换输出
     *
     * @param templateMessage
     * @param dataMap
     * @return
     * @throws IOException
     */
    public static String convertToMessage(String templateMessage, Map<String, Object> dataMap) throws IOException {
        Template template = generatorTemplate(templateMessage);
        if (template != null) {
            template.binding(dataMap);
            return template.render();
        }
        return null;
    }


}
