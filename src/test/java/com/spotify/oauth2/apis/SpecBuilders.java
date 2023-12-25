package com.spotify.oauth2.apis;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.apis.Route.BASE_PATH;

public class SpecBuilders {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured()) //This will print request and response in the report
                .log(LogDetail.ALL).build();
    }

    // For creation of token
    public static RequestSpecification getAccountRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://accounts.spotify.com/")
                .setContentType(ContentType.URLENC)
                .addFilter(new AllureRestAssured()) //This will print request and response in the report
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
