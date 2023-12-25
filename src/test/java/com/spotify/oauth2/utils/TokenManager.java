package com.spotify.oauth2.utils;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.apis.RestResource.postAccount;

public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken(){
        try{
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("***Renew the Authentication Token***");
                Response response = renewToken();
                access_token = response.path("access_token");

                int tokenExpiryTimeInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(tokenExpiryTimeInSeconds - 300);
            }else {
                System.out.println("TOKEN is good to use...");
            }
        }catch (Exception err){
            throw new RuntimeException("Renew access token is not found...");
        }
        System.out.println(access_token);
        return access_token;
    }

    public static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getConfigLoaderInstance().getPropertyValue("grant_type"));
        formParams.put("refresh_token", ConfigLoader.getConfigLoaderInstance().getPropertyValue("refresh_token"));
        formParams.put("client_id", ConfigLoader.getConfigLoaderInstance().getPropertyValue("client_id"));
        formParams.put("client_secret", ConfigLoader.getConfigLoaderInstance().getPropertyValue("client_secret"));

        Response response = postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT! Renew Token Failed...");
        }

        return response;
    }
}
