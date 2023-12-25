package com.spotify.oauth2.apis;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.spotify.oauth2.apis.Route.API;
import static com.spotify.oauth2.apis.Route.TOKEN;
import static com.spotify.oauth2.apis.SpecBuilders.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RestResource {
    public static Response postAccount(HashMap<String, String> formParams) {
        return given()
                    .spec(getAccountRequestSpec())
                    .formParams(formParams)
                .when()
                    .post(API + TOKEN)
                .then().spec(getResponseSpec())
                    .extract().response();
    }

    public static Response post(String path, String token, Object body) {
        return given().spec(getRequestSpec())
                    .body(body)
                    .auth().oauth2(token) //this way can be also used for token
                    //.header("Authorization", "Bearer " + token)
                .when()
                    .post(path)
                .then().spec(getResponseSpec())
                    .extract().response();
    }
}
