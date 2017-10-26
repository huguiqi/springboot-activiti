package com.example.demo.common.reqVO;

import lombok.Data;

/**
 * Created by guiqi on 2017/8/8.
 */
@Data
public class ReqBaseParam {

    private int currentPage,pageSize;

    //判断是否需要分页
    public boolean isPageRow() {
        return currentPage != 0 && pageSize != 0;
    }
}
