package com.lonely.beetlstudy.pojo;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/5/15 14:36
 * @Description
 */
public class Location {
    private int id; //id
    private String name; //名称
    private int parentId; //父id
    //为了组合多维集合添加的属性
    private List<Location> list;

    public Location() {
    }

    public Location(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Location> getList() {
        return list;
    }

    public void setList(List<Location> list) {
        this.list = list;
    }
}
