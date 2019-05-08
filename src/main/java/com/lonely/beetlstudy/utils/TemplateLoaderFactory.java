package com.lonely.beetlstudy.utils;

import com.lonely.beetlstudy.enums.TemplateResourceTypeEnum;
import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

/**
 * @author ztkj
 * @Date 2019/5/8 11:27
 * @Description 根据情况构建 模板资源加载器
 */
public class TemplateLoaderFactory {


    /**
     * 根据类型 来 构建不同的资源加载器
     * @param typeEnum
     * @param path
     * @return
     */
    public static ResourceLoader getResourceLoader(TemplateResourceTypeEnum typeEnum,String path){

        ResourceLoader resourceLoader = null;
        switch (typeEnum){
            case STRING_RESOURCE:
                resourceLoader = new StringTemplateResourceLoader();
                break;
            case CLASSPATH_RESOURCE:
                resourceLoader = new ClasspathResourceLoader(path);
                break;
            case FILE_RESOURCE:
                resourceLoader = new FileResourceLoader(path);
                break;
        }
        return resourceLoader;
    }

}
