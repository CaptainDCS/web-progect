package com.Captain.web.prject.service.impl;

import com.Captain.web.prject.dao.role.RoleDao;
import com.Captain.web.prject.dao.role.impl.RoleDaoImpl;
import com.Captain.web.prject.domain.Role;
import com.Captain.web.prject.service.RoleService;
import org.junit.Test;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/25 19:50
 * description:
 */
public class RoleServiceImpl implements RoleService {
    //引入业务层
    private RoleDao roleDao = new RoleDaoImpl();

    @Override
    public List<Role> getRoleList() {
        return roleDao.getRoleList();
    }

    @Test
    public void test(){
//        RoleService roleService = new RoleServiceImpl();
//        List<Role> list = roleService.getRoleList();
//        for(Role role : list){
//            System.out.println(role.getId()+","+role.getRoleCode()+","+role.getRoleName());
//        }
    }
}
