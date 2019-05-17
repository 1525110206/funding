package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketEntity {

	private Long id;
	private Long memberid;
	private String piid;
	private String status;
	private String authcode;
	private String pstep;

}
