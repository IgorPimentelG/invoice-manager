package com.ms.electronic.invoice.infra.mappers;

import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.infra.dtos.CreateInvoiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvoiceMapper {

	InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

	@Mapping(target = "number", ignore = true)
	@Mapping(target = "validationCode", ignore = true)
	@Mapping(target = "dateIssue", ignore = true)
	@Mapping(target = "canceled", ignore = true)
	@Mapping(target = "issuer", ignore = true)
	@Mapping(target = "reference", source = "reference")
	Invoice createEntity(CreateInvoiceDto source);
}
