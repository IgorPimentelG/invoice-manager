package com.ms.client.infra.mappers;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.dtos.CreateManagerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

	ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

	@Mapping(target = "authorities", ignore = true)
	Manager createManager(CreateManagerDto managerDto);

	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "id", source = "id", ignore = true)
	void map(Manager source, @MappingTarget Manager target);
}
