package org.qqsucc.booktify.file.service;

import org.qqsucc.booktify.file.repository.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.UUID;

public interface FileService {

	FileEntity findById(UUID fileId);

	ResponseInputStream<GetObjectResponse> downloadFromS3(String s3key);

	FileEntity upload(MultipartFile file);
}
