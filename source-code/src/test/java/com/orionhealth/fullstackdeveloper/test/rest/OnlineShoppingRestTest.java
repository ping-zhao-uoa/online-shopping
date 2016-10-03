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

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class OnlineShoppingRestTest {

	@Test
	public void onlineShoppingRest_returnBadRequest_whenExceptionIsInvalid() {
		// Given
		final OnlineShoppingRest onlineShoppingRest = new OnlineShoppingRest();

		// When
		final Response response = onlineShoppingRest.purchase("");

		// Then
		assertThat(response.getStatus()).isEqualTo(400);
		assertThat(response.getEntity()).isEqualTo("The purchase order must be of correct format 'itemA:5, itemB:3 ...'");
	}

	@Test
	public void onlineShoppingRest_returnCorrectly_whenExceptionIsValid() {
		// Given
		final OnlineShoppingRest onlineShoppingRest = new OnlineShoppingRest();

		// When
		final Response response = onlineShoppingRest.purchase("itemC:20");

		// Then
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getEntity().toString()).isEqualTo("{\"itemC\":\"30\"}");
	}
}
