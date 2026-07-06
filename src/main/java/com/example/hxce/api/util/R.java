package com.example.hxce.api.util;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class R {
    private int code;
    private String error;
    private Map<String,Object> data;

    public static R ok(){
        R r=new R();
        r.code=200;
        r.error=null;
        r.data=null;
        return r;
    }

    public static R ok(Map<String,Object> data){
        R r=new R();
        r.code=200;
        r.error=null;
        r.data=data;
        return r;
    }

    public static R error(String error){
        R r=new R();
        r.code=500;
        r.error=error;
        r.data=null;
        return r;
    }

    public static R error(int code,String error){
        R r=new R();
        r.code=code;
        r.error=error;
        r.data=null;
        return r;
    }
}
