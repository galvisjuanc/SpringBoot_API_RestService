package com.springboot_restservice_api.springboot_api.entity;

import org.springframework.stereotype.Component;

@Component
public class AddResponse {

    private String msg;
    private String id;

    public String getMsg() {return msg;}

    public void setMsg(String msg) {this.msg = msg;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
    
}
