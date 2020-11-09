package com.example.demo.router;

import com.example.demo.handler.CustomersHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class QRouter {

    @Bean
    public RouterFunction<ServerResponse> route(CustomersHandler customerHandler) {
        return RouterFunctions.route()
                .POST("/customers/sorted", customerHandler::sortCustomers)
                .build();
    }
}
