package com.springboot.api.service.impl;

import com.springboot.api.CerttypeService;
import com.springboot.manager.CerttypeServiceManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CerttypeServiceImpl implements CerttypeService {

    @Autowired
    CerttypeServiceManager certtypeServiceManager;

    @Override
    public List<Map<String, Object>> queryCertAccttype() {
        return certtypeServiceManager.queryCertAccttype();
    }

    public int deleteAcctTypeCert(Map<String, Object> paramMap){
        return certtypeServiceManager.deleteAcctTypeCert(paramMap);
    }

    public int insertAcctTypeCert(Map<String, Object> paramMap){
        return certtypeServiceManager.insertAcctTypeCert(paramMap);
    }

}
