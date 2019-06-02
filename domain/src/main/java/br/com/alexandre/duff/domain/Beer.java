package br.com.alexandre.duff.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
@Data
public class Beer implements Serializable {

  private String id;

  @Indexed(name = "unique_beer_style_name", unique = true)
  private String beerStyle;
  private Integer min;
  private Integer max;

  @JsonIgnore
  private Integer average;

  @Data
  @AllArgsConstructor
  public static class ID implements Serializable {

    private String id;


  }

}
