package com.Captain.web.prject.domain;

import java.util.Date;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/23 15:05
 * description:收货地址表
 * id	bigint	主键ID
 * contact	varchar	联系人姓名
 * addressDesc	varchar	收货地址明细
 * postCode	varchar	邮编
 * tel	varchar	联系人电话
 * createdBy	bigint	创建者
 * creationDate	datetime	创建时间
 * modifyBy	bigint	修改者
 * modifyDate	datetime	修改时间
 * userId	bigint	用户ID
 */
public class Address {
    private int id;
    private String contact;
    private String addressDese;
    private String postCode;
    private String tel;
    private int createBy;
    private Date creationDate;
    private int modifyBy;
    private Date modifyDate;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressDese() {
        return addressDese;
    }

    public void setAddressDese(String addressDese) {
        this.addressDese = addressDese;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
