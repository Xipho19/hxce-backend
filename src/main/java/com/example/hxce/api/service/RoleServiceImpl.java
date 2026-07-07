package com.example.hxce.api.service;

import cn.hutool.core.bean.BeanUtil;
import com.example.hxce.api.dao.RoleDao;
import com.example.hxce.api.pojo.RoleEntity;
import com.example.hxce.api.util.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public ArrayList<HashMap> searchAllRole() {
        ArrayList<HashMap> list = roleDao.searchAllRole();
        return list;
    }

    @Override
    public ArrayList<String> searchUserRoles(long userId) {
        ArrayList<String> roles = roleDao.searchUserRoles(userId);
        return roles;
    }

    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList list = new ArrayList();
        long count = roleDao.searchCount(param);
        if (count > 0) {
            list = roleDao.searchByPage(param);
        }
        int page = (Integer) param.get("page");
        int size = (Integer) param.get("size");
        PageUtils pageUtils = new PageUtils(list, count, page, size);
        return pageUtils;
    }

    @Override
    public void insert(Map param) {
        System.out.println("RoleServiceImpl.insert 接收的参数：" + param);
        RoleEntity entity = new RoleEntity();
        entity.setRoleName((String) param.get("roleName"));
        entity.setRemark((String) param.get("remark"));
        
        Object statusObj = param.get("status");
        if (statusObj != null) {
            if (statusObj instanceof Integer) {
                entity.setStatus(((Integer) statusObj).byteValue());
            } else if (statusObj instanceof Byte) {
                entity.setStatus((Byte) statusObj);
            } else {
                entity.setStatus(Byte.parseByte(statusObj.toString()));
            }
        }
        
        entity.setPermissions((String) param.get("permissions"));
        entity.setSystemic(true); // 设置 systemic 默认值 true
        System.out.println("准备插入数据库的RoleEntity：" + entity);
        int rows = roleDao.insertRole(entity);
        System.out.println("插入数据库结果行数：" + rows);
        if (rows != 1) {
            throw new RuntimeException("添加角色失败");
        }
    }

    @Override
    public boolean checkRoleNameConflict(Map param) {
        boolean bool = roleDao.checkRoleNameConflict(param);
        return bool;
    }

    @Override
    public HashMap searchById(long id) {
        HashMap map = roleDao.searchById(id);
        return map;
    }

    @Override
    public ArrayList<HashMap> searchRoleUsers(long roleId) {
        ArrayList<HashMap> list = roleDao.searchRoleUsers(roleId);
        return list;
    }

    @Override
    public void update(Map param) {
        System.out.println("RoleServiceImpl.update 接收的参数：" + param);
        int rows = roleDao.update(param);
        System.out.println("更新数据库结果行数：" + rows);
        if (rows != 1) {
            throw new RuntimeException("数据更新失败");
        }
    }

    @Override
    public void deleteByIds(Long[] ids) {
        roleDao.deleteByIds(ids);
    }
}
