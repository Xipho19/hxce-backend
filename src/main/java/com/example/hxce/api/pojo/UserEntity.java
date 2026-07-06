package com.example.hxce.api.pojo;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String name;
    private String photo;
    private String gender;
    private String phone;
    private String roles;
    private Byte status;
}
