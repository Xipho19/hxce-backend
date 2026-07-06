package com.example.hxce.api.util;

import lombok.Data;
import java.util.List;
@Data
public class PageUtils {
    private long totalCount;
    private int pageSize;
    private int totalPage;
    private int pageIndex;
    private List list;

    public PageUtils(List list,long totalCount,int pageIndex,int pageSize){
        this.list=list;
        this.totalCount=totalCount;
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
        totalPage=(int)Math.ceil((double)totalCount/pageSize);
    }
}
