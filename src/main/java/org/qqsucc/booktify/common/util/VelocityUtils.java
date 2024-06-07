package org.qqsucc.booktify.common.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MapUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class VelocityUtils {

	VelocityEngine velocityEngine;

	public String mergeTemplate(String templateName, Map<String, Object> params) {
		VelocityContext context = new VelocityContext();

		if (MapUtils.isNotEmpty(params)) {
			params.forEach(context::put);
		}

		StringWriter stringWriter = new StringWriter();

		velocityEngine.mergeTemplate(templateName, StandardCharsets.UTF_8.name(), context, stringWriter);

		return stringWriter.toString();
	}

}
