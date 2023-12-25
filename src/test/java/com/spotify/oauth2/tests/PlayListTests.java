package com.spotify.oauth2.tests;

import com.spotify.oauth2.apis.RestResource;
import com.spotify.oauth2.apis.StatusCode;
import com.spotify.oauth2.pojos.Playlist;
import com.spotify.oauth2.pojos.Tracks;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.spotify.oauth2.apis.Route.*;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static com.spotify.oauth2.apis.TokenManager.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PlayListTests {
    @Test(priority = 1, description = "Create a playlist in the user")
    public void createPlaylistTest(){
        // FakerApi Used for fake data
        Playlist playlist = playlistBuilder(generateName(), generateDescription(), false);

        Response response = RestResource.post(USERS +"/"+ ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id") + PLAYLISTS, getToken(), playlist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

        Playlist deserializePlaylist = response.as(Playlist.class);
        assertThat(deserializePlaylist.getName(), equalTo(playlist.getName()));
        assertThat(deserializePlaylist.getDescription(), equalTo(playlist.getDescription()));
        assertThat(deserializePlaylist.get_public(), equalTo(playlist.get_public()));
    }

    @Test(priority = 2, description = "Get all the playlists of the current user")
    public void getCurrentUserPlaylists() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("limit","10");
        queryParams.put("offset","0");

        Response response = RestResource.get(ME+PLAYLISTS, queryParams, getToken());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Tracks playlists = response.as(Tracks.class);
        assertThat(playlists.getOffset(), is(equalTo(0)));
        assertThat(playlists.getLimit(), is(equalTo(10)));
    }

    public Playlist playlistBuilder(String name, String description, boolean _public) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }
}
