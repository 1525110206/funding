package com.springboot.api;

import com.springboot.entity.CertEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/cert")
public interface CertService {


    @GetMapping("/queryAllCert")
    public List<CertEntity> queryAllCert();

}
