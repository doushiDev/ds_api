package me.doushi.api.mapping;

import me.doushi.api.domain.User;

import java.util.Map;

/**
 * Created by songlijun on 15/11/7.
 */

public interface UserMapper {

    /**
     * 注册用户
     * @param user 用户信息
     * @return 用户添加状态
     */
    int registerUser(User user);

    /**
     * 检测用户手机号是否被注册过
     * @param par
     * @return
     */
    User checkUserByPhoneAndPlatformId(Map<String, Object> par);
}
