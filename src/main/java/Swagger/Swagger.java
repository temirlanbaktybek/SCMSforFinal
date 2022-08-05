package Swagger;

import io.swagger.jaxrs.config.BeanConfig;


import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import java.util.Set;

public class Swagger {
    public Swagger(@Context ServletConfig servletConfig) {
        super();

        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Supply chain management system");
        beanConfig.setBasePath("/final");
        beanConfig.setHost("localhost:8075");
        beanConfig.setResourcePackage("com/swagger");
        beanConfig.setScan(true);
    }
}