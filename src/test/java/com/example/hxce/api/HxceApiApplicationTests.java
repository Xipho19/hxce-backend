package com.example.hxce.api;

import com.example.hxce.api.dao.StudentDao;
import com.example.hxce.api.pojo.StudentEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional // 建议加在这里，对类内所有测试方法生效
class HxceApiApplicationTests {
    @Resource
    private StudentDao studentDao;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddNewStudent() {
        StudentEntity entity = new StudentEntity();
        entity.setName("李小璐");
        entity.setGender("女");
        entity.setEmail("1959474407@qq.com");
        entity.setPhone("5514556555");
        entity.setStatus((byte)1);
        studentDao.insertStudent(entity);
    }
}