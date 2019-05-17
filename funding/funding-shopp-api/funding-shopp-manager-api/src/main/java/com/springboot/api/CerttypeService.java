package com.springboot.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/certtype")
public interface CerttypeService {

    @GetMapping("queryCertAccttype")
    public List<Map<String, Object>> queryCertAccttype();

}
