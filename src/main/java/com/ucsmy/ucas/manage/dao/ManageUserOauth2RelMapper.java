package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManageUserOauth2Rel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManageUserOauth2RelMapper {


    int insert(ManageUserOauth2Rel record);



    ManageUserOauth2Rel selectByOpenid(String openid);

    ManageUserOauth2Rel selectByUsername(String username);
}