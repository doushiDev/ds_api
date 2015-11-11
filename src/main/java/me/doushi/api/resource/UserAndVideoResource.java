package me.doushi.api.resource;

import io.swagger.annotations.*;
import me.doushi.api.domain.UserAndVideo;
import me.doushi.api.service.UserAndVideoService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * 用户与视频资源管理
 * Created by songlijun on 15/11/11.
 */
@Path("/userAndVideo")
@Api(value = "/userAndVideo")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class UserAndVideoResource {
    private static final Logger LOGGER = Logger.getLogger(UserAndVideoResource.class.getName());

    @Resource
    private UserAndVideoService userAndVideoService;




    @POST
    @ApiOperation("收藏视频")
    @Path("/addUserFavoriteVideo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "收藏成功", response = ResponseEntity.class),
            @ApiResponse(code = 2001, message = "此电影已经收藏", response = ApiError.class),
            @ApiResponse(code = 400, message = "服务器不理解请求的语法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response addUserFavoriteVideo(@ApiParam(value = "收藏信息", name = "userFavorite", required = true) UserAndVideo userFavorite,
                                         @Context HttpServletRequest httpServletRequest,
                                         @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");

        LOGGER.info("用户id:" + userFavorite.getUserId() + "收藏视频id: " + userFavorite.getVideoId());
        Response response ;
        try {
            response = userAndVideoService.addUserAndVideo(userFavorite, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10999, "系统异常", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

    @DELETE
    @ApiOperation("取消收藏视频")
    @Path("/deleteByUserIdAndVideoId/{userId}/{videoId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "取消成功", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "服务器不理解请求的语法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response deleteByUserIdAndVideoId(@ApiParam(value = "用户id", name = "userId", required = true) @PathParam("userId") Integer userId,
                                             @ApiParam(value = "电影id", name = "videoId", required = true) @PathParam("videoId") Integer videoId,
                                             @Context HttpServletRequest httpServletRequest,
                                             @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        LOGGER.info("用户id:" + userId + "取消收藏视频id: " + videoId);

        Response response ;
        try {
            response = userAndVideoService.deleteByUserIdAndVideoId(userId, videoId, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10999, "系统异常", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }


    @GET
    @ApiOperation("获取收藏视频")
    @Path("/getVideosByUserId/{userId}/{pageNum}/{count}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "收藏记录不存在", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "服务器不理解请求的语法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getVideosByUserId(@ApiParam(value = "用户id", name = "userId", required = true) @PathParam("userId") Integer userId,
                                      @ApiParam(value = "页码", name = "pageNum", required = true) @PathParam("pageNum") Integer pageNum,
                                      @ApiParam(value = "条数", name = "count", required = true) @PathParam("count") Integer count,
                                             @Context HttpServletRequest httpServletRequest,
                                      @Context HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        LOGGER.info("用户id:" + userId + "获取收藏视频列表");

        Response response ;
        try {
            response = userAndVideoService.getVideosByUserId(userId,pageNum, count, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10999, "系统异常", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }
}
