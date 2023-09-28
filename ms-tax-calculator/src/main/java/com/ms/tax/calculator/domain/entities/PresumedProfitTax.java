package com.ms.tax.calculator.domain.entities;

import java.math.BigDecimal;

public class PresumedProfitTax extends Tax {

	private String aliquotIRPJ;
	private String aliquotISS;
	private String aliquotCONFINS;
	private BigDecimal taxIRPJ;
	private BigDecimal taxISS;
	private BigDecimal taxCONFINS;

	public PresumedProfitTax(
	  String invoiceNumber,
	  String type,
	  BigDecimal invoiceAmount,
	  BigDecimal taxAmount,
	  String aliquotIRPJ,
	  String aliquotISS,
	  String aliquotCONFINS,
	  BigDecimal taxIRPJ,
	  BigDecimal taxISS,
	  BigDecimal taxCONFINS
	) {
		super(invoiceNumber, type, invoiceAmount, taxAmount);
		this.aliquotIRPJ = aliquotIRPJ;
		this.aliquotISS = aliquotISS;
		this.aliquotCONFINS = aliquotCONFINS;
		this.taxIRPJ = taxIRPJ;
		this.taxISS = taxISS;
		this.taxCONFINS = taxCONFINS;
	}

	public String getAliquotIRPJ() {
		return aliquotIRPJ;
	}

	public void setAliquotIRPJ(String aliquotIRPJ) {
		this.aliquotIRPJ = aliquotIRPJ;
	}

	public String getAliquotISS() {
		return aliquotISS;
	}

	public void setAliquotISS(String aliquotISS) {
		this.aliquotISS = aliquotISS;
	}

	public String getAliquotCONFINS() {
		return aliquotCONFINS;
	}

	public void setAliquotCONFINS(String aliquotCONFINS) {
		this.aliquotCONFINS = aliquotCONFINS;
	}

	public BigDecimal getTaxIRPJ() {
		return taxIRPJ;
	}

	public void setTaxIRPJ(BigDecimal taxIRPJ) {
		this.taxIRPJ = taxIRPJ;
	}

	public BigDecimal getTaxISS() {
		return taxISS;
	}

	public void setTaxISS(BigDecimal taxISS) {
		this.taxISS = taxISS;
	}

	public BigDecimal getTaxCONFINS() {
		return taxCONFINS;
	}

	public void setTaxCONFINS(BigDecimal taxCONFINS) {
		this.taxCONFINS = taxCONFINS;
	}
}
