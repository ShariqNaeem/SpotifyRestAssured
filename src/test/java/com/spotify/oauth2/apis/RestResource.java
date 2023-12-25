package com.spotify.oauth2.apis;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RestResource {
    public static Response postAccount(HashMap<String, String> formParams){
        return given().
                baseUri("https://accounts.spotify.com").
                contentType(ContentType.URLENC).
                formParams(formParams).log().all().
        when().
                post("/api/token").
        then().log().all().
                extract().response();
    }

    public static Response post(String path, String token, String body){
        return given().
                    baseUri("https://api.spotify.com").
                    auth().oauth2(token).
                    body(body).
                    log().all().
                when().
                    post(path).
                then().log().all().
                    extract().response();
    }
}
