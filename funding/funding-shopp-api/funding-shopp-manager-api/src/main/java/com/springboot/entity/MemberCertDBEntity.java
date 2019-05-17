package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCertDBEntity {
    private Long id;

    private Long memberid;

    private Long certid;

    private String iconpath;
}
