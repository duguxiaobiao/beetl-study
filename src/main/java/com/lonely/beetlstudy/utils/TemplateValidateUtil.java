package com.lonely.beetlstudy.utils;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.junit.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * @author ztkj
 * @Date 2019/5/8 13:49
 * @Description 模板校验工具
 */
public class TemplateValidateUtil {

    /**
     * 默认展示当前错误行的前后行范围
     */
    public static Integer SHOW_RANGE = 3;

    /**
     * 校验当前模板语法是否有效。
     *
     * @param groupTemplate
     * @param key           针对 StringTemplateResourceLoader来说是脚本内容，其余的是脚本路径
     * @return
     */
    public static boolean checkTemplateState(GroupTemplate groupTemplate, String key) {
        BeetlException beetlException = validate(groupTemplate, key);
        return beetlException == null;
    }

    /**
     * 最原始的校验，需要自己判断是否有效
     *
     * @param groupTemplate
     * @param key
     * @return
     */
    public static BeetlException validate(GroupTemplate groupTemplate, String key) {
        Assert.assertNotNull(key, "key 不能为空");
        return groupTemplate.validateTemplate(key);
    }

    /**
     * 模拟源码中 针对console的异常展示。  校验和打印错误消息，如果响应为null，则模板没有异常，反之，返回异常消息
     *
     * @param groupTemplate
     * @param key
     * @return
     * @throws IOException
     */
    public static String checkAndPrint(GroupTemplate groupTemplate, String key) throws IOException {
        BeetlException beetlException = validate(groupTemplate, key);
        if (beetlException == null) {
            return null;
        }
        //校验失败，拼接构建输出
        ErrorInfo error = new ErrorInfo(beetlException);
        StringBuilder result = new StringBuilder(">>>");
        result.append(LocalDateTime.now())
                .append(":")
                .append(error.getType())
                .append(MessageFormat.format(":>>> 位于{0}行 ", error.getErrorTokenLine()))
                .append(MessageFormat.format("资源：{0}", beetlException.resource.getId()))
                .append("\r\n")
                .append(error.getMsg() + "\r\n")
                .append(showWrongContentBeforeAndAfterLines(beetlException, error.getErrorTokenLine(), SHOW_RANGE));

        return result.toString();

    }

    /**
     * 根据异常，构建异常显示
     *
     * @param beetlException
     * @param currentLineNo
     * @param showLine
     * @return
     * @throws IOException
     */
    private static String showWrongContentBeforeAndAfterLines(BeetlException beetlException, int currentLineNo, int showLine) throws IOException {
        Resource resource = beetlException.resource;
        int[] range = getRange(currentLineNo, showLine);

        //获取脚本中指定行的内容信息
        String content = resource.getContent(range[0], range[1]);
        if (content != null) {
            StringBuilder result = new StringBuilder();

            //根据换行符分割
            String[] strs = content.split(beetlException.cr);
            int lineNumber = range[0];
            for (int i = 0; i < strs.length; i++) {
                result.append(MessageFormat.format("{0}|{1}\r\n", lineNumber, strs[i]));
                lineNumber++;
            }

            return result.toString();
        }
        return null;
    }

    /**
     * 获取指定当前的行前后指定范围的行索引
     *
     * @param line  当前行索引
     * @param range 要求显示前后范围多少行
     * @return 前后范围的索引
     */
    private static int[] getRange(int line, int range) {
        int startLine = 0;
        int endLine = 0;
        if (line > range) {
            startLine = line - range;
        } else {
            startLine = 1;
        }

        endLine = startLine + 2 * range;
        return new int[]
                {startLine, endLine};
    }
}
