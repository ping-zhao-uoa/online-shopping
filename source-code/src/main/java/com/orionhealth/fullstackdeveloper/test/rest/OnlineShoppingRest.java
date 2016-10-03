/*
 * Copyright (c) Ping Zhao (2016 - 2016).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.orionhealth.fullstackdeveloper.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.orionhealth.fullstackdeveloper.test.service.OnlineShoppingService;
import com.orionhealth.fullstackdeveloper.test.service.OnlineShoppingServiceImpl;

@Path("/")
public class OnlineShoppingRest {

	public static final Logger logger = Logger.getLogger(OnlineShoppingRest.class);

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "The Online Shopping Service is runing!";
	}

	@GET
	@Path("/query")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response query(@QueryParam("stockItems") final String stockItems) {

		logger.debug("Request received to query the remaining stock");

		final OnlineShoppingService onlineShoppingService = new OnlineShoppingServiceImpl();
		final String remainingStock = onlineShoppingService.query(stockItems);

		logger.debug("Request processed successfully. The remaining stock is '" + remainingStock + "'");

		final JSONObject jsonResponse = stringToJson(remainingStock);
		final Response response = Response.status(Status.OK).entity(jsonResponse).build();
		return response;
	}

	@GET
	@Path("/purchase")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response purchase(@QueryParam("order") final String order) {

		logger.debug("Request received with order '" + order + "'");

		final OnlineShoppingService onlineShoppingService = new OnlineShoppingServiceImpl();
		String remainingStock = null;
		try {
			remainingStock = onlineShoppingService.purchase(order);
		} catch (final IllegalArgumentException e) {
			logger.warn("Bad request: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		logger.debug("Request processed successfully. The remaining stock is '" + remainingStock + "'");

		final JSONObject response = stringToJson(remainingStock);
		return Response.status(Status.OK).entity(response).build();
	}

	private JSONObject stringToJson(final String response) {
		JSONObject json = null;
		final JSONParser parser = new JSONParser();

		try {
			json = (JSONObject) parser.parse("{" + response + "}");
		} catch (final ParseException e) {
			logger.warn("Fail to parse the response: " + e.getMessage());
		}

		return json;
	}
}
