package com.example.hxce.api.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface RoleDao {
    public ArrayList<String> searchUserRoles(long userId);
    public ArrayList<HashMap> searchAllRole();

}
