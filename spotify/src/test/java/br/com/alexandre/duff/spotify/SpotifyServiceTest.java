package br.com.alexandre.duff.spotify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import br.com.alexandre.duff.spotify.domain.PlaylistResponse;
import br.com.alexandre.duff.spotify.domain.TracksResponse;
import br.com.alexandre.duff.spotify.exception.SpotifyErrorException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class SpotifyServiceTest {

	private static MockWebServer server;
	
	@Autowired
	private SpotifyService spotifyService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		server = new MockWebServer();
		server.start(TestConfiguration.SERVER_PORT);
	}
		
	@Test
	public void shouldReceiveSpotifyPlaylistsResponse() throws IOException {
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).setBody(Resources.toString(Resources.getResource("json/authorization.json"), Charsets.UTF_8)));
		
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).setBody(Resources.toString(Resources.getResource("json/playlists.json"), Charsets.UTF_8)));
		
		final String authorizationToken = this.spotifyService.getAuthorizationToken();
		
		final PlaylistResponse playlistResponse = this.spotifyService.searchPlayLists("Teste", authorizationToken);
		assertNotNull(playlistResponse);
		assertNotNull(playlistResponse.getPlaylists());
		assertNotNull(playlistResponse.getPlaylists().getItems());
		assertEquals(20, playlistResponse.getPlaylists().getItems().size());
		playlistResponse.getPlaylists().getItems().forEach(item ->  {
			assertNotNull(item.getName());
			assertNotNull(item.getTracks());
			assertNotNull(item.getTracks().getHref());			
		});		
	}
	
	@Test
	public void shouldReceiveSpotifyTracksResponse() throws IOException {
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).setBody(Resources.toString(Resources.getResource("json/authorization.json"), Charsets.UTF_8)));
		
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).setBody(Resources.toString(Resources.getResource("json/tracks.json"), Charsets.UTF_8)));
		
		final String authorizationToken = this.spotifyService.getAuthorizationToken();

		final TracksResponse response = this.spotifyService.getTracksFromPlaylist("http://localhost:" + TestConfiguration.SERVER_PORT + "/v1/playlists/21THa8j9TaSGuXYNBU5tsC", authorizationToken);
		assertNotNull(response);
		assertNotNull(response.getItems());
		response.getItems().forEach(i -> {
			assertNotNull(i.getTrack());
			assertNotNull(i.getTrack().getName());
			assertNotNull(i.getTrack().getExternal_urls());
			assertNotNull(i.getTrack().getExternal_urls().getSpotify());
			assertNotNull(i.getTrack().getArtists());
		});
	}
	
	@Test(expected=SpotifyErrorException.class)
	public void shouldHandleInvalidResponse() throws IOException {
		server.enqueue(new MockResponse()
				.setResponseCode(400)
				.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).setBody(Resources.toString(Resources.getResource("json/authorizationError.json"), Charsets.UTF_8)));

		this.spotifyService.getAuthorizationToken();		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws IOException {
		server.close();
	}

}
