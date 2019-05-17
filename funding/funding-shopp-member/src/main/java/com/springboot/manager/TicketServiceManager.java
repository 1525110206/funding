package com.springboot.manager;

import com.springboot.entity.TicketEntity;

public interface TicketServiceManager {


    TicketEntity getTicketByMemberId(Long id);


    void saveTicket(TicketEntity ticket);


    void updatePstep(TicketEntity ticket);


    void updatePiidAndPstep(TicketEntity ticket);


    void updateStatus(TicketEntity ticket);

}
