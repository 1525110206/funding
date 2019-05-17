package com.springboot.api.service.impl;

import com.springboot.api.TicketService;
import com.springboot.entity.TicketEntity;
import com.springboot.manager.TicketServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketServiceManager ticketServiceManager;

    @Override
    public TicketEntity getTicketByMemberId(Long id) {
        return ticketServiceManager.getTicketByMemberId(id);
    }

    @Override
    public void saveTicket(TicketEntity ticket) {
        ticketServiceManager.saveTicket(ticket);
    }

    @Override
    public void updatePstep(TicketEntity ticket) {
        ticketServiceManager.updatePstep(ticket);
    }

    @Override
    public void updatePiidAndPstep(TicketEntity ticket) {
        ticketServiceManager.updatePiidAndPstep(ticket);
    }
}
