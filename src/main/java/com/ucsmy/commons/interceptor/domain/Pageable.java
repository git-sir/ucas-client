package com.ucsmy.commons.interceptor.domain;

/**
 * Created by ucs_zhongtingyuan on 2017/4/7.
 */
public interface Pageable {
    int getPageNumber();

    int getPageSize();

    int getOffset();
}
