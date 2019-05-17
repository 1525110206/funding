package com.springboot.api;


import com.springboot.entity.TicketEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ticket")
public interface TicketService {
    @PostMapping("/getTicketByMemberId")
    TicketEntity getTicketByMemberId(Long id);


    @PostMapping("/saveTicket")
    void saveTicket(TicketEntity ticket);


    @PostMapping("/updatePstep")
    void updatePstep(TicketEntity ticket);


    @PostMapping("/updatePiidAndPstep")
    void updatePiidAndPstep(TicketEntity ticket);

}
