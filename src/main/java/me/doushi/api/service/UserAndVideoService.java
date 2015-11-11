package me.doushi.api.service;

import me.doushi.api.domain.UserAndVideo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Created by songlijun on 15/11/11.
 */
public interface UserAndVideoService {

    /**
     * 用户收藏电影
     * @param userAndVideo
     * @param httpServletRequest
     * @return
     */
    Response addUserAndVideo(UserAndVideo userAndVideo, HttpServletRequest httpServletRequest)  throws Exception;

    /**
     * 取消收藏
     * @param userId 用户id
     * @param movieId 电影id
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    Response deleteByUserIdAndVideoId(Integer userId, Integer movieId, HttpServletRequest httpServletRequest)  throws Exception;

    /**
     * 获取收藏视频
     * @param userId 用户id
     * @param httpServletRequest
     * @return
     */
    Response getVideosByUserId(Integer userId, Integer pageNum,Integer count,HttpServletRequest httpServletRequest);
}
