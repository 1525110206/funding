package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Data {

    private List<MemberCertEntity> certimgs = new ArrayList<>();

    private List<Integer> del_ids;

}
