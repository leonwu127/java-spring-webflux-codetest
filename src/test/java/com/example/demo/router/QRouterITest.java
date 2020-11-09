package com.example.demo.router;

import com.example.demo.domain.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.Consts.UTF_8;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QRouterITest {
    @Value("classpath:customers-test.json")
    private Resource customers;

    @Value("classpath:customers-simple-test.json")
    private Resource simpleCustomers;

    @Value("classpath:customers-simple-response.json")
    private Resource simpleCustomersResponse;

    @Value("classpath:customers-test-null-duetime.json")
    private Resource customersWithNullDueTime;

    @Value("classpath:customers-test-null-duetime-response.json")
    private Resource customersWithNullDueTimeResponse;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    public void aValidSimpleCustomers_PostRequestForSorting_CustomersSortedByDueTime() throws IOException {
        List<Customer> expectedCustomers = objectMapper.readValue(
                asString(simpleCustomersResponse),new TypeReference<List<Customer>>() {});

        String response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(asString(simpleCustomers))
                .post("/customers/sorted")
                .then()
                .extract()
                .response()
                .getBody()
                .prettyPrint();

        List<Customer> customers = objectMapper.readValue(response, new TypeReference<List<Customer>>() {});
        assertArrayEquals(expectedCustomers.toArray(), customers.toArray());
    }

    @Test
    public void aCustomerWithNullDueTime_PostRequestForSorting_CustomersSortedByDueTimeWithoutNullCustomer() throws IOException {
        List<Customer> expectedCustomers = objectMapper.readValue(
                asString(customersWithNullDueTimeResponse),new TypeReference<List<Customer>>() {});

        String response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(asString(customersWithNullDueTime))
                .post("/customers/sorted")
                .then()
                .extract()
                .response()
                .getBody()
                .prettyPrint();

        List<Customer> customers = objectMapper.readValue(response, new TypeReference<List<Customer>>() {});
        assertArrayEquals(expectedCustomers.toArray(), customers.toArray());
    }

    @Test
    public void invalidUrlPath_PostRequestForSorting_Return404() throws IOException {

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(asString(customers))
                .post("/invalid/path")
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract();
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}