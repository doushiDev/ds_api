package me.doushi.api.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songlijun on 15/10/17.
 */

//@Path("/test")
//@Api(value = "/test")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//@Component
public class TestRestAPI {

    @GET
    @Path("/{para}")
     public String test(@PathParam("para") String para) {
        return "{ss:'ddd',res:'ddd',int:11,boole:true}";
    }

    public static String countTime(String begin,String end) throws ParseException {
        int hour = 0;
        int minute = 0;
        long total_minute = 0;
        StringBuffer sb = new StringBuffer();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin_date = df.parse(begin);
            Date end_date = df.parse(end);

            total_minute = (end_date.getTime() - begin_date.getTime())/(1000*60);

            hour = (int) total_minute/60;
            minute = (int) total_minute%60;

        } catch (ParseException e) {
            System.out.println("传入的时间格式不符合规定");
        }

        if (hour != 0 && hour < 2){
            sb.append(hour).append("小时").append("前");
        }else if(hour > 3){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
            sb.append(simpleDateFormat.format(simpleDateFormat.parse(begin)));
        }else{
            if (minute == 0 &&  minute < 5){
                sb.append("刚刚");
            }else{
                sb.append(minute).append("分钟").append("前");
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception
    {
     String result=new TestRestAPI().countTime("2012-11-30 11:11:16", "2012-11-30 16:18:18");
        System.out.println(result);
    }
}
