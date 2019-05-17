package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberCertEntity {

    private Long id;

    private Long memberid;

    private Long certid;

    private String iconpath;

    private MultipartFile fileImg;

}
