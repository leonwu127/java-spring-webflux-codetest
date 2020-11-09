package com.example.demo.handler;

import com.example.demo.application.service.QService;
import com.example.demo.domain.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomersHandler {

    private final QService qService;

    CustomersHandler(QService qService) {
        this.qService = qService;
    }

    public Mono<ServerResponse> sortCustomers(ServerRequest request) {
        Flux<Customer> customers = request.bodyToFlux(Customer.class);
        return ServerResponse
                .ok()
                .body(qService.sortCustomersByDueTime(customers),
                        Customer.class);
    }
}
