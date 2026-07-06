package com.example.hxce.api.service;

import com.example.hxce.api.dao.PermissionDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        return null;
    }
}


