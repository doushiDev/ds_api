package me.doushi.api.resource;

import io.swagger.annotations.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import me.doushi.api.domain.User;
import me.doushi.api.service.UserService;
import me.doushi.api.util.ApiError;
import me.doushi.api.util.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * 用户资源管理
 * Created by songlijun on 15/11/7.
 */
@Path("/user")
@Api(value = "/user")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class UserResource {


    @Resource
    private UserService userService;

    @POST
    @ApiOperation("注册用户")
    @Path("/registerUser")
    @ApiResponses({
            @ApiResponse(code = 201, message = "注册成功", response = ResponseEntity.class),
            @ApiResponse(code = 205, message = "手机号已注册,请直接登录", response = ApiError.class),
            @ApiResponse(code = 400, message = "服务器不理解请求的语法", response = ApiError.class),
            @ApiResponse(code = 500, message = "系统异常", response = ApiError.class)})
    public Response registerUser(@ApiParam(value = "用户基本信息", name = "user", required = true) User user,
                                 @Context HttpServletRequest httpServletRequest){
        Response response = null;
        try {
            response = userService.registerUser(user, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(INTERNAL_SERVER_ERROR).entity(new ApiError(10999, "系统异常", httpServletRequest.getRequestURI(), "系统错误,请联系逗视管理员")).build();
        }
        return response;
    }
}
