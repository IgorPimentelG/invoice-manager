package com.ms.tax.calculator.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "national_simple_taxes")
public class NationalSimpleTax extends Tax implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String aliquot;
	private String regime;

	public NationalSimpleTax() {
		super(null, null, null, null);
	}

	public NationalSimpleTax(
	  String invoiceNumber,
	  String type,
	  BigDecimal invoiceAmount,
	  BigDecimal taxAmount,
	  String aliquot,
	  String regime
	) {
		super(invoiceNumber, type, invoiceAmount, taxAmount);
		this.aliquot = aliquot;
		this.regime = regime;
	}

	public String getAliquot() {
		return aliquot;
	}

	public void setAliquot(String aliquot) {
		this.aliquot = aliquot;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}
}
