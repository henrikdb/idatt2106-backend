package no.ntnu.idi.stud.savingsapp.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class for the ModelMapper bean.
 */
@Configuration
public class ModelMapperConfig {

	/**
	 * Configures and provides the ModelMapper bean.
	 * @return ModelMapper bean configured with custom mappings.
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(new StringToTimestampConverter());
		return modelMapper;
	}

	private static class StringToTimestampConverter implements Converter<String, Timestamp> {

		private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		@Override
		public Timestamp convert(MappingContext<String, Timestamp> mappingContext) {
			String source = mappingContext.getSource();
			LocalDateTime dateTime = LocalDateTime.parse(source, this.formatter);
			return Timestamp.valueOf(dateTime);
		}

	}

}
