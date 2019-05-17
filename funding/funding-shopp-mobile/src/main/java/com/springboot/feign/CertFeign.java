package com.springboot.feign;

import com.springboot.api.CertService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("cert")
@Component
public interface CertFeign extends CertService {
}
