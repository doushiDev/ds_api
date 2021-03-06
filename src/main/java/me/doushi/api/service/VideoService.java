package me.doushi.api.service;

import me.doushi.api.domain.Video;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 视频资源Service
 * Created by songlijun on 15/10/23.
 */
public interface VideoService {




    Response getVideosByType(int videoId, int count, int type, int userId, HttpServletRequest httpServletRequest);

    /**
     * 获取发现 Banner
     * @return
     */
    List<Video> getVideosByBanner(int userId);

    /**
     * 根据videoId 获取video信息
     * @param videoId
     * @param httpServletRequest
     * @return
     */
    Response getVideosById(int videoId,int userId, HttpServletRequest httpServletRequest);

    /**
     * 获取排行榜
     * @param httpServletRequest
     * @return
     */
    Response getVideoTaxis(int userId,HttpServletRequest httpServletRequest);
}
