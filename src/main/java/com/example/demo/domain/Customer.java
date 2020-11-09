package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Customer {
    @JsonProperty("id")
    Long id;
    @JsonProperty("name")
    String name;
    @JsonProperty("duetime")
    ZonedDateTime dueTime;
    @JsonProperty("jointime")
    ZonedDateTime joinTime;
}
