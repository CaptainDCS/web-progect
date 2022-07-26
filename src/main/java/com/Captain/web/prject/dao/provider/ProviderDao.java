package com.Captain.web.prject.dao.provider;

import com.Captain.web.prject.domain.Provider;

import java.util.List;

public interface ProviderDao {
    //获取供应商列表
    public List<Provider> getProviderList(String proName, String proCode);
    //添加供货商
    public int addProvider(Provider provider);
    //删除供货商
    public int deleteProvider(int id);
    //通过ID获取供货商数据
    public Provider getProviderById(String id);
}
