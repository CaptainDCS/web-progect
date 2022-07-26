package com.Captain.web.prject.dao.role;

import com.Captain.web.prject.domain.Role;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/25 19:39
 * description:
 */
public interface RoleDao {
    //获取角色列表
    public List<Role> getRoleList();
}
