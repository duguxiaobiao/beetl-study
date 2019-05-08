package com.lonely.beetlstudy.pojo;

import lombok.Data;
import org.beetl.ext.fn.Print;

/**
 * @author ztkj
 * @Date 2019/5/7 17:36
 * @Description
 */
@Data
public class User {

    /**
     * 用户名
     */
    private String name;
    /**
     * 地址
     */
    private String address;

    public User() {
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
