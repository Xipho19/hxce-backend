package com.example.hxce.api.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.hxce.api.dao.StudentDao;
import com.example.hxce.api.pojo.StudentEntity;
import com.example.hxce.api.util.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void sendSmsCode(String phone) {
        // 判断手机号是否存在
        if (studentDao.existByPhone(new HashMap() {
            {
                put("phone", phone);
            }
        }) == false) {
            throw new RuntimeException("手机号不存在");
        }
        String smsCodeKey = "smsCode_student_" + phone;
        String refreshSmsCodeKey = "smsCode_student_refresh_" + phone;
        // 如果刷新缓存不存在，才可以生成验证码
        if (redisTemplate.hasKey(smsCodeKey) == false) {
            String smsCode = RandomUtil.randomNumbers(6);
            System.out.println("生成的验证码" + smsCode);
            redisTemplate.opsForValue().set(smsCodeKey, smsCode, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(refreshSmsCodeKey, smsCode, 1, TimeUnit.MINUTES);
        } else {
            throw new RuntimeException("现有验证码未过期");
        }
    }

    @Override
    public Map<String, Object> login(String phone, String smsCode) {
        String smsCodeKey = "smsCode_student_" + phone;
        String refreshSmsCodeKey = "refreshSmsCode_student_" + phone;
        if (redisTemplate.hasKey(smsCodeKey) == false) {
            throw new RuntimeException("验证码不存在");
        }
        // 缓存的验证码
        String cachedSmsCode = (String) redisTemplate.opsForValue().get(smsCodeKey);
        if (smsCode.equals(cachedSmsCode) == false) {
            throw new RuntimeException("验证码错误");
        }
        // 登录成功就删除缓存
        redisTemplate.delete(smsCodeKey);
        redisTemplate.delete(refreshSmsCodeKey);
        // 查询手机号对应的主键值
        Long studentId = studentDao.login(phone);
        if (studentId == null) {
            throw new RuntimeException("手机号不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);
        return map;
    }

    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList list = new ArrayList();
        long count = studentDao.searchCount(param);
        if (count > 0) {
            list = studentDao.searchByPage(param);
        }
        int page = (Integer) param.get("page");
        int size = (Integer) param.get("size");
        PageUtils pageUtils = new PageUtils(list, count, page, size);
        return pageUtils;
    }

    @Override
    public void insert(Map param) {
        System.out.println("添加学生的参数：" + param);
        StudentEntity entity = new StudentEntity();
        entity.setName((String) param.get("name"));
        entity.setGender((String) param.get("gender"));
        entity.setPhone((String) param.get("phone"));
        entity.setEmail((String) param.get("email"));

        // 处理 status，可能是 Integer 类型
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

        int rows = studentDao.insertStudent(entity);
        System.out.println("添加学生的数据库结果行数：" + rows);
        if (rows != 1) {
            throw new RuntimeException("添加学生失败");
        }
    }

    @Override
    public boolean existByPhone(Map param) {
        boolean bool = studentDao.existByPhone(param);
        return bool;
    }

    @Override
    public HashMap searchById(long id) {
        HashMap map = studentDao.searchById(id);
        return map;
    }

    @Override
    public void update(Map param) {
        System.out.println("更新学生的参数：" + param);
        int rows = studentDao.update(param);
        System.out.println("更新学生的数据库结果行数：" + rows);
        if (rows != 1) {
            throw new RuntimeException("数据更新失败");
        }
    }

    @Override
    public void deleteByIds(Long[] ids) {
        studentDao.deleteByIds(ids);
    }
}
