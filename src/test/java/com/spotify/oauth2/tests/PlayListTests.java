package com.spotify.oauth2.tests;

import com.spotify.oauth2.apis.RestResource;
import com.spotify.oauth2.apis.StatusCode;
import com.spotify.oauth2.pojos.Playlist;
import com.spotify.oauth2.pojos.Tracks;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static com.spotify.oauth2.apis.Route.*;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static com.spotify.oauth2.apis.TokenManager.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PlayListTests {
    public String playlistId;
    public Playlist createdPlayList;

    @Story("`SHAR`-12345") //On which story you are working
    @Link("https://ShariqNaeem.org") //link on which you are working
    @Link(name = "allure", type = "myLink")
    @TmsLink("TC-12345")  // Teams management system: used for test cases link
    @Issue("Bug-12345")   // If there is some issue raised related to the test case
    @Test(priority = 1, description = "Create a playlist in the user")
    public void createPlaylistTest(){
        // FakerApi Used for fake data
        Playlist playlist = playlistBuilder(generateName(), generateDescription(), false);
        createdPlayList = playlist;
        Response response = RestResource.post(USERS +"/"+ ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id") + PLAYLISTS, getToken(), playlist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

        Playlist deserializePlaylist = response.as(Playlist.class);
        assertThat(deserializePlaylist.getName(), equalTo(playlist.getName()));
        assertThat(deserializePlaylist.getDescription(), equalTo(playlist.getDescription()));
        assertThat(deserializePlaylist.get_public(), equalTo(playlist.get_public()));

        playlistId = deserializePlaylist.getId();
    }

    @Test(priority = 3, description = "Get all the playlists of the current user")
    public void getCurrentUserPlaylists() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("limit","10");
        queryParams.put("offset","0");

        Response response = RestResource.get(ME+PLAYLISTS, queryParams, getToken());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Tracks playlists = response.as(Tracks.class);
        assertThat(playlists.getOffset(), is(equalTo(Integer.parseInt(queryParams.get("offset")))));
        assertThat(playlists.getLimit(), is(equalTo(Integer.parseInt(queryParams.get("limit")))));
    }

    @Test(priority = 2, description = "Get all the playlists of the specific user")
    public void getSpecificUserPlaylists() {
        Response response = RestResource.get(USERS +"/"+ ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id") +PLAYLISTS, getToken());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Tracks playlists = response.as(Tracks.class);
        assertThat(playlists.getOffset(), is(equalTo(0))); //By default value of offset
        assertThat(playlists.getLimit(), is(equalTo(20))); //By default value of limit
        String s = playlists.getHref();
        Assert.assertTrue(playlists.getHref().contains(ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id")));
    }

    @Test(priority = 4, description = "Get a playlist that is created above", dependsOnMethods = "createPlaylistTest")
    public void getCreatedPlaylist() {
        Response response = RestResource.get(PLAYLISTS +"/"+ playlistId, getToken());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Playlist deserializePlaylist = response.as(Playlist.class);

        assertThat(deserializePlaylist.getName(), equalTo(createdPlayList.getName()));
        assertThat(deserializePlaylist.getDescription(), equalTo(createdPlayList.getDescription()));
    }

    public Playlist playlistBuilder(String name, String description, boolean _public) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }
}
