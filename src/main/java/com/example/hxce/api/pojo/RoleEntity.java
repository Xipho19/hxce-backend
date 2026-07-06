package com.example.hxce.api.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleEntity {
    private Long id;
    private String roleName;
    private String permissions;
    private String remark;
    private Boolean systemic;
    private Byte status;
}
