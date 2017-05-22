package com.ucsmy.ucas.manage.ext;

import com.ucsmy.ucas.manage.entity.ManageOrganization;
import com.ucsmy.ucas.manage.entity.ManageRole;
import com.ucsmy.ucas.manage.entity.ManageUserAccount;
import com.ucsmy.ucas.manage.entity.ManageUserProfile;

/**
 * userProfile扩展类
 * Created by chenqilin on 2017/4/17.
 */
public class UserProfilePojo extends ManageUserProfile {

    private ManageUserAccount userAccount;

    private ManageRole role;

    private ManageOrganization org;

    public ManageUserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(ManageUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public ManageRole getRole() {
        return role;
    }

    public void setRole(ManageRole role) {
        this.role = role;
    }

    public ManageOrganization getOrg() {
        return org;
    }

    public void setOrg(ManageOrganization org) {
        this.org = org;
    }
}
