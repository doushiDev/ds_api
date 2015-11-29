package me.doushi.api.resource;

import io.swagger.annotations.*;
import me.doushi.api.service.UtilService;
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
 * 工具接口
 * Created by songlijun on 15/11/13.
 */
@Path("/util")
@Api(value = "/util")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class UtilServer {

    private static final Logger LOGGER = Logger.getLogger(VideoResource.class.getName());


    @Resource
    private UtilService utilService;

    @GET
    @Path("getQiNiuUpToken")
    @ApiOperation("获取上传token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getQiNiuUpToken(@Context HttpServletRequest httpServletRequest,
                                    @Context HttpServletResponse httpServletResponse) {

        LOGGER.info("获取上传token");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        try {
            response = utilService.getQiNiuUpToken(httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

    @POST
    @Path("JPushClient")
    @ApiOperation("极光推送")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response jPushClient(
            @ApiParam(value = "视频标题", name = "title", required = true) @FormParam("title") String title,
            @ApiParam(value = "视频id", name = "videoId", required = true) @FormParam("videoId") Integer videoId,
            @Context HttpServletRequest httpServletRequest,
                                    @Context HttpServletResponse httpServletResponse) {

        LOGGER.info("极光推送");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Response response;
        try {
            response = utilService.jPushClient(title, videoId, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }
}
