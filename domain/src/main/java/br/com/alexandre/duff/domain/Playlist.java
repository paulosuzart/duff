package br.com.alexandre.duff.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Playlist implements Serializable {

  private String name;
  private List<Track> tracks;

}
