package com.example.usermanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanage.common.BaseResponse;
import com.example.usermanage.common.ErrorCode;
import com.example.usermanage.common.ResultUtils;
import com.example.usermanage.exception.BusinessException;
import com.example.usermanage.model.User;
import com.example.usermanage.model.request.UserLoginRequest;
import com.example.usermanage.model.request.UserRegisterRequest;
import com.example.usermanage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>用户控制器</p>
 */

@RestController @RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if(StringUtils.isBlank(userAccount)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账户不能为空");
        }
        if(StringUtils.isBlank(userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码不能为空");
        }
        if(StringUtils.isBlank(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "确认密码不能为空");
        }

        Integer result = userService.userRegister(userAccount,userPassword,checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){
        System.out.println("收到登录请求: " + userLoginRequest);
        if (userLoginRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        System.out.println("用户账号: " + userAccount + ", 密码: " + userPassword);
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount,userPassword,request);
        System.out.println("登录结果: " + user);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String  username,HttpServletRequest request){
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user = (User) obj;
        if(user == null||user.getUserRole() != 1){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        List<User>  userList = userService.searchUsers(username);

        List<User> safeUserList = userList.stream().map(
                safeuser ->{
                    safeuser.setPassword(null);
                    return safeuser;
                }
        ).collect(Collectors.toList());

        return ResultUtils.success(safeUserList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Integer id,HttpServletRequest  request){
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User user = (User) obj;
        if(user == null||user.getUserRole() != 1||id<=0){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Boolean result = userService.deleteUser(id);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object obj = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        User currentUser = (User) obj;
        if(currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User safeUser =currentUser;
        safeUser.setPassword(null);

        return ResultUtils.success(safeUser);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

}