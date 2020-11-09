package com.example.demo.application.service;

import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomersProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class QServiceImpl implements QService{

    private final CustomersProcessor customersProcessor;

    @Autowired
    public QServiceImpl(CustomersProcessor customersProcessor) {
        this.customersProcessor = customersProcessor;
    }

    @Override
    public Flux<Customer> sortCustomersByDueTime(Flux<Customer> unsortedCustomers) {
        return customersProcessor.sort(unsortedCustomers);
    }
}
