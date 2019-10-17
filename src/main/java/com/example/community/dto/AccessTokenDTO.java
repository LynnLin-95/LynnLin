package com.example.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;//您从GitHub收到的GitHub App的客户端ID
    private String client_secret;//您从GitHub收到的GitHub App的客户密码
    private String code;//您收到的作为对步骤1的响应的代码
    private String redirect_uri;//授权后将用户发送到应用程序中的URL
    private String state;//您在步骤1中提供的无法猜测的随机字符串
}
