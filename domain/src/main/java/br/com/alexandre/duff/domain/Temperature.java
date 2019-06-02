package br.com.alexandre.duff.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Temperature implements Serializable {

  private Integer temperature;

}
