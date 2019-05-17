package com.springboot.feign;

import com.springboot.api.ManagerService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("manager")
public interface ManagerFeign extends ManagerService {
}
