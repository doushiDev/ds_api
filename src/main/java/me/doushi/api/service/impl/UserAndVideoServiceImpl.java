package me.doushi.api.service.impl;

import me.doushi.api.domain.UserAndVideo;
import me.doushi.api.domain.Video;
import me.doushi.api.mapping.UserAndVideoMapper;
import me.doushi.api.service.UserAndVideoService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by songlijun on 15/11/11.
 */
@Service
@Transactional
public class UserAndVideoServiceImpl implements UserAndVideoService {

    private static final Logger LOGGER = Logger.getLogger(UserAndVideoServiceImpl.class.getName());

    Response response = null;

    @Resource
    private UserAndVideoMapper userAndVideoMapper;

    @Override
    public Response addUserAndVideo(UserAndVideo userAndVideo, HttpServletRequest httpServletRequest) throws Exception {
        StringBuffer message = new StringBuffer();

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<UserAndVideo> userAndVideoResponseEntity = new ResponseEntity<UserAndVideo>();
        userAndVideoResponseEntity.setRequest(requestURI);

        Map<String, Object> parMap = new HashMap<>();
        parMap.put("userId", userAndVideo.getUserId());
        parMap.put("videoId", userAndVideo.getVideoId());

        //检查参数
        for (Field f : userAndVideo.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if ("id".equals(f.getName()))
                continue;
            if ((f.get(userAndVideo) == null) || ("".equals(f.get(userAndVideo)))) {
                message.append(f.getName() + ",");
            }
        }
        if (!"".equals(message.toString())) {
            message.append("不能为空");
            LOGGER.error("传入信息非法");
            response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "传入信息非法  " + message, httpServletRequest.getRequestURI(), "传入信息非法")).build();
        } else {
            int count = 0;
            //判断用户是否已经收藏
            UserAndVideo CheckUserAndVideo = userAndVideoMapper.getUserAndVideoByUserIdAndVideoId(parMap);
            if (null != CheckUserAndVideo) { //存在收藏记录
                switch (CheckUserAndVideo.getStatus()) {
                    case 0: //已取消收藏
                        //修改收藏状态
                        parMap.put("status", 1);
                        CheckUserAndVideo.setStatus(1);
                        count = userAndVideoMapper.updateByUserIdAndVideoId(parMap);
                        userAndVideoResponseEntity.setContent(CheckUserAndVideo);
                        break;
                    case 1: //用户已经收藏
                        response = Response.status(BAD_REQUEST).entity(new ApiError(2001, "此电影已经收藏", httpServletRequest.getRequestURI(), "此视频已经收藏,无需在收藏")).build();
                        return response;
                    default:
                        break;
                }
            }else{
                //用户没有收藏,进行添加
                userAndVideo.setStatus(1); //状态
                count = userAndVideoMapper.insert(userAndVideo);
                userAndVideoResponseEntity.setContent(userAndVideo);
            }
            if (count > 0) {
                //收藏成功
                userAndVideoResponseEntity.setStatusCode(200);
                userAndVideoResponseEntity.setMessage("收藏成功");
                response = Response.status(CREATED).entity(userAndVideoResponseEntity).build();
            } else {
                message.append("收藏失败");
                LOGGER.error("收藏失败");
                response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "" + message, httpServletRequest.getRequestURI(), "收藏失败,请稍后重试")).build();
            }
        }

        return response;
    }

    @Override
    public Response deleteByUserIdAndVideoId(Integer userId,Integer videoId, HttpServletRequest httpServletRequest) throws Exception {

        StringBuffer message = new StringBuffer();

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<String> userAndVideoResponseEntity = new ResponseEntity<String>();
        userAndVideoResponseEntity.setRequest(requestURI);

        Map<String, Object> parMap = new HashMap<>();

        //检查参数
        if ((null == userId) || 0 == userId) {
            message.append("userId" + ",");
        }
        if ((null == videoId) || 0 == videoId) {
            message.append("videoId" + ",");
        }
        if (!"".equals(message.toString())) {
            message.append("不能为空");
            LOGGER.error("传入信息非法");
            response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "传入信息非法  " + message, httpServletRequest.getRequestURI(), "传入信息非法")).build();
        } else {
            parMap.put("userId", userId);
            parMap.put("videoId", videoId);
            parMap.put("status", 0);
            int count = userAndVideoMapper.updateByUserIdAndVideoId(parMap);
            if (count > 0) {
                //取消成功
                userAndVideoResponseEntity.setStatusCode(200);
                userAndVideoResponseEntity.setContent("取消成功");
                userAndVideoResponseEntity.setMessage("取消成功");
                response = Response.status(OK).entity(userAndVideoResponseEntity).build();
            } else {
                message.append("取消失败");
                LOGGER.error("取消失败");
                response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "" + message, httpServletRequest.getRequestURI(), "收藏失败,请稍后重试")).build();
            }
        }
        return response;
    }


    @Override
    public Response getVideosByUserId(Integer userId,Integer pageNum, Integer count, HttpServletRequest httpServletRequest) {

        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<List<Video>> userAndVideoResponseEntity = new ResponseEntity<List<Video>>();
        userAndVideoResponseEntity.setRequest(requestURI);

        Map<String,Object> paramMap = new HashMap<String,Object>();
        if (pageNum != 0){
            pageNum *= count;
        }
        paramMap.put("pageNum", pageNum);
        paramMap.put("userId", userId);
        paramMap.put("count", count);
        List<Video> userFavorites =  userAndVideoMapper.selectUserAndVideoListByUserId(paramMap);
        //判断数据是否为空
        if (!userFavorites.isEmpty()){
            //视频集合不为空
            LOGGER.info("收藏视频集合为: " + userFavorites.size() + "条");
            userAndVideoResponseEntity.setStatusCode(OK.getStatusCode());
            userAndVideoResponseEntity.setContent(userFavorites);
            userAndVideoResponseEntity.setMessage("获取 "+ userFavorites.size() + "条数据");
            userAndVideoResponseEntity.setRequest(httpServletRequest.getRequestURI());
            response = Response.status(OK).entity(userAndVideoResponseEntity).build();
        }else{
            LOGGER.info("收藏视频不存在");
            userAndVideoResponseEntity.setStatusCode(NO_CONTENT.getStatusCode());
            userAndVideoResponseEntity.setContent(userFavorites);
            userAndVideoResponseEntity.setMessage("没有数据");
            userAndVideoResponseEntity.setRequest(httpServletRequest.getRequestURI());
            response = Response.status(OK).entity(userAndVideoResponseEntity).build();
        }
        return response;
    }
}
