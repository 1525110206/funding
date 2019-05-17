package com.springboot.dao;


import com.springboot.entity.TicketEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TicketMapper {

    int deleteByPrimaryKey(Integer id);

    TicketEntity selectByPrimaryKey(Integer id);

    List<TicketEntity> selectAll();

    TicketEntity getTicketByMemberId(Long memberid);

    void saveTicket(TicketEntity ticket);

    void updatePstep(TicketEntity ticket);

    void updatePiidAndPstep(TicketEntity ticket);

    void updateStatus(TicketEntity ticket);


}
