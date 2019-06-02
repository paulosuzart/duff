package br.com.alexandre.duff.web.service;

import br.com.alexandre.duff.domain.DuffMan.Opinion;
import br.com.alexandre.duff.domain.DuffMan.Opinion.Item;
import br.com.alexandre.duff.domain.Playlist;
import br.com.alexandre.duff.domain.Temperature;
import br.com.alexandre.duff.domain.Track;
import br.com.alexandre.duff.spotify.SpotifyService;
import br.com.alexandre.duff.spotify.domain.PlaylistResponse;
import br.com.alexandre.duff.spotify.domain.TracksResponse;
import br.com.alexandre.duff.web.repository.DuffManRepository;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DuffManService {

  @Autowired
  private SpotifyService spotifyService;

  @Autowired
  private DuffManRepository duffmanRepository;

  public Mono<Opinion> askOpinion(final Temperature temperature) {
    final Mono<String> authorizationTokenMono = spotifyService.getAuthorizationToken();

    return authorizationTokenMono.flatMap(authorizationToken ->
      duffmanRepository.classificateBeers(temperature)
        .flatMap(classification ->
          searchPlaylists(classification.getBeerStyle(), authorizationToken)
            .collectList().map(playLists -> new Item(classification.getBeerStyle(), playLists))
        ).collectList().map(Opinion::new));

  }

  private Flux<Playlist> searchPlaylists(final String beerStyle, final String authorizationToken) {
    final Mono<PlaylistResponse> playlistResponse = spotifyService.searchPlayLists(beerStyle, authorizationToken);

    return playlistResponse.flatMapMany(response ->
      Flux.fromIterable(response.getPlaylists().getItems())
        .flatMap(g ->
          spotifyService.getTracksFromPlaylist(g.getTracks().getHref(), authorizationToken)
            .map(tracksResponse -> new Playlist(g.getName(), toListOfTracks(tracksResponse)))
        ));
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
