package com.ms.client.infra.mappers;

import com.ms.client.domain.entities.Address;
import com.ms.client.infra.dtos.CreateAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
	Address createAddress(CreateAddressDto source);
	void map(Address source, @MappingTarget Address target);
}
