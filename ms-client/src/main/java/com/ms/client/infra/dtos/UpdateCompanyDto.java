package com.ms.client.infra.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UpdateCompanyDto {
  @Length(min = 1, max = 60, message = "Corporate name needs a min of 1 characters and max of 60")
  private String corporateName;

  @Pattern(
    regexp = "^(SIMPLE_NATIONAL|PRESUMED_PROFIT)$",
    message = "Allowed regimes: SIMPLE_NATIONAL or PRESUMED_PROFIT"
  )
  private String taxRegime;

  @Email(message = "Email is invalid. The allowed format is exemple@exemple.com")
  private String email;

  @Pattern(
    regexp = "^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$",
    message = "Phone is invalid. The allowed format is (xx) xxxxx-xxxx"
  )
  private String phone;

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public String getTaxRegime() {
    return taxRegime;
  }

  public void setTaxRegime(String taxRegime) {
    this.taxRegime = taxRegime;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
