package br.com.alexandre.duff.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;

import br.com.alexandre.duff.domain.DuffMan;
import br.com.alexandre.duff.domain.Playlist;
import br.com.alexandre.duff.domain.Temperature;
import br.com.alexandre.duff.domain.Track;
import br.com.alexandre.duff.spotify.SpotifyService;
import br.com.alexandre.duff.spotify.domain.PlaylistResponse;
import br.com.alexandre.duff.spotify.domain.TracksResponse;
import br.com.alexandre.duff.web.repository.DuffManRepository;

@Service
public class DuffManService {

	@Autowired
	private SpotifyService spotifyService;

	@Autowired
	private DuffManRepository duffmanRepository;

	public DuffMan.Opinion askOpinion(final Temperature temperature) {
		final String authorizationToken = spotifyService.getAuthorizationToken();
		return new DuffMan.Opinion(duffmanRepository.classificateBeers(temperature)
				.stream()
				.map(c -> new DuffMan.Opinion.Item(c.getBeerStyle(), searchPlaylists(c.getBeerStyle(), authorizationToken)))
				.collect(Collectors.toList()));
	}

	private List<Playlist> searchPlaylists(final String beerStyle, final String authorizationToken) {
		final PlaylistResponse playlistResponse = spotifyService.searchPlaylists(beerStyle, authorizationToken);
		if (playlistResponse != null && playlistResponse.getPlaylists() != null && playlistResponse.getPlaylists().getItems() != null) {
			return playlistResponse.getPlaylists().getItems()
					.parallelStream()
					.map(g -> {
						final TracksResponse tracksResponse = spotifyService.getTracksFromPlaylist(g.getTracks().getHref(), authorizationToken);
						return new Playlist(g.getName(), toListOfTracks(tracksResponse));
					})
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}
	
	private List<Track> toListOfTracks(final TracksResponse response) {
		if (response != null && response.getItems() != null) {
			return response.getItems().stream()
					.map(t -> new Track(t.getTrack().getName(), Joiner.on(",")
							.skipNulls()
							.join(t.getTrack().getArtists()
									.stream()
									.map(a -> a.getName())
									.collect(Collectors.toList())), t.getTrack().getExternal_urls().getSpotify()))
					.collect(Collectors.toList());
		} else {
			return new ArrayList<Track>();
		}
	}

}
