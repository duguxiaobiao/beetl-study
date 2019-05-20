package com.lonely.beetlstudy.functions;

import com.lonely.beetlstudy.pojo.WhereCondition;
import com.lonely.beetlstudy.utils.WhereConditionUtil;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/5/16 10:27
 * @Description 根据参数构建 where条件
 */
public class BuildWhereSqlFunction implements Function {

    /**
     * 执行方法
     * @param paras
     * @param ctx
     * @return
     */
    @Override
    public Object call(Object[] paras, Context ctx) {

        if (paras.length == 0) {
            return "where 1 = 1";
        }

        try {
            List<WhereCondition> whereConditions = (List<WhereCondition>) paras[0];

            List<WhereCondition> childConditions = WhereConditionUtil.getChildConditions(whereConditions, null);

            return WhereConditionUtil.buildWhereSql(childConditions);
        } catch (Exception e) {

            throw new RuntimeException(e);

        }


    }
}
