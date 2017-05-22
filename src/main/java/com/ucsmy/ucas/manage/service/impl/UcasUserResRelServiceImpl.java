package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.interceptor.domain.PageRequest;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.commons.page.UcasPageInfo;
import com.ucsmy.ucas.manage.dao.UcasUserResRelMapper;
import com.ucsmy.ucas.manage.ext.UserRelResourcePojo;
import com.ucsmy.ucas.manage.service.UcasUserResRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenqilin on 2017/4/25.
 */
@Service
public class UcasUserResRelServiceImpl implements UcasUserResRelService {

    @Autowired
    private UcasUserResRelMapper ucasUserResRelMapper;

    @Override
    @Logger(printSQL = true)
    public UcasPageInfo<UserRelResourcePojo> queryUserRelResource(String openId, int pageNum, int pageSize) {
        UcasPageInfo<UserRelResourcePojo> list = ucasUserResRelMapper.queryUserRelResource(openId, new PageRequest(pageNum, pageSize));
        List<UserRelResourcePojo> resultList = list.getResultList();
        // 添加序号
        for(int i = 0; i < resultList.size(); i++) {
            UserRelResourcePojo res = resultList.get(i);
            res.setNo(i + 1);
            resultList.set(i, res);
        }
        list.setResultList(resultList);
        return list;
    }
}
