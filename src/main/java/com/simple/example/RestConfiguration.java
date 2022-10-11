package com.simple.example;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Rest Route Builder Class
 * 
 * @author Roy Chela | Bring Global
 * @version 1.0.0
 * @since Nov 22, 2020
 * @implNote This class configures the rest component for all endpoints
 */
@Component
public class RestConfiguration extends RouteBuilder {
	@Value("${camel.component.servlet.name}")
	private String camelComponent;

	@Value("${adapter.api.path}")
	private String apiPath;

	@Value("${adapter.api.enableCors}")
	private Boolean apiEnableCors;

	@Value("${adapter.api-docs.path}")
	private String apiDocsPath;

	@Value("${adapter.api-docs.version}")
	private String apiDocsVersion;

	@Value("${adapter.api-docs.title}")
	private String apiDocsTitle;

	@Override
	public void configure() throws Exception {
		// Rest Configuration
		restConfiguration()
        .component(camelComponent)
        .contextPath(apiPath)
        .bindingMode(RestBindingMode.off)
        .enableCORS(apiEnableCors)
            .apiContextPath(apiDocsPath).apiProperty("api.title", apiDocsTitle)
            .apiProperty("api.version", apiDocsVersion).apiProperty("cors", apiEnableCors.toString())
            .apiVendorExtension(true);
	}
}
