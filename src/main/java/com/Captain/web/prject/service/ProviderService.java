package com.Captain.web.prject.service;

import com.Captain.web.prject.domain.Provider;

import java.util.List;

public interface ProviderService {
    //获取供应商列表
    public List<Provider> getProviderList(String proName, String proCode);
    //添加供应商
    public boolean addProvider(Provider provider);
    //删除供应商
    public boolean deleteProvider(int id);
    //通过ID获取供货商信息
    public Provider getProviderById(String id);

}
