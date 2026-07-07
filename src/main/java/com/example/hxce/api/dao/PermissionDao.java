package com.example.hxce.api.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface PermissionDao {
    public ArrayList<String> searchUserPermissions(long userId);
    public ArrayList<HashMap> searchAllPermission();

}
