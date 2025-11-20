package com.example.usermanage.service.impl;

import com.example.usermanage.model.User;
import com.example.usermanage.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void test() {
        User user=new User();
        user.setUsername("张三");
        user.setPassword("<PASSWORD>");
        user.setEmail("<EMAIL>");
        user.setGender(1);
        user.setAvatarUrl("https://www.baidu.com");
        user.setUserStatus(1);
        user.setIsDelete(0);
        user.setIsVaild(1);
        user.setCreatedTime(new java.util.Date());
        user.setUpdateTime(new java.util.Date());

       boolean result=userService.save(user);
       Assertions.assertTrue(result);
    }

    @Test
    public void userRegisterTest(){
        String userAccount="zhangsan";
        String userPassword="<PASSWORD>";
        String checkPassword="<PASSWORD>";
        Integer result=userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertTrue(result>0);

    }
}