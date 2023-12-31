package com.ms.electronic.invoice.infra.mappers;

import com.ms.electronic.invoice.domain.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
	void map(Address source, @MappingTarget Address target);
}