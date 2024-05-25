package org.qqsucc.booktify.procedure.controller.mapper;

import org.mapstruct.*;
import org.qqsucc.booktify.procedure.controller.dto.ProcedureDto;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProcedureMapper {

	ProcedureDto toDto(Procedure entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "master", ignore = true)
	@Mapping(target = "status", constant = "ACTIVE")
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "masterId", source = "masterId")
	Procedure toEntity(UUID masterId, ProcedureDto dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "master", ignore = true)
	@Mapping(target = "masterId", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void patchMerge(ProcedureDto dto, @MappingTarget Procedure entity);

}
