package com.springboot.api.service.impl;

import com.springboot.api.BaseApiService;
import com.springboot.api.ManagerService;
import com.springboot.entity.CertEntity;
import com.springboot.entity.ManagerEntity;
import com.springboot.entity.MemberCertDBEntity;
import com.springboot.manager.CertServiceManager;
import com.springboot.manager.CerttypeServiceManager;
import com.springboot.manager.ManagerServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ManagerServiceImpl extends BaseApiService implements ManagerService {
    @Autowired
    ManagerServiceManager managerServiceManager;


    @Autowired
    CertServiceManager certServiceManager;

    @Autowired
    CerttypeServiceManager certtypeServiceManager;

    @Override
    public Map<String, Object> login(@RequestBody ManagerEntity managerEntity) {
        return managerServiceManager.login(managerEntity);
    }

    @Override
    public Map<String, Object> getUser(String token) {
        return managerServiceManager.getUser(token);
    }

    @Override
    public List<CertEntity> queryAllCert() {
        return certServiceManager.queryAllCert();
    }

    @Override
    public List<Map<String, Object>> queryCertAccttype() {
        return certtypeServiceManager.queryCertAccttype();
    }
    @Override
    public int deleteAcctTypeCert(@RequestBody Map<String, Object> paramMap){
        return certtypeServiceManager.deleteAcctTypeCert(paramMap);
    }
    @Override
    public int insertAcctTypeCert(@RequestBody Map<String, Object> paramMap){
        return certtypeServiceManager.insertAcctTypeCert(paramMap);
    }

    @Override
    public List<CertEntity> queryCertByAccttype(@RequestParam(value="accttype") String accttype) {
        return certServiceManager.queryCertByAccttype(accttype);
    }

    @Override
    public void saveMemberCert(@RequestBody MemberCertDBEntity certimgs) {
        certServiceManager.saveMemberCert(certimgs);
    }

}
