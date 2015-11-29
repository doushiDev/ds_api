package me.doushi.api.service.impl;

import me.doushi.api.domain.Video;
import me.doushi.api.mapping.VideoMapper;
import me.doushi.api.service.VideoService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by songlijun on 15/10/23.
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger LOGGER = Logger.getLogger(VideoServiceImpl.class.getName());


    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<Video> getVideosByBanner(int userId) {
        return videoMapper.getVideosByBanner(userId);
    }

    @Override
    public Response getVideosByType(int videoId, int count, int type, int userId, HttpServletRequest httpServletRequest) {

        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<List<Video>> videoResponseEntity = new ResponseEntity<>();
        videoResponseEntity.setRequest(requestURI);

        Map<String, Object> parMap = new HashMap<>();
        parMap.put("videoId", videoId);
        parMap.put("count", count);
        parMap.put("type", type);
        parMap.put("userId", userId);

        //  判断参数是否合法
        if (count > 100) {//获取条数非法
            LOGGER.error(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "参数（count）值非法，希望得到（int[1~100]），实际得到（" + count + ")"));
            response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "参数（count）值非法，希望得到（int[1~100]），实际得到（" + count + ")")).build();
        } else {
            //请求数据
            List<Video> videos = videoMapper.getVideosByType(parMap);
            //判断数据是否为空
            if (!videos.isEmpty()) {
                //视频集合不为空
                LOGGER.info("视频集合为: " + videos.size() + "条");
                videoResponseEntity.setStatusCode(OK.getStatusCode());
                videoResponseEntity.setContent(videos);
                videoResponseEntity.setMessage("获取 " + videos.size() + "条数据");
                videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
                response = Response.status(OK).entity(videoResponseEntity).build();
            } else {
                videoResponseEntity.setStatusCode(NO_CONTENT.getStatusCode());
                videoResponseEntity.setContent(videos);
                videoResponseEntity.setMessage("没有数据");
                videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
                response = Response.status(NO_CONTENT).entity(videoResponseEntity).build();
            }
        }
        return response;
    }

    @Override
    public Response getVideosById(int videoId,int userId, HttpServletRequest httpServletRequest) {
        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<Video> videoResponseEntity = new ResponseEntity<>();
        videoResponseEntity.setRequest(requestURI);

        Map<String, Object> parMap = new HashMap<>();
        parMap.put("videoId",videoId);
        parMap.put("userId",userId);
        Video video = videoMapper.getVideosById(parMap);
        if (video != null){
            videoResponseEntity.setStatusCode(OK.getStatusCode());
            videoResponseEntity.setContent(video);
            videoResponseEntity.setMessage("获取数据成功");
             response = Response.status(OK).entity(videoResponseEntity).build();
        }else{
            videoResponseEntity.setStatusCode(NOT_FOUND.getStatusCode());
            videoResponseEntity.setContent(video);
            videoResponseEntity.setMessage("数据为空");
            response = Response.status(NOT_FOUND).entity(videoResponseEntity).build();
        }
        return response;
    }

    @Override
    public Response getVideoTaxis(int userId,HttpServletRequest httpServletRequest) {
        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<List<Video>> videoResponseEntity = new ResponseEntity<>();
        videoResponseEntity.setRequest(requestURI);

        //请求数据
        List<Video> videos = videoMapper.getVideoTaxis(userId);
        //判断数据是否为空
        if (!videos.isEmpty()) {
            //视频集合不为空
            LOGGER.info("视频集合为: " + videos.size() + "条");
            videoResponseEntity.setStatusCode(OK.getStatusCode());
            videoResponseEntity.setContent(videos);
            videoResponseEntity.setMessage("获取 " + videos.size() + "条数据");
            videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
            response = Response.status(OK).entity(videoResponseEntity).build();
        } else {
            videoResponseEntity.setStatusCode(NO_CONTENT.getStatusCode());
            videoResponseEntity.setContent(videos);
            videoResponseEntity.setMessage("没有数据");
            videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
            response = Response.status(NO_CONTENT).entity(videoResponseEntity).build();
        }
        return response;
    }
}
