package com.ms.client.main.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

public class Mapper {

	@Bean
	public ModelMapper getMapper() {
		var mapper = new ModelMapper();

		mapper.getConfiguration()
		  .setSkipNullEnabled(true)
		  .setMatchingStrategy(MatchingStrategies.STRICT);

		return mapper;
	}
}
