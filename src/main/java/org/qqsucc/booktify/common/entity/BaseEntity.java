package org.qqsucc.booktify.common.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity implements Serializable {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@EqualsAndHashCode.Include
	UUID id;

}
