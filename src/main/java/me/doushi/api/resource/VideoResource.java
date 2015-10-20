package me.doushi.api.resource;

import io.swagger.annotations.*;
import me.doushi.api.util.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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


    /**
     * 获取最新video
     * @param count 获取条数
     * @return
     */
    @GET
    @Path("getNewVideo/{count}")
    @ApiOperation("获取最新video")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功", response = String.class),
            @ApiResponse(code = 400, message = "参数传入有误",response = ApiError.class)})
    public Response getNewVideo(@ApiParam(value = "获取条数",required=true) @PathParam("count") Long count,
                              @Context HttpServletRequest httpServletRequest) throws Exception{
        Response response = null;

        ResponseEntity<String> e = new ResponseEntity<String>(HttpStatus.OK);

        if (count > 100){//获取条数非法

//            throw new ApiException("获取条数非法",10018,"","","");
            response = Response.status(BAD_REQUEST).entity(new ApiError(10178,"系统错误",httpServletRequest.getRequestURI(),"参数（count）值非法，希望得到（int[1~100]），实际得到（" + count + ")")).build();

        }


        return response;
    }

}
