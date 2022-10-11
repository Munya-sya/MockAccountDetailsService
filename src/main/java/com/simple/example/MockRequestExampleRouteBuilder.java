package com.simple.example;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 
 * 
 * @author Roy Chela| Bring Global
 * @version 1.0.0
 * @since November, 2020
 */
@Component
public class MockRequestExampleRouteBuilder extends RouteBuilder {
	
	
	@Autowired
	private MockProcessor mockProcessor;
	
	@Override
	public void configure() throws Exception {
		
		// Rest Endpoints
		 rest("/MockMicroserviceExampleService")
         .post("/services")
                 .description("Example Post REST Service")
                // .consumes(XML.getContentType())
                // .produces(XML.getContentType())
                 .consumes("application/json")
                 .produces("application/XML")
                 .to("direct:dispatchPost");
                // .to("undertow:http4://localhost:8080/api/AdapterMicroserviceExampleService/services");
 
		 /* Routes Configuration */
		 from("direct:dispatchPost").routeId("request.dispatchPost")
		 .removeHeaders("Camel*")
		 .process(mockProcessor);
		
	
	}
}
