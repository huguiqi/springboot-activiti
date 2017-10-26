package com.example.demo.common.restClient.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.demo.common.restClient.domain.BaseModel;
import com.example.demo.common.restClient.domain.BaseResponse;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by guiqi on 2017/8/10.
 */

@Component
public class HttpRestClient {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;



    public Object post(String url, Object data) {
        return sendFor(url, data);
    }



    public Object get(String url, Object data) {
        try {
            HttpHeaders headers = buildHttpHeaders();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<Object> entity = null;
            if (data != null){
                entity = new HttpEntity<Object>(headers);
                Map<String,String> map = (Map<String, String>) objectToMap(data);
                for (Map.Entry<String , String > setEntity : map.entrySet()){
                    builder.queryParam(setEntity.getKey(),setEntity.getValue());
                }
            }

            ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
            System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.getBody());
            if (response.getStatusCode().value() == HttpStatus.OK.value()){
                return  JSONObject.parseObject(response.getBody()).toJavaObject(BaseResponse.class);
            }
        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            logger.error(eek.getMessage(),eek);
        }
        return null;
    }

    public Object put(String url, Object data) {
        return sendFor(url, data);
    }

    private Object sendFor(String url, Object data) {
        try {
            HttpHeaders headers = buildHttpHeaders();
            HttpEntity<Object> entity = null;
            if (data != null){
                HashMap<String, String> paramMap = new HashMap<String,String>();
                paramMap.put("data", JSON.toJSONString(data));
                entity = new HttpEntity<Object>(paramMap, headers);
            }
            ResponseEntity<BaseResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, BaseResponse.class);
            System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.getBody());
            if (response.getStatusCode().value() == HttpStatus.OK.value()){
                return response.getBody();
            }
        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            logger.error(eek.getMessage(),eek);
        }
        return null;
    }

    public Object patch(String url, Object data) {
        return post(url+"?_method=patch",data);
    }


    private HttpHeaders buildHttpHeaders() {
//        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
//        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        String tokenId = (String) session.getAttribute("登陆后的token");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        //todo 暂时性写死User-Agent，后续真实环境干掉
        headers.set("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        headers.set("clientId", "服务器认证的key");
        headers.set("clientTime", String.valueOf(System.currentTimeMillis()));
        headers.set("token", tokenId);
        return headers;
    }

    public Map<?, ?> objectToMap(Object obj)  throws Exception {
        if(obj == null)
            return null;
        return new BeanMap(obj);
    }

    /**
     *
     * @param data,BaseResponse的data值
     * @param typeRef，传入你要传换的接收类型
     * @return
     */
     public Object toResultBean(Object data, TypeReference typeRef){
        if (data.getClass() == JSONArray.class){
            String  jsonString = JSONObject.toJSONString(data);
            Object returnValue = JSON.parseObject(jsonString,typeRef);
            System.out.println("List EQ===========");
            return returnValue;
        }else if (data.getClass() == JSONObject.class){
            BaseModel model = ((JSONObject) data).toJavaObject(BaseModel.class);
            JSONArray jsonArray =(JSONArray) model.getObject();
            String jsonString = jsonArray.toJSONString();
            request.setAttribute("totalCount", model.getTotal());
            Object returnValue =  JSON.parseObject(jsonString,typeRef);
            System.out.println("JSONObject EQ===========");
            return  returnValue;
        }

        return null;
    }



}
