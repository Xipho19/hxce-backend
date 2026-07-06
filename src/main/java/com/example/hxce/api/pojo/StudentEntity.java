package com.example.hxce.api.pojo;

import lombok.Data;

@Data
public class StudentEntity {
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private Byte status;
}
