package org.qqsucc.booktify.file.controller.facade;

import org.qqsucc.booktify.file.controller.dto.DownloadFileStreamDto;
import org.qqsucc.booktify.file.controller.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileFacade {

	DownloadFileStreamDto download(UUID fileId);

	FileDto upload(MultipartFile file);

}
