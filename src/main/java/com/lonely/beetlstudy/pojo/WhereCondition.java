package com.lonely.beetlstudy.pojo;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/5/15 13:42
 * @Description where条件
 */
public class WhereCondition {


    private String id;

    private String pid;

    private String left;

    private String right;

    private String jungle;

    private List<WhereCondition> sonWhereConditions;

    public WhereCondition(String id, String pid, String left, String right, String jungle) {
        this.id = id;
        this.pid = pid;
        this.left = left;
        this.right = right;
        this.jungle = jungle;
    }

    public WhereCondition() {
    }

    @Override
    public String toString() {
        /*return "WhereCondition{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", left='" + left + '\'' +
                ", right='" + right + '\'' +
                ", jungle='" + jungle + '\'' +
                '}';*/


        return MessageFormat.format("({0} {1} {2})",left,jungle,right);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getJungle() {
        return jungle;
    }

    public void setJungle(String jungle) {
        this.jungle = jungle;
    }

    public List<WhereCondition> getSonWhereConditions() {
        return sonWhereConditions;
    }

    public void setSonWhereConditions(List<WhereCondition> sonWhereConditions) {
        this.sonWhereConditions = sonWhereConditions;
    }
}
