package skeleton.utils;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

	private JsonUtils() {
	}

	public static String toJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(value);
	}

	public static Properties getMessages(Locale locale) throws JsonGenerationException, JsonMappingException, IOException {

		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		CustomReloadableResourceBundleMessageSource msgSrc = (CustomReloadableResourceBundleMessageSource) webAppContext
				.getBean("messageSource");
		
		return msgSrc.getAllProperties(locale);
	}
}
