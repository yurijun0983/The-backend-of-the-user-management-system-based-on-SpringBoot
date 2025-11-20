package com.example.usermanage.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usermanage.mapper.UserMapper;
import com.example.usermanage.model.User;
import com.example.usermanage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Integer userRegister(String userAccount, String userPassword, String checkPassword) {
        //输入为 空直接不注册
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }

        if(userAccount.length()<4){
            return -1;
        }

        if(userPassword.length()<8||checkPassword.length()<8){
            return -1;
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userAccount);
        long cnt = this.count(queryWrapper);
        if(cnt>0){
            return -1;
        }
        String validAccount = "^[a-zA-Z0-9]{4,}$";
        Matcher matcher = Pattern.compile(validAccount).matcher(userAccount);
        if(!matcher.find()){
            return -1;
        }
        if(!userPassword.equals(checkPassword)){
            return -1;
        }
        String encryptedPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        

        User user = new User();
        user.setUsername(userAccount);
        user.setPassword(encryptedPassword);
        boolean saveResult = this.save(user);
        if (saveResult) {
            return user.getId();
        } else {
            return -1;
        }
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest  request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }

        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }
        String encryptedPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userAccount);
        queryWrapper.eq("password",encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user==null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setPassword(user.getPassword());
        safeUser.setGender(user.getGender());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setEmail(user.getEmail());
        safeUser.setIsDelete(user.getIsDelete());
        safeUser.setCreatedTime(user.getCreatedTime());
        safeUser.setUpdateTime(user.getUpdateTime());
        safeUser.setIsVaild(user.getIsVaild());
        safeUser.setUserRole(user.getUserRole());


        request.getSession().setAttribute(USER_LOGIN_STATE,safeUser);
        return safeUser;
    }

    @Override
    public List<User> searchUsers(String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean deleteUser(Integer id){
        if (id<=0){
            return false;
        }
        return this.removeById(id);
    }
}