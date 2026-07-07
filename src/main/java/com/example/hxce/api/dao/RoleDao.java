package com.example.hxce.api.dao;

import com.example.hxce.api.pojo.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface RoleDao {
    public ArrayList<String> searchUserRoles(long userId);
    public ArrayList<HashMap> searchAllRole();
    public ArrayList<HashMap> searchByPage(Map param);
    public long searchCount(Map param);
    public HashMap searchById(long id);
    public ArrayList<HashMap> searchRoleUsers(long roleId);
    public boolean checkRoleNameConflict(Map param);
    public int insertRole(RoleEntity entity);
    public int update(Map param);
    public int deleteByIds(Long[] ids);
}
