package me.doushi.api.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import javafx.scene.control.Alert;
import me.doushi.api.domain.Video;
import me.doushi.api.service.UtilService;
import me.doushi.api.util.ResponseEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;

/**
 * Created by songlijun on 15/11/13.
 */
@Service
public class UtilServiceImpl implements UtilService {

    private static final Logger LOGGER = Logger.getLogger(UtilServiceImpl.class.getName());

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


    String TITLE = "Test from API example";
    static String ALERT = "Test from API Example - alert";
    static String MSG_CONTENT = "Test from API Example - msgContent";
    String REGISTRATION_ID = "0900e8d85ef";
    String TAG = "tag_api";


    @Override
    public Response jPushClient(String title,Integer videoId, HttpServletRequest httpServletRequest) {
        Response response;
        String requestURI = httpServletRequest.getRequestURI();
        ResponseEntity<String> jPushResponseEntity = new ResponseEntity<>();
        jPushResponseEntity.setRequest(requestURI);

        String  masterSecret ="4a48b30423ce1fdd16c27602";
        String  appKey ="b0cb764764c796e00f6a643c";


        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);


        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(title,videoId+"");
//        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(videoId + "");


        try {
            PushResult result = jpushClient.sendPush(payload);
            LOGGER.info("Got result - " + result);
            jPushResponseEntity.setStatusCode(200);
            jPushResponseEntity.setMessage("推送成功");
            jPushResponseEntity.setContent("视频id "+ videoId+" 推送成功");
            response = Response.status(OK).entity(jPushResponseEntity).build();

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOGGER.error("Connection error, should retry later", e);
            jPushResponseEntity.setStatusCode(400);
            jPushResponseEntity.setMessage("推送失败");
            jPushResponseEntity.setContent("视频id "+ videoId+" 推送失败");
            response = Response.status(BAD_REQUEST).entity(jPushResponseEntity).build();

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOGGER.error("Should review the error, and fix the request", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());

            jPushResponseEntity.setStatusCode(e.getErrorCode());
            jPushResponseEntity.setMessage(e.getErrorMessage());
            jPushResponseEntity.setContent("视频id "+ videoId + " 推送失败");
            response = Response.status(BAD_REQUEST).entity(jPushResponseEntity).build();

        }

        return response;
    }


    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String title,String videoId) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(title)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("videoId",videoId)
                                .build())
                        .build())
                .setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_all_all_alert() {

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(null)
                .setNotification(Notification.alert("alert"))
                .build();

    }
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String videoId) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())

                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("alert")
                                .setBadge(1)
                                .addExtra("videoId", videoId)
                                .build())
                        .build())
                .setMessage(Message.content("测试影片"))

                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }
}
