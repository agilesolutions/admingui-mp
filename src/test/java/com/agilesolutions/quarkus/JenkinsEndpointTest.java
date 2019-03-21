package com.agilesolutions.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JenkinsEndpointTest {

//    @Test
    public void testHelloEndpoint() {
        given()
          .when().post("/jenkins")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}