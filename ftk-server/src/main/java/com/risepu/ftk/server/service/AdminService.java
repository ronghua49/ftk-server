package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.AdminUser;

//@Remote(path = "/admin")
public interface AdminService {

    /**
     * 根据用户名查询管理员
     *
     * @param userName 用户名
     * @return
     */
    AdminUser findAdminByName(String userName);

    /**
     * 更新管理员信息
     *
     * @param adminUser
     */
    void updateAdminUser(AdminUser adminUser);

    //

}
