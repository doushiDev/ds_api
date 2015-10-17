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

/**
 * Created by songlijun on 15/10/17.
 */

@Path("/test")
@Api(value = "/test")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Component
public class TestRestAPI {

    @GET
    @Path("/{para}")
     public String test(@PathParam("para") String para) {
        return "{ss:'ddd',res:'ddd',int:11,boole:true}";
    }
}
