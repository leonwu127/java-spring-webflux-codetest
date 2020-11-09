package com.example.demo.domain;

import reactor.core.publisher.Flux;

public interface SortDomainEntity<E> {

    Flux<E> sort (Flux<E> t);
}
