package br.com.alexandre.duff.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Track implements Serializable {

  private String name;
  private String artist;
  private String link;

}
