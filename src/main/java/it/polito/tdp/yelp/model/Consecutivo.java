package it.polito.tdp.yelp.model;

import java.util.Objects;

public class Consecutivo {

	Review partenza;
	Review arrivo;
	double peso;
	public Consecutivo(Review partenza, Review arrivo, double peso) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.peso = peso;
	}
	public Review getPartenza() {
		return partenza;
	}
	public void setPartenza(Review partenza) {
		this.partenza = partenza;
	}
	public Review getArrivo() {
		return arrivo;
	}
	public void setArrivo(Review arrivo) {
		this.arrivo = arrivo;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arrivo, partenza, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consecutivo other = (Consecutivo) obj;
		return Objects.equals(arrivo, other.arrivo) && Objects.equals(partenza, other.partenza)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso);
	}
	@Override
	public String toString() {
		return "Consecutivo [partenza=" + partenza + ", arrivo=" + arrivo + ", peso=" + peso + "]";
	}
	
	
}
