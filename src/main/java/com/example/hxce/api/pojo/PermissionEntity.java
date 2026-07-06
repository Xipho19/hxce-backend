package com.example.hxce.api.pojo;

import lombok.Data;

@Data
public class PermissionEntity {
    private Long id;
    private String premissionCode;
    private String premissionName;
    private Long moduleId;
    private Long actionId;
}
