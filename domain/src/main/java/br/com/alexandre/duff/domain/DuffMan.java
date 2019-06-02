package br.com.alexandre.duff.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public interface DuffMan {

  @Data
  @AllArgsConstructor
  class Opinion implements Serializable {

    private List<Item> items;

    @Data
    @RequiredArgsConstructor
    public static class Item implements Serializable {

      private final String beerStyle;
      private final List<Playlist> playlist;

    }
  }

  @Data
  class Classification implements Serializable {

    private String beerStyle;
    private Integer ranking;

  }
}
