package com.simple.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.lang.Long.parseLong;


@Component
public class MockProcessor implements Processor {
	private static final Logger logger = LoggerFactory.getLogger(MockProcessor.class);
	String value;
	Double balance;
	    
	public MockProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {

		  String jsonInput = exchange.getIn().getBody(String.class);
		  exchange.getIn().setHeader("accept", "application/json");
		  logger.info("Received Request <<< " + jsonInput);
		 // exchange.getOut().setBody(jsonInput);

	      exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");

		  String jsonString = jsonInput.toString();
		  DocumentContext docCtx = JsonPath.parse(jsonString);
		  JsonPath jsonPath = JsonPath.compile("$.requestPayload.primaryData.businessKey");
		  value=docCtx.read(jsonPath);
		  System.out.println(value);

		if (parseLong(value) == 1250585001){
			balance = 100000.00;
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			String response = getInfoSuccessResponse().toString();
			logger.info("Response sent: " + response);
			exchange.getOut().setBody(response);
		} else if (parseLong(value) == 1250585002) {
			balance = 5000.00;
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			String response = getInfoSuccessResponse().toString();
			logger.info("Response sent: " + response);
			exchange.getOut().setBody(response);
		}
		else if (parseLong(value) == 1250585003) {
			balance = 9000.00;
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			String response = getInfoSuccessResponse().toString();
			logger.info("Response sent: " + response);
			exchange.getOut().setBody(response);
		} else{
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			String response = getInfoErrorResponse().toString();
			logger.info("Response sent: " + response);
			exchange.getOut().setBody(response);
		}
	} 

	private StringBuilder getInfoSuccessResponse(){
		String jsonSuccessResponse = "{\" header\":{"+
				" \"messageID\": \"AD-1234\"," +
				" \"conversationID\": \"50697c9c-e129-4071-b323-a6640296fee1\"," +
				" \"targetSystemID\": \"not available\"," +
				" \"channelCode\": \"101\"," +
				" \"channelName\": \"atm\"," +
				" \"routeCode\": \"001\"," +
				" \"routeName\": \"t24\"," +
				" \"statusCode\": \"00\"," +
				" \"statusDesc\": \"Success\"," +
				" \"ehfInfo\": { " +
					"\"item\": [" +
						"{"+
						" \"ehfRef\": \"OSP-1002\"," +
						" \"ehfDesc\": \"Processed Successfully\"" +
						"}" +
					"]"+
				"}" +
				"}," +
				"\"responsePayload\": {" +
					"\"primaryData\": {" +
						" \"businessKey\": \""+value+"\" , "+
						" \"businessKeyType\": \"AccountNumber\"," +
					"}," +
					"\"accountDetails\": {" +
						" \"accountNumber\": \""+value+"\" , " +
						" \"currency\": \"RWF\"," +
						" \"category\": \"1001\"," +
						" \"description\": \"Current Account  Bundled\"," +
						" \"accountStatus\": \"\"," +
						" \"onlineActualBalance\": \""+balance+"\" , " +
						" \"availableBalance\": \""+balance+"\" , " +
						" \"companyId\": \"RW9990001\"," +
						" \"companyName\": \"BPR BANK BNK DEV\"," +
						" \"localCurrency\": \"RWF\"," +
						" \"localCountryCode\": \"RW\"," +
						" \"groupCondition\": \"4403\"," +
						" \"accountCompanyCode\": \"RW9990001\"," +
						" \"accountName\": \"JOHN\"," +
						"\"postingRestrictionDetails\": {" +
							" \"code\": \"\"," +
							" \"type\": \"\"," +
							" \"description\": \"\"," +
						"}" +
					"}" +
				"}" +
				"}";
		return new StringBuilder(jsonSuccessResponse);
	}

	private StringBuilder getInfoErrorResponse() {
		String jsonErrorResponse = "{\" header\":{"+
				" \"messageID\": \"AD-12345\"," +
				" \"conversationID\": \"50697c9c-e129-4071-b323-a6640296fee1\"," +
				" \"targetSystemID\": \"not available\"," +
				" \"channelCode\": \"101\"," +
				" \"channelName\": \"atm\"," +
				" \"routeCode\": \"001\"," +
				" \"routeName\": \"t24\"," +
				" \"statusCode\": \"06\"," +
				" \"statusDesc\": \"Error\"," +
				" \"ehfInfo\": { " +
					"\"item\": [" +
						"{"+
							" \"ehfRef\": \"OSP-1006\"," +
							" \"ehfDesc\": \"Account Invalid\"" +
						"}" +
					"]"+
				"}" +
				"}," +
				"\"responsePayload\": {" +
					"\"primaryData\": {" +
						" \"businessKey\": \""+value+"\" , " +
						" \"businessKeyType\": \"AccountNumber\"," +
					"}," +
				"}," +
				"}";
		return new StringBuilder(jsonErrorResponse);
	}
}