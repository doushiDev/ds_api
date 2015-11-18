package me.doushi.api.service.impl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import me.doushi.api.domain.Video;
import me.doushi.api.service.UtilService;
import me.doushi.api.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

/**
 * Created by songlijun on 15/11/13.
 */
@Service
public class UtilServiceImpl implements UtilService {


//    @Value("${ACCESS_KEY}")
    String access_key = "qzX1uyw1OIarlNOdm0FuT8MoiZUGNWHu57_Cq0rr";
//    @Value("${SECRET_KEY}")
    String secret_key = "dT9QtJdnlgNWDsqOyhMGL8Msf6ZDxeunJuU3UmTr";

    Auth auth = Auth.create(access_key, secret_key);


    @Override
    public Response getQiNiuUpToken(HttpServletRequest httpServletRequest) {
        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<String> tokenResponseEntity = new ResponseEntity<>();
        tokenResponseEntity.setRequest(requestURI);
        String upToken = auth.uploadToken("itjh", null, 9600, new StringMap()
                .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
                .putNotEmpty("persistentPipeline", ""), true);
        tokenResponseEntity.setStatusCode(200);
        tokenResponseEntity.setMessage("token获取成功");
        tokenResponseEntity.setContent(upToken);
        response = Response.status(OK).entity(tokenResponseEntity).build();
        return response;
    }
}
