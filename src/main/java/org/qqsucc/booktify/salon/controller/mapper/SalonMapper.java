package org.qqsucc.booktify.salon.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.qqsucc.booktify.salon.controller.dto.SalonDto;
import org.qqsucc.booktify.salon.repository.entity.Salon;

@Mapper(componentModel = "spring")
public interface SalonMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "salonMasters", ignore = true)
	Salon toEntity(SalonDto dto);

	SalonDto toDto(Salon entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "salonMasters", ignore = true)
	void putMerge(SalonDto salonDto, @MappingTarget Salon salon);
}
