package com.example.community.model;

import lombok.Data;

/**
 * 登录用户
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;//账户ID
    private String token;//令牌
    private Long gmtCreate;//创建时间
    private Long gmtModified;//修改时间
}
