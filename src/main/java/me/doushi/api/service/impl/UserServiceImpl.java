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

import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by songlijun on 15/11/7.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());


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
                user.setPassword(null);
                userResponseEntity.setContent(user);

                userResponseEntity.setMessage("注册用户成功");
                response = Response.status(CREATED).entity(userResponseEntity).build();
            } else {
                response = Response.status(BAD_REQUEST).entity(new ApiError(BAD_REQUEST.getStatusCode(), "注册失败", httpServletRequest.getRequestURI(), "注册失败,请稍后重试")).build();
            }
        } else {

            if (!user.getPlatformId().equals("9")) {
                LOGGER.info("用户已存在");
                userResponseEntity.setStatusCode(200);
                checkUser.setPassword(null);
                userResponseEntity.setContent(checkUser);
                userResponseEntity.setMessage("用户已存在");
//                response = Response.status(OK).entity(userResponseEntity).build();

                response = Response.status(BAD_REQUEST).entity(new ApiError(BAD_REQUEST.getStatusCode(), "用户已存在", httpServletRequest.getRequestURI(), "用户已存在")).build();


            }else{
                LOGGER.info("手机号已被注册");
                userResponseEntity.setStatusCode(UNAUTHORIZED.getStatusCode());
                userResponseEntity.setContent(null);
                userResponseEntity.setMessage("手机号已被注册");
//                response = Response.status(UNAUTHORIZED).entity(userResponseEntity).build();
                response = Response.status(BAD_REQUEST).entity(new ApiError(BAD_REQUEST.getStatusCode(), "手机号已被注册", httpServletRequest.getRequestURI(), "手机号已被注册")).build();

            }

        }
        return response;
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public Response updateUser(User user, HttpServletRequest httpServletRequest) {
        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<Integer> userResponseEntity = new ResponseEntity();
        userResponseEntity.setRequest(requestURI);

        //更新用户信息
        int upCount = userMapper.updateUser(user);
        if (upCount > 0){
            LOGGER.info("用户更新成功");
            userResponseEntity.setStatusCode(200);
            userResponseEntity.setContent(1);
             userResponseEntity.setMessage("更新成功");
            response = Response.status(OK).entity(userResponseEntity).build();

        }else{
            LOGGER.info("用户更新失败");
            userResponseEntity.setStatusCode(200);
            userResponseEntity.setContent(0);
            userResponseEntity.setMessage("更新失败,请稍后重试");
            response = Response.status(OK).entity(userResponseEntity).build();
        }
        return  response;
    }
}
