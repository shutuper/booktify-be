package org.qqsucc.booktify.file.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.file.repository.FileRepository;
import org.qqsucc.booktify.file.repository.entity.FileEntity;
import org.qqsucc.booktify.file.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileServiceImpl implements FileService {

	FileRepository fileRepository;
	S3Client s3Client;

	@NonFinal
	@Value("${s3.bucket-name}")
	String bucketName;

	@Override
	public FileEntity findById(UUID fileId) {
		return fileRepository
				.findById(fileId)
				.orElseThrow(() -> new NotFoundException("File not found"));
	}

	@Override
	public ResponseInputStream<GetObjectResponse> downloadFromS3(String s3key) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
				.bucket(bucketName)
				.key(s3key)
				.build();

		try {
			return s3Client.getObject(getObjectRequest);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new BusinessException("Failed file downloading: " + ex.getMessage());
		}
	}

	@Override
	public FileEntity upload(MultipartFile file) {
		String filename = file.getOriginalFilename();
		String s3Key = UUID.randomUUID().toString();

		uploadToS3(file, s3Key);

		FileEntity toSave = FileEntity.builder()
				.uploadedById(SecurityUtils.getAuthUserIdOpt().orElse(null))
				.s3key(s3Key)
				.filename(filename)
				.build();

		return fileRepository.save(toSave);
	}

	private void uploadToS3(MultipartFile file, String s3Key) {
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket(bucketName)
					.key(s3Key)
					.contentType(file.getContentType())
					.contentLength(file.getSize()).build();

			RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

			s3Client.putObject(putObjectRequest, requestBody);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new BusinessException("Failed file uploading: " + ex.getMessage());
		}
	}
}
