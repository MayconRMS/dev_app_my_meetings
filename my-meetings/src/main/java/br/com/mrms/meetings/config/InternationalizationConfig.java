package br.com.mrms.meetings.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternationalizationConfig {

	//Pega o arquivo de messagens 
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
		msgSource.setBasename("classpath:messages");
		msgSource.setDefaultEncoding("UTF-8");
		msgSource.setDefaultLocale(Locale.getDefault());
		return msgSource;
	}

	//Insere o arquivo de mensagens no bean
	@Bean
	public LocalValidatorFactoryBean validationFactoryBean() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
}
