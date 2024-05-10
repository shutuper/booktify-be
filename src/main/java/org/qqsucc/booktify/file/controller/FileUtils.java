package org.qqsucc.booktify.file.controller;

import org.springframework.http.MediaType;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class FileUtils {

	private static final FileNameMap FILE_NAME_MAP = URLConnection.getFileNameMap();

	private FileUtils() {
	}

	public static String toContentDisposition(String filename) {
		return toContentDisposition(filename, true);
	}

	public static String toContentDisposition(String filename, boolean isAttachment) {
		return String.format(
				"%s; filename*=UTF-8''%s",
				isAttachment ? "attachment" : "inline",
				URLEncoder.encode(filename, StandardCharsets.UTF_8)
						.replace("+", "%20") // Replace plus sign (+) with a space
		);
	}

	public static MediaType getMediaType(String filename) {
		return Optional.ofNullable(FILE_NAME_MAP.getContentTypeFor(filename))
				.map(MediaType::parseMediaType)
				.orElse(null);
	}

}
