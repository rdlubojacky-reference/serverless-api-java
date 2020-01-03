package com.luboblog;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.luboblog.dal.User;

public class GetUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger logger = Logger.getLogger(GetUserHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try {
			// get the 'pathParameters' from input
			Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
			String userId = pathParameters.get("id");

			// get the Product by id
			User user = new User().get(userId);

			// send the response back
			if (user != null) {
				return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(user)
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
			} else {
				return ApiGatewayResponse.builder().setStatusCode(404)
						.setObjectBody("User with id: '" + userId + "' not found.")
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
			}
		} catch (Exception ex) {
			logger.error("Error in listing users: " + ex);

			// send the error response back
			Response responseBody = new Response("Error in listing user: ", input);

			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(responseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
		}
	}
}
