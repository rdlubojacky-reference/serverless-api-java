package com.luboblog;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.luboblog.dal.User;

public class ListUsersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger logger = Logger.getLogger(ListUsersHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		try {
			// get all products
			List<User> products = new User().list();

			// send the response back
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(products)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
		} catch (Exception ex) {
			logger.error("Error in listing users: " + ex);

			// send the error response back
			Response responseBody = new Response("Error in listing user: ", input);

			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(responseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
		}
	}
}
