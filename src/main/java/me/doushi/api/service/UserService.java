package me.doushi.api.service;

import me.doushi.api.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Created by songlijun on 15/11/7.
 */
public interface UserService {
    /**
     * 注册用户
     * @param user 用户信息
     * @param httpServletRequest
     * @return
     */
    Response registerUser(User user, HttpServletRequest httpServletRequest) throws Exception;
}
