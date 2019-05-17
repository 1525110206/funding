package com.springboot.feign;


import com.springboot.api.UserService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("member")
@Component
public interface UserFeign extends UserService {
}
