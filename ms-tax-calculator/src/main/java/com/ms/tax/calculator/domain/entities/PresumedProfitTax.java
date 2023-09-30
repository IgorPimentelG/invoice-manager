package com.ms.tax.calculator.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "presumed_profit_taxes")
public class PresumedProfitTax extends Tax implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "aliquot_irpj")
	private String aliquotIRPJ;

	@Column(name = "aliquot_iss")
	private String aliquotISS;

	@Column(name = "aliquot_confins")
	private String aliquotCONFINS;

	@Column(name = "tax_irpj")
	private BigDecimal taxIRPJ;

	@Column(name = "tax_iss")
	private BigDecimal taxISS;

	@Column(name = "tax_confins")
	private BigDecimal taxCONFINS;

	public PresumedProfitTax() {
		super(null, null, null, null);
	}

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
