package com.springboot.manager.impl;

import com.springboot.dao.TicketMapper;
import com.springboot.entity.TicketEntity;
import com.springboot.manager.TicketServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceManagerImpl implements TicketServiceManager {

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public TicketEntity getTicketByMemberId(Long id) {
        return ticketMapper.getTicketByMemberId(id);
    }

    @Override
    public void saveTicket(TicketEntity ticket) {
        ticketMapper.saveTicket(ticket);
    }

    @Override
    public void updatePstep(TicketEntity ticket) {
        ticketMapper.updatePstep(ticket);
    }

    @Override
    public void updatePiidAndPstep(TicketEntity ticket) {
        ticketMapper.updatePiidAndPstep(ticket);
    }

    @Override
    public void updateStatus(TicketEntity ticket) {
        ticketMapper.updateStatus(ticket);
    }
}
