package com.spotify.oauth2.tests;

import com.spotify.oauth2.apis.RestResource;
import com.spotify.oauth2.pojos.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static com.spotify.oauth2.utils.TokenManager.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PlayListTests {
    @Test
    public void createPlaylist(){
        // FakerApi Used for fake data
        Playlist playlist = playlistBuilder(generateName(), generateDescription(), false);

        Response response = RestResource.post("/v1/users/"+ ConfigLoader.getConfigLoaderInstance().getPropertyValue("user_id") + "/playlists", getToken(), playlist);
        assertThat(response.statusCode(), is(equalTo(201)));

        Playlist deserializePlaylist = response.as(Playlist.class);
        assertThat(deserializePlaylist.getName(), equalTo(playlist.getName()));
        assertThat(deserializePlaylist.getDescription(), equalTo(playlist.getDescription()));
        assertThat(deserializePlaylist.get_public(), equalTo(playlist.get_public()));

    }

    public Playlist playlistBuilder(String name, String description, boolean _public) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }
}
