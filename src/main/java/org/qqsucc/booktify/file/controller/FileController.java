package org.qqsucc.booktify.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.file.controller.dto.DownloadFileStreamDto;
import org.qqsucc.booktify.file.controller.dto.FileDto;
import org.qqsucc.booktify.file.controller.facade.FileFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "file")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileController {

	FileFacade fileFacade;

	@GetMapping("/public/files/{fileId}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable UUID fileId,
														  @RequestParam(defaultValue = "false") Boolean isAttachment) {
		DownloadFileStreamDto downloadFileDto = fileFacade.download(fileId);

		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION, FileUtils.toContentDisposition(downloadFileDto.getFilename(), isAttachment))
				.contentType(downloadFileDto.getMediaType())
				.body(downloadFileDto.getStreamingResponseBody());
	}

	@PostMapping(value = "/private/files", consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileDto> upload(@RequestPart(value = "file", required = false) MultipartFile file) {
		FileDto response = fileFacade.upload(file);
		return ResponseEntity.ok(response);
	}

}
