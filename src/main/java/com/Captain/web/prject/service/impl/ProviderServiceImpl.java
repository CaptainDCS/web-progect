package com.Captain.web.prject.service.impl;

import com.Captain.web.prject.dao.provider.ProviderDao;
import com.Captain.web.prject.dao.provider.impl.ProviderDaoImpl;
import com.Captain.web.prject.domain.Provider;
import com.Captain.web.prject.service.ProviderService;
import com.Captain.web.prject.utils.DataSourceUtil;

import java.util.List;

/**
 * projectName:web-progect
 * author:dcs
 * time:2021/10/30 13:21
 * description:
 */
public class ProviderServiceImpl implements ProviderService {
    ProviderDao providerDao = new ProviderDaoImpl();

    @Override
    public List<Provider> getProviderList(String proName, String proCode) {
        return providerDao.getProviderList(proName, proCode);
    }

    @Override
    public boolean addProvider(Provider provider) {
        boolean flag = false;
        try {
            DataSourceUtil.begin();
            int updatRows = providerDao.addProvider(provider);
            DataSourceUtil.commit();
            if(updatRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
                System.out.println("add failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                DataSourceUtil.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public boolean deleteProvider(int id) {
        boolean flag = false;
        if(providerDao.deleteProvider(id) > 0) flag = true;
        return flag;
    }

    @Override
    public Provider getProviderById(String id) {
        return providerDao.getProviderById(id);
    }
}
