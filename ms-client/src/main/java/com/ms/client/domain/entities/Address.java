package com.ms.client.domain.entities;

import com.ms.client.domain.validation.AddressValidator;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String state;
	private String city;
	private String street;
	private String neighborhood;
	private String number;

	@Column(name = "zip_code")
	private String zipCode;

	public Address() {}

	public Address(
	  long id,
	  String state,
	  String city,
	  String street,
	  String neighborhood,
	  String number,
	  String zipCode) {
		setId(id);
		setState(state);
		setCity(city);
		setStreet(street);
		setNeighborhood(neighborhood);
		setNumber(number);
		setZipCode(zipCode);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		AddressValidator.validate(state)
		  .isEmpty("State cannot be empty.")
		  .isState();
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		AddressValidator.validate(city).isEmpty("City cannot be empty.");
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		AddressValidator.validate(street).isEmpty("Street cannot be empty.");
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		AddressValidator.validate(neighborhood).isEmpty("Neighborhood cannot be empty.");
		this.neighborhood = neighborhood;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		AddressValidator.validate(number)
		  .isEmpty("Number cannot be empty.")
		  .isAddressNumber();
		this.number = number;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		AddressValidator.validate(zipCode)
		  .isEmpty("Zip code cannot be empty.")
		  .isZipCode();
		this.zipCode = zipCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.zipCode);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(id, address.id) &&
		  Objects.equals(zipCode, address.zipCode);
	}
}
