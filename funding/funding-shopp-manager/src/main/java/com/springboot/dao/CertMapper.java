package com.springboot.dao;

import com.springboot.entity.CertEntity;
import com.springboot.entity.MemberCertDBEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CertMapper {


	public List<CertEntity> queryCertByAccttype(String accttype);

	public List<CertEntity> queryAllCert();

	public void insertMemberCert(MemberCertDBEntity memberCert);



//	List<Map<String, Object>> queryAllAccttypeCert();
//
//	int insertAccttypeCert(Map<String, Object> map);
//
//	int deleteAccttypeCert(Map<String, Object> map);
//
//	void insertMemberCert(MemberCertEntity memberCert);
}
