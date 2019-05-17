package com.springboot.manager;

import com.springboot.entity.CertEntity;
import com.springboot.entity.MemberCertDBEntity;

import java.util.List;

public interface CertServiceManager {

//    public List<CertEntity> queryCertByAccttype(String accttype);

    public List<CertEntity> queryAllCert();

    public List<CertEntity> queryCertByAccttype(String accttype);


    public void saveMemberCert(MemberCertDBEntity certimgs);


//    public List<Map<String, Object>> queryAllAccttypeCert();

//    public int insertAccttypeCert(Map<String, Object> map);
//
//    public int deleteAccttypeCert(Map<String, Object> map);

//    public void saveMemberCert(List<MemberCertEntity> certimgs);

}
