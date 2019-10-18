package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken:"+accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("name:"+githubUser.getName());
        if (githubUser != null){
            //登录成功，写cookie 和 session
            User user = new User();
            String token = UUID.randomUUID().toString();//根据token来验证session，进行持久化
            user.setToken(token);//UUID
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));//强转为String
            user.setGmtCreate(System.currentTimeMillis());//当前时间，毫秒
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);//存到h2数据库

            response.addCookie(new Cookie("token",token));

            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
