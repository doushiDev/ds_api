package me.doushi.api.service.impl;

import me.doushi.api.domain.Video;
import me.doushi.api.mapping.VideoMapper;
import me.doushi.api.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by songlijun on 15/10/23.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<Video> getNewVideos(int count) {
        return videoMapper.getNewVideos(count);
    }

    @Override
    public List<Video> getVideosByHot(int videoId, int count) {
        Map<String,Object> parmap = new HashMap<String,Object>();
        parmap.put("videoId",videoId);
        parmap.put("count",count);
        return videoMapper.getVideosByHot(parmap);
    }
}
