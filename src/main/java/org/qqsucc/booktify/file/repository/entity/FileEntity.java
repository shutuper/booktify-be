package org.qqsucc.booktify.file.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;
import org.qqsucc.booktify.common.entity.BaseEntity;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class FileEntity extends BaseEntity {

	String filename;

	@Column(name = "s3_key")
	String s3key;

	@Nullable
	UUID uploadedById;

}
