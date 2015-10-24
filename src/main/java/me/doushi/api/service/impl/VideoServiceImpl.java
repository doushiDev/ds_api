package me.doushi.api.service.impl;

import me.doushi.api.domain.Video;
import me.doushi.api.mapping.VideoMapper;
import me.doushi.api.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
