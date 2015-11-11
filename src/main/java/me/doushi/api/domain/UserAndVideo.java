package me.doushi.api.domain;

import java.io.Serializable;

/**
 * 用户与视频关系实体
 * Created by songlijun on 15/11/11.
 */
public class UserAndVideo implements Serializable {

    private static final long serialVersionUID = -5604124306173982592L;
    private Integer id;
    private Integer userId;
    private Integer videoId;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
