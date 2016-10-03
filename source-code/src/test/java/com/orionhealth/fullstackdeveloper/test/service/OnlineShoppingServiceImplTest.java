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
package com.orionhealth.fullstackdeveloper.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OnlineShoppingServiceImplTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void validateOrder_throwIllegalArgumentException_whenOrderIsBlank() {
		// Given
		final OnlineShoppingServiceImpl onlineShoppingService = new OnlineShoppingServiceImpl();

		// Then
		this.expectedException.expect(IllegalArgumentException.class);
		this.expectedException.expectMessage("The purchase order must be of correct format 'itemA:5, itemB:3 ...'");

		// When
		onlineShoppingService.validateOrder("");
	}

	@Test
	public void validateOrder_throwIllegalArgumentException_whenOrderIsOfIncorrectFormat() {
		// Given
		final OnlineShoppingServiceImpl onlineShoppingService = new OnlineShoppingServiceImpl();

		// Then
		this.expectedException.expect(IllegalArgumentException.class);
		this.expectedException.expectMessage("The purchase order must be of correct format 'itemA:5, itemB:3 ...'");

		// When
		onlineShoppingService.validateOrder("itemA-3");
	}

	@Test
	public void validateOrder_returnTrue_whenOrderisValid() {
		// Given
		final OnlineShoppingServiceImpl onlineShoppingService = new OnlineShoppingServiceImpl();

		// When
		onlineShoppingService.validateOrder("itemD:8");

		// Then
	}

	@Test
	public void processOrder_throwIllegalArgumentException_whenOrderExceedsRemainingStock() {
		// Given
		final OnlineShoppingServiceImpl onlineShoppingService = new OnlineShoppingServiceImpl();

		// Then
		this.expectedException.expect(IllegalArgumentException.class);
		this.expectedException.expectMessage("The purchase order 'itemA' cannot be processed as it exceed the remaining stock '20'");

		// When
		onlineShoppingService.processOrder("itemA:22");
	}

	@Test
	public void processOrder_returnCorrectValue_whenOrderIsValid() {
		// Given
		final OnlineShoppingServiceImpl onlineShoppingService = new OnlineShoppingServiceImpl();

		// When
		final String result = onlineShoppingService.processOrder("itemA:8,itemB:5");

		// Then
		assertThat(result).isEqualTo("\"itemA\":\"12\",\"itemB\":\"5\",");
	}

}
