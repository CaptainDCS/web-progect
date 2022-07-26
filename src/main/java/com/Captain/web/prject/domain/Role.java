package com.Captain.web.prject.domain;

import java.util.Date;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/23 15:17
 * description:
 * id	bigint	主键ID
 * roleCode	varchar	角色编码
 * roleName	varchar	角色名称
 * createdBy	bigint	创建者
 * creationDate	datetime	创建时间
 * modifyBy	bigint	修改者
 * modifyDate	datetime	修改时间
 */
public class Role {
    private int id;
    private String roleCode;
    private String roleName;
    private int createdBy;
    private Date creationDate;
    private int modifyBy;
    private Date modifyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
