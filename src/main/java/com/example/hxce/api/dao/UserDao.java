package com.example.hxce.api.dao;

import com.example.hxce.api.pojo.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UserDao {
    public boolean existByPhone(Map param);
    public Long login(String phone);
    public HashMap searchUserInfoById(long id);
    public ArrayList<HashMap> searchByPage(Map param);
    public long searchCount(Map param);
    public int insert(UserEntity entity);
    public HashMap  searchById(long id);
    public int update(Map param);
    public int deleteByIds(Long[] ids);
}
