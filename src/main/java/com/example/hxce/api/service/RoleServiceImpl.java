package com.example.hxce.api.service;

import com.example.hxce.api.dao.RoleDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Override
    public ArrayList<HashMap> searchAllRole() {
        ArrayList<HashMap> list=roleDao.searchAllRole();
        return list;
    }

    @Override
    public ArrayList<String> searchUserRoles(long userId) {
        ArrayList<String> roles=roleDao.searchUserRoles(userId);
        return roles;
    }


}
