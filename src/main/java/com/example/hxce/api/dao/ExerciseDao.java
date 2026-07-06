package com.example.hxce.api.dao;
import com.example.hxce.api.pojo.ExerciseEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExerciseDao {
    public int insert(ExerciseEntity entity);
    public String searchByUuid(String uuid);

}
