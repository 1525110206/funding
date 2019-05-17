package com.springboot.controller.manager;

import com.springboot.entity.AjaxResult;
import com.springboot.entity.CertEntity;
import com.springboot.feign.CertFeign;
import com.springboot.feign.CerttypeFeign;
import com.springboot.feign.ManagerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/certtype")
public class CerttypeController {
    @Autowired
    CertFeign certFeign;

    @Autowired
    ManagerFeign managerFeign;

    @Autowired
    CerttypeFeign certtypeFeign;



    @RequestMapping("/index.html")
    public String index(Map<String, Object> map){
        List<CertEntity> queryAllCert = managerFeign.queryAllCert();
        map.put("allCert", queryAllCert);
        return "certtype/type";
    }


    @RequestMapping("/queryAllCertAccttype")
    @ResponseBody
    public Object queryAllCertAccttype(){
        AjaxResult result = new AjaxResult();
        try{

            List<Map<String, Object>> queryAllCertAccttype = managerFeign.queryCertAccttype();
            result.setData(queryAllCertAccttype);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/insertAllAcctTypeCert")
    public Object insertAcctTypeCert( Integer certid, String accttype ) {
        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);

            int count = managerFeign.insertAcctTypeCert(paramMap);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteAllAcctTypeCert")
    public Object deleteAcctTypeCert( Integer certid, String accttype ) {
        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);

            int count = managerFeign.deleteAcctTypeCert(paramMap);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }








}
