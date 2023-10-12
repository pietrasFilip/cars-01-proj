package com.app.web;


import com.app.web.config.AppConfig;
import com.app.web.routing.CarsRouting;
import com.app.web.routing.ErrorRouting;
import com.app.web.routing.SecurityRouting;
import com.app.web.routing.UserRouting;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {

        initExceptionHandler(e -> System.out.println(e.getMessage()));
        port(8080);

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        var errorRouting = context.getBean("errorRouting", ErrorRouting.class);
        errorRouting.routes();

        var securityRouting = context.getBean("securityRouting", SecurityRouting.class);
        securityRouting.routes();

        var carsRouting = context.getBean("carsRouting", CarsRouting.class);
        carsRouting.routes();

        var userRouting = context.getBean("userRouting", UserRouting.class);
        userRouting.routes();
    }
}