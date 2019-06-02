package br.com.alexandre.duff.spotify.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PlaylistResponse implements Serializable {

  private Playlists playlists;

  @Data
  public static class Playlists implements Serializable {

    private List<Item> items = new ArrayList<>();

    @Data
    public static class Item implements Serializable {


      private String name;
      private Tracks tracks;

      @Data
      public static class Tracks implements Serializable {

        private String href;

      }
    }
  }
}
