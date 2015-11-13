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




    Response getVideosByType(int videoId,int count,int type,HttpServletRequest httpServletRequest);

    /**
     * 获取发现 Banner
     * @return
     */
    List<Video> getVideosByBanner();
}
