package com.simoes.customerservice.config;

import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StellaConfig {

	@Bean
	public CPFValidator cpfValidator() {
		return new CPFValidator();
	}

}
