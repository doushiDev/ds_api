package me.doushi.api.mapping;

import me.doushi.api.domain.Video;

import java.util.List;

/**
 * 视频资源 数据<->DB
 * Created by songlijun on 15/10/23.
 */
public interface VideoMapper {
    /**
     * 获取最新的视频集合
     * @param count
     * @return
     */
    List<Video> getNewVideos(int count);
}
