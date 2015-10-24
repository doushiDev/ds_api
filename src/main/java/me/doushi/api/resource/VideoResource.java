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
     * 获取最新video
     *
     * @param count 获取条数
     * @return
     */
    @GET
    @Path("getNewVideos/{count}")
    @ApiOperation("获取最新video")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "请求成功,但数据为空", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "参数非法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response getNewVideos(@ApiParam(value = "获取条数", required = true) @PathParam("count") int count,
                                 @Context HttpServletRequest httpServletRequest) throws Exception {
        Response response = null;
        ResponseEntity<List<Video>> videoResponseEntity = new ResponseEntity<List<Video>>();
        try {
            //  判断参数是否合法
            if (count > 100) {//获取条数非法
                LOGGER.error(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "参数（count）值非法，希望得到（int[1~100]），实际得到（" + count + ")"));
                response = Response.status(BAD_REQUEST).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "参数（count）值非法，希望得到（int[1~100]），实际得到（" + count + ")")).build();
            } else {
                //请求数据
                List<Video> videos = videoService.getNewVideos(count);
                //判断数据是否为空
                if (!videos.isEmpty()){
                    //视频集合不为空
                    LOGGER.info("视频集合为: " + videos.size() + "条");
                    videoResponseEntity.setStatusCode(OK.getStatusCode());
                    videoResponseEntity.setContent(videos);
                    videoResponseEntity.setMessage("获取 "+ videos.size() + "条数据");
                    videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
                    response = Response.status(OK).entity(videoResponseEntity).build();
                }else{
                    videoResponseEntity.setStatusCode(NO_CONTENT.getStatusCode());
                    videoResponseEntity.setContent(videos);
                    videoResponseEntity.setMessage("没有数据");
                    videoResponseEntity.setRequest(httpServletRequest.getRequestURI());
                    response = Response.status(NO_CONTENT).entity(videoResponseEntity).build();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error("系统异常");
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10178, "系统错误", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }

}
