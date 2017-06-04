package com.ucsmy.ucas.commons.constant;

/**
 * Created by ucs_mojiazhou on 2017/4/28.
 */


    public  enum HttpResponseCode{
        SUCCESS("成功", 0), INVALID_REQUEST("无效请求", 40001), EXPIRE_TOKEN("token过期", 40002),
        INSUFFICIENT_SCOPE("没有授权", 40003),
        INVALID_TOKEN("token无效", 40004),
        SERVER_ERROR("调用外部接口异常", 40016)
        ;



        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private HttpResponseCode(String name, int index) {
            this.name = name;
            this.index = index;
        }


    public String getName() {
        return name;
    }

/*    public void setName(String name) {
        this.name = name;
    }*/

    public int getIndex() {
        return index;
    }

/*
    public void setIndex(int index) {
        this.index = index;
    }
*/

}

