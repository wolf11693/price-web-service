package com.xantrix.webapp.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ValidationMessageConfig {
	@Bean(name = "messageSource")
	public MessageSource getMessageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("messages");
		resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);

		return resourceBundleMessageSource;
	}
	
	@Bean(name = "localValidator")
	public LocalValidatorFactoryBean getLocalValidator() {
		MessageSource theMessageSource = this.getMessageSource();

		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(theMessageSource);

		return localValidatorFactoryBean;
	}

	@Bean(name = "localResolver")
	public LocaleResolver getLocaleResolver() {
		Locale theLocale = LocaleContextHolder.getLocale();

		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(theLocale);
		// sessionLocaleResolver.setDefaultLocale(new Locale("it"));

		return sessionLocaleResolver;
	}
	
}
