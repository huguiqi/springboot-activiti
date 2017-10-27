package com.example.demo.common.restClient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by guiqi on 2017/8/10.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String code;
    private String message;
    private Object data;
    private String serverIp;


    private static String RESPONSE_OK = "200000";

    public boolean isOK(){
        return RESPONSE_OK.equals(code);
    }

}
