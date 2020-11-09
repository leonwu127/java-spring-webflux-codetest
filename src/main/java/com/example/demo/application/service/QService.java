package com.example.demo.application.service;

import com.example.demo.domain.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public interface QService {

    Flux<Customer> sortCustomersByDueTime(Flux<Customer> unsortedCustomers);
}
