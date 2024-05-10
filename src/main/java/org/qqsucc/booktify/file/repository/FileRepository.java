package org.qqsucc.booktify.file.repository;

import org.qqsucc.booktify.file.repository.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
