package com.example.hxce.api.service;

import com.example.hxce.api.dao.PermissionDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        try {
            ArrayList<HashMap> permissions = permissionDao.searchAllPermission();
            // 按模块分组
            ArrayList<HashMap> result = new ArrayList<>();
            HashMap<String, List<HashMap>> moduleMap = new HashMap<>();
            
            for (HashMap permission : permissions) {
                String moduleName = (String) permission.get("moduleName");
                if (moduleName == null) {
                    moduleName = "其他";
                }
                if (!moduleMap.containsKey(moduleName)) {
                    moduleMap.put(moduleName, new ArrayList<>());
                }
                moduleMap.get(moduleName).add(permission);
            }
            
            for (String moduleName : moduleMap.keySet()) {
                HashMap module = new HashMap<>();
                module.put("moduleName", moduleName);
                module.put("permissions", moduleMap.get(moduleName));
                module.put("checked", false);
                result.add(module);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


