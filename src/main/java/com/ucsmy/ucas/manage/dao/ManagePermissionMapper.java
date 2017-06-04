package com.ucsmy.ucas.manage.dao;

import com.ucsmy.ucas.manage.entity.ManagePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ucas_client_permission
 * Created by chenqilin on 2017/4/14.
 */
@Mapper
public interface ManagePermissionMapper {

    /**
     * 根据条件查询
     * @param moduleId 菜单Id
     * @param name 权限名称，全等查询，不用like
     * @param excludeId 排除的权限Id
     * @return
     */
    List<ManagePermission> queryPermissionByCondition(@Param("moduleId") String moduleId
            , @Param("name") String name, @Param("excludeId") String excludeId);

    List<ManagePermission> queryPermissionByModuleID(@Param("id") String id);

    ManagePermission getPermissionById(@Param("permissionId") String permissionId);

    int updatePermission(ManagePermission permission);

    int deletePermissionByID(@Param("permissionId") String permissionId);

    int deletePermissionByModule(@Param("id") String id);

    int addPermission(ManagePermission permission);

    List<ManagePermission> queryPermissionAll();

    List<ManagePermission> queryPermissionByUserID(@Param("userId") String userId);
}
