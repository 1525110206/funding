package com.springboot.manager;

import java.util.List;
import java.util.Map;

public interface CerttypeServiceManager {

    List<Map<String, Object>> queryCertAccttype();

    int deleteAcctTypeCert(Map<String, Object> paramMap);

    int insertAcctTypeCert(Map<String, Object> paramMap);


}
