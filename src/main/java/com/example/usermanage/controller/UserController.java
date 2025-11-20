package com.example.usermanage.controller;

import com.example.usermanage.model.User;
import com.example.usermanage.model.request.UserLoginRequest;
import com.example.usermanage.model.request.UserRegisterRequest;
import com.example.usermanage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * <p>用户控制器</p>
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public Integer userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest==null){
            return -1;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }

        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){

        if (userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount,userPassword,request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String  username,HttpServletRequest request){
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user = (User) obj;
        if(user == null||user.getUserRole() != 1){
            return new ArrayList<>();
        }

        List<User>  userList = userService.searchUsers(username);

        return userList.stream().map(
                safeuser ->{
                    safeuser.setPassword(null);
                    return safeuser;
                }
        ).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public Boolean deleteUser(@RequestBody Integer id,HttpServletRequest  request){
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user = (User) obj;
        if(user == null||user.getUserRole() != 1){
            return null;
        }
       return userService.deleteUser(id);
    }
}
