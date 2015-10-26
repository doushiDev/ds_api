package me.doushi.api.service;

import me.doushi.api.domain.Video;

import java.util.List;

/**
 * 视频资源Service
 * Created by songlijun on 15/10/23.
 */
public interface VideoService {


    /**
     * 获取最新的视频集合
     * @param count
     * @return
     */
    List<Video> getNewVideos(int count);

    /**
     * 获取最热video.
     *  videoId后count条数据
     * @param videoId
     * @param count
     * @return
     */
    List<Video> getVideosByHot(int videoId, int count);
}
