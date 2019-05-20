package com.lonely.beetlstudy.utils;

import com.lonely.beetlstudy.enums.TemplateResourceTypeEnum;
import com.lonely.beetlstudy.functions.BuildWhereSqlFunction;
import com.lonely.beetlstudy.pojo.WhereCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ztkj
 * @Date 2019/5/8 11:36
 * @Description 文件资源类型模板
 */
public class FileResourceTemplateSingleton {

    private static GroupTemplate groupTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FileResourceTemplateSingleton.class);

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 模板配置初始化
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        //StringTemplateResourceLoader resourceLoader = (StringTemplateResourceLoader) TemplateLoaderFactory.getResourceLoader(TemplateResourceTypeEnum.STRING_RESOURCE, null);

        File file = ResourceUtils.getFile("classpath:templates/");
        //需要先在resource目录下建立 templates 目录，以及目录下的模板文件
        String root = file.getPath();
        FileResourceLoader resourceLoader = (FileResourceLoader) TemplateLoaderFactory.getResourceLoader(TemplateResourceTypeEnum.FILE_RESOURCE, root);

        //获取默认beetl配置
        Configuration cfg = Configuration.defaultConfiguration();

        //设置模板
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunction("buildWhereSql", new BuildWhereSqlFunction());
    }


    /**
     * 根据传入的脚本进行校验以及生成Template对象
     *
     * @param templatePathName
     * @return
     * @throws IOException
     */

    public static Template generatorTemplate(String templatePathName) throws IOException {
        //根据传入的字符串内容构建模板信息
        //这里可以将传入的模板保存到数据库中，添加缓存。

        //校验模板
        String valiadteResult = TemplateValidateUtil.checkAndPrint(groupTemplate, templatePathName);
        if (!StringUtils.isEmpty(valiadteResult)) {
            //存在异常
            logger.error("校验模板出现异常，异常原因：{}", valiadteResult);
            throw new RuntimeException(valiadteResult);
        }
        Template t = groupTemplate.getTemplate(templatePathName);

        return t;
    }

    /**
     * 根据参数进行转换输出
     *
     * @param templatePathName
     * @param dataMap
     * @return
     * @throws IOException
     */
    public static String convertToMessage(String templatePathName, Map<String, Object> dataMap) throws IOException {
        Template template = generatorTemplate(templatePathName);
        if (template != null) {
            template.binding(dataMap);
            return template.render();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {

        //Map<String, Object> paramsMap = new HashMap<>();
        //System.out.println(FileResourceTemplateSingleton.convertToMessage("格式化.txt",paramsMap));


        /*Map<String, Object> paramsMap = new HashMap<>();*/

        //针对循环操作
       /* paramsMap.put("userList", new ArrayList<User>() {{
            this.add(new User("aa", "xxxxx"))
            this.add(new User("bb", "yyyyy"));
        }});*/
        //System.out.println(FileResourceTemplateSingleton.convertToMessage("循环语句.txt",paramsMap));
        //System.out.println(FileResourceTemplateSingleton.convertToMessage("/filetest/循环语句1.txt",paramsMap));
        ;


    }


    /**
     * 构建where条件
     *
     * @throws IOException
     */
    private static void buildWhereSql() throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("tableName", "user");
        paramsMap.put("paramsCols", new ArrayList<String>() {{
            this.add("id");
            this.add("name");
        }});

        List<List<String>> values = new ArrayList<>();
        paramsMap.put("paramsValues", values);
        paramsMap.put("whereConditions", new ArrayList<WhereCondition>() {{
            this.add(new WhereCondition("1", null, "name", "dugu", "="));
            this.add(new WhereCondition("11", "1", "age", "25", "="));
            this.add(new WhereCondition("12", "1", "age", "30", "="));
            this.add(new WhereCondition("111", "11", "age", "30", ">"));
            this.add(new WhereCondition("2", null, "address", "武汉", "llike"));
        }});

        //获取子节点信息
        //List<WhereCondition> allWhereCondition = getSonWhereConditions(whereConditions, null);

        System.out.println(convertToMessage("/mysql/mysql_delete.beetl", paramsMap));
    }

    private static String printSonWhereCondition(WhereCondition whereCondition, int level) {
        StringBuffer printSonMessage = new StringBuffer();
        printSonMessage.append(whereCondition).append("\r\n");
        if (!whereCondition.getSonWhereConditions().isEmpty()) {
            for (WhereCondition sonWhereCondition : whereCondition.getSonWhereConditions()) {
                for (int i = 0; i < level; i++) {
                    printSonMessage.append("\t");
                }
                printSonMessage.append("and" + printSonWhereCondition(sonWhereCondition, level + 1));
            }


        }

        return printSonMessage.toString();
    }


    private static void buildMessage(WhereCondition parentWhereCondition) {
        StringBuffer printSonMessage = new StringBuffer("or (");

        //判断是否
        if (CollectionUtils.isNotEmpty(parentWhereCondition.getSonWhereConditions())) {

            /*for (WhereCondition sonWhereCondition : parentWhereCondition.getSonWhereConditions()) {
                printSonMessage.append(printSonWhereCondition(sonWhereCondition,level+1));
            }*/


        }
        printSonMessage.append(")");

    }


    private static List<WhereCondition> getSonWhereConditions(List<WhereCondition> whereConditions, String parentId) {
        List<WhereCondition> sonWhereCondition = new ArrayList<>();

        for (WhereCondition whereCondition : whereConditions) {

            parentId = Optional.ofNullable(parentId).orElse("");

            String curerentParentId = Optional.ofNullable(whereCondition.getPid()).orElse("");

            if (parentId.equals(curerentParentId)) {
                whereCondition.setSonWhereConditions(getSonWhereConditions(whereConditions, whereCondition.getId()));
                sonWhereCondition.add(whereCondition);
            }
        }
        return sonWhereCondition;
    }

}
