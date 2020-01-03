package com.luboblog;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luboblog.dal.User;

public class CreateUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger logger = Logger.getLogger(CreateUserHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

      try {
          // get the 'body' from input
          JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

		  // create the Product object for post
		  User user = new User();
          // product.setId(body.get("id").asText());
          user.setFirstName(body.get("firstName").asText());
		  user.setLastName(body.get("lastName").asText());
		  user.setCreateDate(DateTime.now().toDate());
          user.save(user);

		  // send the response back
		  return ApiGatewayResponse.builder()
			  .setStatusCode(200)
			  .setObjectBody(user)
			  .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
			  .build();

      } catch (Exception ex) {
          logger.error("Error in saving product: " + ex);

		  // send the error response back
		  Response responseBody = new Response("Error in saving product: ", input);
		  
		  return ApiGatewayResponse.builder()
			  .setStatusCode(500)
			  .setObjectBody(responseBody)
			  .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
			  .build();
      }
	}
}
