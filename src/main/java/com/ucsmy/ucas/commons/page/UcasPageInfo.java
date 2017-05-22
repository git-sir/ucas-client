package com.ucsmy.ucas.commons.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucsmy.commons.interceptor.domain.PageInfo;
import com.ucsmy.commons.interceptor.domain.Pageable;

import java.util.List;

/**
 * Created by ucs_panwenbo on 2017/4/17.
 */
public class UcasPageInfo<T> extends PageInfo<T>{
    private List<T> resultList;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private int pages;

    @Override
    public void init(Pageable pageable) {
        this.resultList=super.getData();
        this.pageNo=pageable.getPageNumber();
        this.pageSize=pageable.getPageSize();
        this.totalCount=super.getCount();
        this.pages = (int)(this.totalCount / this.pageSize) + (this.totalCount % this.pageSize == 0 ? 0 : 1);
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    @JsonIgnore
    public long getCount() {
        return super.getCount();
    }

    @Override
    @JsonIgnore
    public List<T> getData() {
        return super.getData();
    }
}
