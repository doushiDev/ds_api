package me.doushi.api.mapping;

import me.doushi.api.domain.Video;

import java.util.List;
import java.util.Map;

/**
 * 视频资源 数据<->DB
 * Created by songlijun on 15/10/23.
 */
public interface VideoMapper {

    /**
     * 获取发现 Banner
     * @return
     */
    List<Video> getVideosByBanner(int userId);

    /**
     * 根据类型获取视频
     * @param parMap
     * @return
     */
    List<Video> getVideosByType(Map<String, Object> parMap);

    /**
     * 根据videoId 获取video信息
     * @param parMap
     * @return
     */
    Video getVideosById(Map<String, Object> parMap);

    /**
     * 获取视频排行榜
     * @return
     */
    List<Video> getVideoTaxis(int userId);
}
