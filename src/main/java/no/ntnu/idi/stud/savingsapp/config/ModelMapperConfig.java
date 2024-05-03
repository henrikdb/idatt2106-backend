package no.ntnu.idi.stud.savingsapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
		return new ModelMapper();
	}

}
