package com.ms.tax.calculator.domain.entities;

import java.math.BigDecimal;

public class NationalSimpleTax extends Tax {

	private String aliquot;
	private String regime;

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
