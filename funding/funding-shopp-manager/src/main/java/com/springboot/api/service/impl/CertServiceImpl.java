package com.springboot.api.service.impl;

import com.springboot.api.CertService;
import com.springboot.entity.CertEntity;
import com.springboot.manager.CertServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CertServiceImpl implements CertService {

     @Autowired
     CertServiceManager certServiceManager;

//    @Override
//    public List<CertEntity> queryCertByAccttype(String accttype) {
//        return certServiceManager.queryCertByAccttype(accttype);
//    }

    @Override
    public List<CertEntity> queryAllCert() {
        return certServiceManager.queryAllCert();
    }

//    @Override
//    public List<Map<String, Object>> queryAllAccttypeCert() {
//        return certServiceManager.queryAllAccttypeCert();
//    }

//    @Override
//    public int insertAccttypeCert(Map<String, Object> map) {
//        return certServiceManager.insertAccttypeCert(map);
//    }
//
//    @Override
//    public int deleteAccttypeCert(Map<String, Object> map) {
//        return certServiceManager.deleteAccttypeCert(map);
//    }

//    @Override
//    public void saveMemberCert(List<MemberCertEntity> certimgs) {
//        certServiceManager.saveMemberCert(certimgs);
//    }

}
