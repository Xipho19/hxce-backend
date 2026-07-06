package com.example.hxce.api.dao;

import com.example.hxce.api.pojo.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface StudentDao {
    public int insertStudent(StudentEntity entity);

    public boolean existByPhone(Map param);

    public Long login(String phone);

    public ArrayList<HashMap> searchByPage(Map param);

    public long searchCount(Map param);

    public HashMap searchById(long id);

    public int update(Map param);

    public int deleteByIds(Long[] ids);
}
