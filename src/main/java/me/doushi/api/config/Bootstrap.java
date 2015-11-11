package me.doushi.api.config;


import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.ExternalDocs;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Component("bootstrap")
public class Bootstrap extends HttpServlet {

    private static final long serialVersionUID = -8054210804494637749L;
    @Value("${swagger.apiVersion}")
    private String version;

    @Value("${swagger.host}")
    private String host;

    @Value("${swagger.resourcePackage}")
    private String resourcePackage;

    @Value("${swagger.basePath}")
    private String basePath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();

        try {
            InputStream inputStream = Bootstrap.class.getResourceAsStream("/swagger.properties");

            Properties prop = new Properties();
            prop.load(inputStream);

            host = prop.getProperty("swagger.host");
            version = prop.getProperty("swagger.apiVersion");
            basePath = prop.getProperty("swagger.basePath");
            resourcePackage = prop.getProperty("swagger.resourcePackage");

            BeanConfig beanConfig = new BeanConfig();
            beanConfig.setVersion(version);
            beanConfig.setHost(host);
            beanConfig.setBasePath(basePath);
            beanConfig.setResourcePackage(resourcePackage);

            beanConfig.setTitle("Doushi REST API");
            beanConfig.setContact("doushiapi@doushi.me");
            beanConfig.setDescription("Doushi App REST API");
            String[] aa = {"https"};
//            beanConfig.setSchemes(aa);


            beanConfig.setScan(true);


        } catch (Exception e) {
            e.printStackTrace();
        }


        ServletContext context = config.getServletContext();
        Swagger swagger = new Swagger();
        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));

        swagger.tag(new Tag()
                .name("test")
                .description("Everything about your Pets")
                .externalDocs(new ExternalDocs("Find out more", "http://swagger.io")));
        swagger.tag(new Tag()
                .name("video")
                .description("视频资源接口"));
        swagger.tag(new Tag()
                .name("userAndVideo")
                .description("用户与视频资源接口"));

        swagger.tag(new Tag()
                .name("user")
                .description("用户资源管理")
                .externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
        context.setAttribute("swagger", swagger);

    }

//    public void init(ServletConfig config)
//            throws ServletException {
//        super.init(config);
//
//        try {
//        InputStream inputStream = Bootstrap.class.getResourceAsStream("/swagger.properties");
//
//        Properties prop = new Properties();
//        prop.load(inputStream);
//
//        host = prop.getProperty("swagger.host");
//        version = prop.getProperty("swagger.apiVersion");
//        basePath =  prop.getProperty("swagger.basePath");
//        resourcePackage = prop.getProperty("swagger.resourcePackage");
//
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion(version);
//        beanConfig.setHost(host);
//        beanConfig.setBasePath(basePath);
//        beanConfig.setResourcePackage(resourcePackage);
//
//        beanConfig.setScan(true);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ServletContext context = config.getServletContext();
//
//
//        Swagger swagger = new Swagger();
//        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
//
//
//        swagger.tag(new Tag()
//                .name("test")
//                .description("Everything about your Pets")
//                .externalDocs(new ExternalDocs("Find out more", "http://swagger.io")));
//        swagger.tag(new Tag()
//                .name("store")
//                .description("Access to Petstore orders"));
//        swagger.tag(new Tag()
//                .name("user")
//                .description("Operations about user")
//                .externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
//        context.setAttribute("swagger", swagger);
//    }


}

