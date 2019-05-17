package com.springboot.feign;

import com.springboot.api.TicketService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("ticket")
@Component
public interface TicketFeign extends TicketService {
}
