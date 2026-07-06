package com.example.hxce.api.service;

import com.example.hxce.api.util.PageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface StudentService {
    public void sendSmsCode(String phone);

    public Map<String, Object> login(String phone, String smsCode);

    public PageUtils searchByPage(Map param);

    public void insert(Map param);

    public boolean existByPhone(Map param);

    public HashMap searchById(long id);

    public void update(Map param);

    public void deleteByIds(Long[] ids);
}
