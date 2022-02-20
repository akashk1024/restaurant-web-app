package com.application.restaurantwebapp;

import com.application.restaurantwebapp.service.OrderService;
import com.application.restaurantwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RestaurantWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantWebAppApplication.class, args);
    }

}
