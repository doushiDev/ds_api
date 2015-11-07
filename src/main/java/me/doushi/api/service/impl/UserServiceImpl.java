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

    Response response = null;
    StringBuffer message = new StringBuffer();

    @Resource
    private UserMapper userMapper;

    @Transactional
    @Override
    public Response registerUser(User user, HttpServletRequest httpServletRequest) throws Exception {

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<User> videoResponseEntity = new ResponseEntity<User>();
        videoResponseEntity.setRequest(requestURI);
        //检测手机号是否被注册过
        Map<String,Object> par = new HashMap<>();
        par.put("phone",user.getPhone());
        int checkUserPhoneCount = userMapper.checkUserByPhone(par);
        if (checkUserPhoneCount == 0){ //手机号未注册
            int addUserCount = userMapper.registerUser(user);
            if (addUserCount > 0) {
                LOGGER.info("注册用户成功");
                videoResponseEntity.setStatusCode(201);
                videoResponseEntity.setContent(user);
                user.setPassword(null);
                videoResponseEntity.setMessage("注册用户成功");
                response = Response.status(CREATED).entity(videoResponseEntity).build();
            }else{
                response = Response.status(ACCEPTED).entity(new ApiError(10110, "注册失败", httpServletRequest.getRequestURI(), "注册失败,请稍后重试")).build();
            }
        }else{
            LOGGER.info("手机号已被注册");
            response = Response.status(ACCEPTED).entity(new ApiError(10112, "注册失败", httpServletRequest.getRequestURI(), "手机号已被注册,请直接登录")).build();
        }
        return response;
    }
}
