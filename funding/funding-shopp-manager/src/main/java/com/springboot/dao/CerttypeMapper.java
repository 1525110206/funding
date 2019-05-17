package com.springboot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CerttypeMapper {

    public List<Map<String, Object>> queryCertAccttype();

    int deleteAcctTypeCert(Map<String, Object> paramMap);

    int insertAcctTypeCert(Map<String, Object> paramMap);

}
