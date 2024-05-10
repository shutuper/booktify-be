package org.qqsucc.booktify.file.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.IOUtils;
import org.qqsucc.booktify.file.controller.FileUtils;
import org.qqsucc.booktify.file.controller.dto.DownloadFileStreamDto;
import org.qqsucc.booktify.file.controller.dto.FileDto;
import org.qqsucc.booktify.file.controller.facade.FileFacade;
import org.qqsucc.booktify.file.controller.mapper.FileMapper;
import org.qqsucc.booktify.file.repository.entity.FileEntity;
import org.qqsucc.booktify.file.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileFacadeImpl implements FileFacade {

	FileService fileService;
	FileMapper fileMapper;

	@Override
	public DownloadFileStreamDto download(UUID fileId) {
		FileEntity file = fileService.findById(fileId);
		return DownloadFileStreamDto.builder()
				.filename(file.getFilename())
				.mediaType(FileUtils.getMediaType(file.getFilename()))
				.streamingResponseBody(outputStream -> {
					try (InputStream inputStream = fileService.downloadFromS3(file.getS3key())) {
						IOUtils.copy(inputStream, outputStream);
					}
				})
				.build();
	}

	@Override
	public FileDto upload(MultipartFile file) {
		return fileMapper.toDto(fileService.upload(file));
	}
}
