package com.way.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
@Configuration
public class CharsetFilter {
	
    /*@Value("${spring.http.encoding.charset}")  
    private Charset charSet;
    */
    /*@Value("${spring.http.encoding.enable}")  
    private boolean enable;
    
    @Value("${spring.http.encoding.force}")  
    private boolean force;*/
	

	@Bean
	@ConditionalOnMissingBean(CharacterEncodingFilter.class)
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceRequestEncoding(true);
		filter.setForceResponseEncoding(true);
		return filter;
	}

}
