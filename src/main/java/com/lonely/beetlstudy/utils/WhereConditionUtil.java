package com.lonely.beetlstudy.utils;

import com.lonely.beetlstudy.pojo.WhereCondition;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ztkj-hzb
 * @Date 2019/5/16 10:19
 * @Description where条件组装工具类
 */
public class WhereConditionUtil {


    /**
     * 将条件根据父子关系分类
     *
     * @param whereConditions
     * @param pid
     * @return
     */
    public static List<WhereCondition> getChildConditions(List<WhereCondition> whereConditions, String pid) {
        List<WhereCondition> arrs = new ArrayList<>();

        for (WhereCondition whereCondition : whereConditions) {
            pid = Optional.ofNullable(pid).orElse("");
            String currPid = Optional.ofNullable(whereCondition.getPid()).orElse("");

            if (pid.equals(currPid)) {
                whereCondition.setSonWhereConditions(getChildConditions(whereConditions, whereCondition.getId()));
                arrs.add(whereCondition);
            }
        }
        return arrs;
    }


    /**
     * 根据父子关系分组后的数据进行构建where部分的sql语句
     *
     * @param whereConditions
     */
    public static String buildWhereSql(List<WhereCondition> whereConditions) {
        StringBuilder result = new StringBuilder("where 1=1 \n");

        for (int i = 0; i < whereConditions.size(); i++) {
            WhereCondition whereCondition = whereConditions.get(i);

            if (i == 0) {
                result.append(MessageFormat.format(" and {0}", getDetailSql(whereCondition))).append("\n");

            } else {
                result.append(MessageFormat.format(" or ({0})", getDetailSql(whereCondition))).append("\n");
            }
        }

        //System.out.println(result);

       //System.out.println("-------------------------------------");

        return result.toString();

    }

    /**
     * 递归返回当前节点下的所有节点构建后的sql语句
     *
     * @param whereCondition
     * @return
     */
    private static String getDetailSql(WhereCondition whereCondition) {
        StringBuilder result = new StringBuilder();
        result.append(whereCondition.toString());
        List<WhereCondition> whereConditions = Optional.ofNullable(whereCondition.getSonWhereConditions()).orElse(new ArrayList<>());
        if (!whereConditions.isEmpty()) {

            if (whereConditions.size() == 1) {
                result.append(" and ").append(whereConditions.get(0).toString());
            } else {

                //and (xx or xx)
                for (int i = 0; i < whereConditions.size(); i++) {
                    WhereCondition condition = whereConditions.get(i);
                    if (i == 0) {
                        result.append(" and (").append(getDetailSql(condition));
                    } else if (i != whereConditions.size() - 1) {
                        result.append(" or ").append(getDetailSql(condition));
                    } else {
                        result.append(" or ").append(getDetailSql(condition)).append(") ");
                    }
                }

            }

        }
        return result.toString();
    }


}
