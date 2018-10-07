package br.com.alexandre.duff.domain;

import java.io.Serializable;

public class Temperature implements Serializable {

	private static final long serialVersionUID = -2809298617872818033L;

	private Integer temperature;
	
	public Temperature() { }
	
	public Temperature(final Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Temperature other = (Temperature) obj;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (temperature != null) ? temperature.toString() : "";
	}
	
	
}
