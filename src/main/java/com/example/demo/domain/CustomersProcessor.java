package com.example.demo.domain;

import reactor.core.publisher.Flux;

import java.util.Comparator;

public class CustomersProcessor implements SortDomainEntity<Customer>{
    @Override
    public Flux<Customer> sort(Flux<Customer> customers) {

        return customers
                .filter(c1 -> c1.getDueTime() != null)
                .sort(Comparator.comparing(Customer::getDueTime));
    }
}
