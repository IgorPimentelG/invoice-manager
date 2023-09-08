package com.ms.client.infra.mappers;

import com.ms.client.domain.entities.Company;
import com.ms.client.domain.types.TaxRegime;
import com.ms.client.infra.dtos.CreateCompanyDto;
import com.ms.client.infra.dtos.UpdateCompanyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

	@Mapping(source = "address", target = "address", ignore = true)
	Company createCompany(CreateCompanyDto source);

	@Mapping(target = "manager", ignore = true)
	@Mapping(target = "address", ignore = true)
	void map(Company source, @MappingTarget Company target);

	default Company map(UpdateCompanyDto data) {
		var company = new Company();
		if (data.getCorporateName() != null) company.setCorporateName(data.getCorporateName());
		if (data.getPhone() != null) company.setPhone(data.getPhone());
		if (data.getEmail() != null) company.setEmail(data.getEmail());
		if (data.getTaxRegime() != null) company.setTaxRegime(TaxRegime.valueOf(data.getTaxRegime()));

		return company;
	}
}
