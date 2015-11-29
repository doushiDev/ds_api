package me.doushi.api.resource;

import io.swagger.annotations.*;
import me.doushi.api.domain.Video;
import me.doushi.api.service.VideoService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

/**
 * 视频资源 接口
 * Created by songlijun on 15/10/18.
 */
@Path("/video")
@Api(value = "/video", description = "the products API")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class VideoResource {

    private static final Logger LOGGER = Logger.getLogger(VideoResource.class.getName());


    @Resource
    private VideoService videoService;

    /**
     * 获取 video
     *
     * @param count 获取条数
     *
     * @return
     */
    @GET
    @Path("getVideosByType/{videoId}/{count}/{type}/{userId}")
    @ApiOperation("根据类型获取视频资源")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getVideosByType(@ApiParam(value = "视频id</br> 0:获取最新数据", required = true) @PathParam("videoId") int videoId,
                                    @ApiParam(value = "获取条数", required = true) @PathParam("count") int count,
                                    @ApiParam(value = "类型 </br>0:推荐1:精华 2:热门", required = true) @PathParam("type") int type,
                                    @ApiParam(value = "用户id", required = true) @PathParam("userId") int userId,
                                    @Context HttpServletRequest httpServletRequest,
                                    @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        try {
            response = videoService.getVideosByType(videoId, count, type,userId, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

    /**
     * 根据videoId获取 video信息
     *
     * @return
     */
    @GET
    @Path("getVideosById/{videoId}/{userId}")
    @ApiOperation("根据视频id获取视频资源")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getVideosById(
            @ApiParam(value = "视频id", required = true) @PathParam("videoId") int videoId,
            @ApiParam(value = "用户id", required = true) @PathParam("userId") int userId,

                                    @Context HttpServletRequest httpServletRequest,
                                    @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        try {
            response = videoService.getVideosById(videoId, userId,httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

    @GET
    @Path("getVideosByBanner/{userId}")
    @ApiOperation("获取发现 banner视频")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getVideosByBanner(
            @ApiParam(value = "用户id", required = true) @PathParam("userId") int userId,

            @Context HttpServletRequest httpServletRequest,
                                      @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        ResponseEntity<List<Video>> videoResponseEntity = new ResponseEntity<List<Video>>();
        try {
            //请求数据
            List<Video> videos = videoService.getVideosByBanner(userId);
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

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

    @GET
    @Path("getVideoTaxis/{userId}")
    @ApiOperation("获取视频排行榜")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getVideoTaxis(
            @ApiParam(value = "用户id", required = true) @PathParam("userId") int userId,

            @Context HttpServletRequest httpServletRequest,
                                      @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        try {
            response = videoService.getVideoTaxis(userId,httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }


}
