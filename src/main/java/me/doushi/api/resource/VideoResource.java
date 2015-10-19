package me.doushi.api.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 视频资源 接口
 * Created by songlijun on 15/10/18.
 */
@Path("/video")
@Api(value = "/video", description = "the products API")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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

    public String getNewVideo(@ApiParam(value = "获取条数",required=true) @PathParam("count") Long count){

        return "";
    }

}
