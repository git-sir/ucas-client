package com.ucsmy.commons.utils;

/**
 * 通用状态枚举类
 * Created by chenqilin on 2017/4/21.
 */
public enum CommStatusEnum {
    ACCOUNT_IN_USE("在用", "0"),
    ACCOUNT_IN_CLOD("冻结", "1"),
    SYS_DELETE("删除", "2"),
    SYS_INUSE("正常", "0"),
    SYS_UNUSE("失效", "1");


    private String value;

    private String name;

    private CommStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
