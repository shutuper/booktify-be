package org.qqsucc.booktify.file.controller.mapper;

import org.mapstruct.Mapper;
import org.qqsucc.booktify.file.controller.dto.FileDto;
import org.qqsucc.booktify.file.repository.entity.FileEntity;

@Mapper(componentModel = "spring")
public interface FileMapper {

	FileDto toDto(FileEntity file);

}
