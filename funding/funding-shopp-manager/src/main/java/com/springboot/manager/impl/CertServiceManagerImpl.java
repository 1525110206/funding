package com.springboot.manager.impl;

import com.springboot.dao.CertMapper;
import com.springboot.entity.CertEntity;
import com.springboot.entity.MemberCertDBEntity;
import com.springboot.manager.CertServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertServiceManagerImpl implements CertServiceManager {
    @Autowired
    CertMapper certMapper;


    public CertServiceManagerImpl() {
    }


//    @Override
//    public List<CertEntity> queryCertByAccttype(String accttype) {
//        return certMapper.queryCertByAccttype(accttype);
//    }

    @Override
    public List<CertEntity> queryAllCert() {
        return certMapper.queryAllCert();
    }

    @Override
    public List<CertEntity> queryCertByAccttype(String accttype) {
        return certMapper.queryCertByAccttype(accttype);
    }

    @Override
    public void saveMemberCert(MemberCertDBEntity certimgs) {
//        for (MemberCertEntity memberCert : certimgs) {
            certMapper.insertMemberCert(certimgs);
//        }
    }


//    @Override
//    public List<Map<String, Object>> queryAllAccttypeCert(String accttype) {
//        return certMapper.queryCertByAccttype(accttype);
//    }

//    @Override
//    public int insertAccttypeCert(Map<String, Object> map) {
//        return certMapper.insertAccttypeCert(map);
//    }
//
//    @Override
//    public int deleteAccttypeCert(Map<String, Object> map) {
//        return certMapper.deleteAccttypeCert(map);
//    }

//    @Override
//    public void saveMemberCert(List<MemberCertEntity> certimgs) {
//        certMapper.
//    }
}
