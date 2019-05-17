package com.springboot.manager.impl;

import com.springboot.dao.CerttypeMapper;
import com.springboot.manager.CerttypeServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CerttypeServiceManagerImpl implements CerttypeServiceManager {
    @Autowired
    CerttypeMapper certtypeMapper;

    public CerttypeServiceManagerImpl(){

    }

    @Override
    public List<Map<String, Object>> queryCertAccttype() {
        return certtypeMapper.queryCertAccttype();
    }

    @Override
    public int deleteAcctTypeCert(Map<String, Object> paramMap) {
        return certtypeMapper.deleteAcctTypeCert(paramMap);
    }

    @Override
    public int insertAcctTypeCert(Map<String, Object> paramMap) {
        return certtypeMapper.insertAcctTypeCert(paramMap);
    }
}
