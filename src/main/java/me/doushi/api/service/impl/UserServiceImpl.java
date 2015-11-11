package me.doushi.api.service.impl;

import me.doushi.api.domain.User;
import me.doushi.api.mapping.UserMapper;
import me.doushi.api.service.UserService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;

/**
 * Created by songlijun on 15/11/7.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    StringBuffer message = new StringBuffer();

    @Resource
    private UserMapper userMapper;

    @Transactional
    @Override
    public Response registerUser(User user, HttpServletRequest httpServletRequest) throws Exception {
        Response response;

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<User> userResponseEntity = new ResponseEntity<User>();
        userResponseEntity.setRequest(requestURI);
        //检测用户是否被注册
        Map<String, Object> par = new HashMap<>();
        par.put("phone", user.getPhone());
        par.put("platformId", user.getPlatformId());
        User checkUser = userMapper.checkUserByPhoneAndPlatformId(par);
        if (null == checkUser) {
            int addUserCount = userMapper.registerUser(user);
            if (addUserCount > 0) {
                LOGGER.info("注册用户成功");
                userResponseEntity.setStatusCode(201);
                userResponseEntity.setContent(user);
                user.setPassword(null);
                userResponseEntity.setMessage("注册用户成功");
                response = Response.status(CREATED).entity(userResponseEntity).build();
            } else {
                response = Response.status(ACCEPTED).entity(new ApiError(10110, "注册失败", httpServletRequest.getRequestURI(), "注册失败,请稍后重试")).build();
            }
        } else {
            LOGGER.info("用户已存在");
            userResponseEntity.setStatusCode(201);

            userResponseEntity.setContent(checkUser);
            user.setPassword(null);
            userResponseEntity.setMessage("用户已存在");
            response = Response.status(CREATED).entity(userResponseEntity).build();
        }
        return response;
    }

    @Override
    public Response loginUser(String phone, String password, HttpServletRequest httpServletRequest) {

        Response response;

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<User> userResponseEntity = new ResponseEntity<User>();
        userResponseEntity.setRequest(requestURI);
        //检测用户是否被注册
        Map<String, Object> par = new HashMap<>();
        par.put("phone", phone);
        User checkUser = userMapper.checkUserByPhoneAndPlatformId(par);
        if (null != checkUser) {
            par.put("password", password);
            User loginUser = userMapper.checkUserByPhoneAndPlatformId(par);
            if (loginUser != null) {
                LOGGER.info("登录成功");
                userResponseEntity.setStatusCode(200);
                userResponseEntity.setContent(loginUser);
                loginUser.setPassword(null);
                userResponseEntity.setMessage("登录成功");
                response = Response.status(CREATED).entity(userResponseEntity).build();
            } else {
                response = Response.status(ACCEPTED).entity(new ApiError(1012, "密码错误", httpServletRequest.getRequestURI(), "登录失败,密码错误")).build();
            }
        } else {
            LOGGER.info("此手机号不存在");
            response = Response.status(ACCEPTED).entity(new ApiError(1013, "手机号未注册", httpServletRequest.getRequestURI(), "手机号未注册")).build();
        }
        return response;
    }
}
