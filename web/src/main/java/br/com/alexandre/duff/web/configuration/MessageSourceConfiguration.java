package br.com.alexandre.duff.web.configuration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class MessageSourceConfiguration {

	@Autowired
    private MessageSource messageSource;

	@Bean
	public MessageSourceAccessor accessor() { 
    	return new MessageSourceAccessor(messageSource);
	}
	
//	@Bean
//	public LocaleResolver localeResolver() {
//	    final AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//	    localeResolver.setDefaultLocale(Locale.US);
//	    return localeResolver;
//	}
}


