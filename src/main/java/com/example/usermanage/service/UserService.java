package com.example.usermanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usermanage.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService extends IService<User> {

    public static final String USER_LOGIN_STATE = "user_login";

    /**
     * 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    Integer userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据用户名查询用户列表
     * @param username
     * @return 用户列表
     */
    List<User> searchUsers(String username);

    /**
     * 删除用户
     * @param id
     * @return 是否成功
     */
    Boolean deleteUser(Integer id);
    /**
     * 用户注销
     * @param request
     * @return 是否成功
     */
    int userLogout(HttpServletRequest request);
}