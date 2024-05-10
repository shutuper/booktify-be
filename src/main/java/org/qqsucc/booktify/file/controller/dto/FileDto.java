package org.qqsucc.booktify.file.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FileDto {

	UUID id;

	String filename;

}
