package com.example.hxce.api.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.hxce.api.dao.PermissionDao;
import com.example.hxce.api.dao.RoleDao;
import com.example.hxce.api.dao.UserDao;
import com.example.hxce.api.pojo.UserEntity;
import com.example.hxce.api.util.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    public void sendSmsCode(String phone){
        boolean bool = userDao.existByPhone(new HashMap(){{
            put("phone",phone);
        }});
        if(bool==false){
            throw new RuntimeException("手机号不存在");
        }
    String smsCodeKey="smsCode_user_"+phone;
    String refreshSmsCodeKey="smsCode_user_refresh_"+phone;
    if(redisTemplate.hasKey(smsCodeKey)==false &&
            redisTemplate.hasKey(refreshSmsCodeKey)==false){
        String  smsCode = RandomUtil.randomNumbers(6);
        System.out.println("生成的验证码:"+smsCode);
        redisTemplate.opsForValue().set(smsCodeKey,smsCode,5, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(refreshSmsCodeKey,smsCode,1,TimeUnit.MINUTES);

        }
    else if(redisTemplate.hasKey(refreshSmsCodeKey)==true){
        throw new RuntimeException("现有验证码未过期");
        }
        else{
            String  smsCode = RandomUtil.randomNumbers(6);
            System.out.println("生成的验证码:"+smsCode);
            redisTemplate.opsForValue().set(smsCodeKey,smsCode,5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(refreshSmsCodeKey,smsCode,1,TimeUnit.MINUTES);
        }
    }

    @Override
    public Map<String, Object> login(String phone, String smsCode) {
        //查询验证码缓存是否存在
        String smsCodeKey="smsCode_user_"+phone;
        String refreshSmsCodeKey="smsCode_user_refresh_"+phone;
        if(redisTemplate.hasKey(smsCodeKey)==false){
            throw new RuntimeException("验证码不存在");
        }
        //判断验证码是否一致
        String cachedSmsCode = (String) redisTemplate.opsForValue().get(smsCodeKey);
        if(cachedSmsCode.equals(smsCode)==false){
            throw new RuntimeException("验证码错误");
        }
        //删除验证码缓存和刷新缓存
        else{
            redisTemplate.delete(smsCodeKey);
            redisTemplate.delete(refreshSmsCodeKey);
        }
        //调用数据库查询手机号对应ID
        Long userId=userDao.login(phone);

        //查询用户权限和角色
        ArrayList<String> roles=roleDao.searchUserRoles(userId);
        ArrayList<String> permissions=permissionDao.searchUserPermissions(userId);
        //把相关数据封存在Map对象返回
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("roles",roles);
        map.put("permissions",permissions);
        return map;
    }

    @Override
    public HashMap searchUserInfoById(long id) {
        HashMap map=userDao.searchUserInfoById(id);
        return map;
    }

    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList list=new ArrayList();
        long count =userDao.searchCount(param);
        if(count>0){
            list=userDao.searchByPage(param);
        }
        int page=(Integer)param.get("page");
        int size=(Integer)param.get("size");
        PageUtils pageUtils=new PageUtils(list,count,page,size);
        return pageUtils;

    }

    @Override
    public void insert(Map param) {
        UserEntity entity=BeanUtil.toBean(param, UserEntity.class);
        int rows=userDao.insert(entity);
        if(rows!=1){
            throw new RuntimeException("添加用户失败");
        }
    }

    @Override
    public boolean existByPhone(Map param) {
        boolean bool=userDao.existByPhone(param);
        return bool;
    }

    @Override
    public HashMap searchById(long id) {
        HashMap map = userDao.searchById(id);
        String temp=(String)map.get("roles");
        JSONArray jsonArray= JSONUtil.parseArray(temp);
        map.put("roles",jsonArray);
        return map;
    }

    @Override
    public void update(Map param) {
        Long[] roles=(Long[])param.get("roles");
        String jsonStr=JSONUtil.toJsonStr(roles);
        param.put("roles",jsonStr);
        int rows=userDao.update(param);
        if(rows!=1){
            throw new RuntimeException("数据更新失败");
        }

    }

    @Override
    public void deleteByIds(Long[] ids) {
        userDao.deleteByIds(ids);
    }
}
