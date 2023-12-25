package com.spotify.oauth2.tests;

import com.spotify.oauth2.apis.RestResource;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.TokenManager.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PlayListTests {
    @Test
    public void createPlaylist(){
        String reqBody = "{\n" +
                "    \"name\": \"Testing1234567\",\n" +
                "    \"description\": \"New testing playlist description with Testing1234567\",\n" +
                "    \"public\": false\n" +
                "}";

        Response response = RestResource.post("/v1/users/"+ ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id") + "/playlists", getToken(), reqBody);
        assertThat(response.statusCode(), is(equalTo(201)));
        //assertThat(response.path(""));
    }
}
