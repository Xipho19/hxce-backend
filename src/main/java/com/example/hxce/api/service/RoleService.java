package com.example.hxce.api.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleService {
    public ArrayList<HashMap> searchAllRole();
    public ArrayList<String> searchUserRoles(long userId);
}
