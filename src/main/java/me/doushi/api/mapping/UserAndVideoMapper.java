package me.doushi.api.mapping;

import me.doushi.api.domain.UserAndVideo;
import me.doushi.api.domain.Video;

import java.util.List;
import java.util.Map;

/**
 * Created by songlijun on 15/11/11.
 */
public interface UserAndVideoMapper {

    /**
     * 取消收藏
     * @param parMap 用户id,视频id
     * @return
     */
    int updateByUserIdAndVideoId(Map<String,Object> parMap);

    /**
     * 添加收藏
     * @param userAndVideo
     * @return
     */
    int insert(UserAndVideo userAndVideo);


    /**
     * 获取用户收藏记录
     * @param parMap 用户id 页码,条数
     * @return
     */
    List<Video> selectUserAndVideoListByUserId(Map<String,Object> parMap);

    /**
     * 获取收藏记录
     * @param parMap
     * @return 收藏状态
     */
    UserAndVideo getUserAndVideoByUserIdAndVideoId(Map<String, Object> parMap);
}
