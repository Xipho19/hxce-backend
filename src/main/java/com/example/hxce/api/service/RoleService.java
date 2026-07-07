package com.example.hxce.api.service;

import com.example.hxce.api.util.PageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface RoleService {
    public ArrayList<HashMap> searchAllRole();
    public ArrayList<String> searchUserRoles(long userId);
    public PageUtils searchByPage(Map param);
    public void insert(Map param);
    public boolean checkRoleNameConflict(Map param);
    public HashMap searchById(long id);
    public ArrayList<HashMap> searchRoleUsers(long roleId);
    public void update(Map param);
    public void deleteByIds(Long[] ids);
}
