package com.springboot.api;

import com.springboot.entity.CertEntity;
import com.springboot.entity.ManagerEntity;
import com.springboot.entity.MemberCertDBEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/manager")
public interface ManagerService {

    //登录成功后，生成对应的token，作为key，将用户userID作为value存放在redis中，返回token给客户端
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody ManagerEntity managerEntity);



    //使用token查找用户信息
    @PostMapping("/getUser")
    public Map<String, Object> getUser(@RequestParam(value="token") String token);


    @GetMapping("/queryAllCert")
    public List<CertEntity> queryAllCert();


    @GetMapping("queryCertAccttype")
    public List<Map<String, Object>> queryCertAccttype();

    @PostMapping("deleteAcctTypeCert")
    public int deleteAcctTypeCert(@RequestBody Map<String, Object> paramMap);

    @PostMapping("insertAcctTypeCert")
    public int insertAcctTypeCert(@RequestBody Map<String, Object> paramMap);

    @PostMapping("queryCertByAccttype")
    public List<CertEntity> queryCertByAccttype(@RequestParam(value="accttype") String accttype);

    @PostMapping("saveMemberCert")
    public void saveMemberCert(@RequestBody MemberCertDBEntity certimgs);

}
