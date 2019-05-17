package com.springboot.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("certtype")
public interface CerttypeFeign extends com.springboot.api.CerttypeService {
}
