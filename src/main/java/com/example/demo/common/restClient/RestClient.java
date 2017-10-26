package com.example.demo.common.restClient;

import com.alibaba.fastjson.TypeReference;

/**
 * Created by guiqi on 2017/8/10.
 */

public interface RestClient{

    public Object post(String url, Object data);

    public Object get(String url, Object data);

    public Object put(String url, Object data);

    public Object patch(String url, Object data);

    /**
     *
     * @param data,BaseResponse的data值
     * @param typeRef，传入你要传换的接收类型
     * @return
     */
     public Object toResultBean(Object data, TypeReference typeRef);

}
