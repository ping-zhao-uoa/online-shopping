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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class OnlineShoppingServiceImpl implements OnlineShoppingService {
	public static final Logger logger = Logger.getLogger(OnlineShoppingServiceImpl.class);

	@Override
	public String query(final String stockItems) {
		final String[] items = stockItems.split(",");

		final StringBuffer remainingStock = new StringBuffer(500);
		for (final String item : items) {
			final String stockInfo = "\"" + item + "\":\"" + OnlineShoppingStock.getStock(item) + "\"";
			remainingStock.append(stockInfo).append(",");
		}
		return remainingStock.toString();
	}

	@Override
	public String purchase(final String order) {
		validateOrder(order);
		return processOrder(order);
	}

	// exposed to tests
	protected void validateOrder(final String order) {
		if (StringUtils.isBlank(order) || !order.contains(":")) {
			final String errorMessage = "The purchase order must be of correct format 'itemA:5, itemB:3 ...'";
			logger.warn(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
	}

	// exposed to tests
	protected String processOrder(final String order) {
		final StringBuffer remainingStock = new StringBuffer(500);

		final String[] items = order.split(",");

		// pre-process the request to avoid partial success which will break data integrity
		for (final String item : items) {
			final String[] itemOrder = item.split(":");

			// assume that the value of the current item is 0 when the array length is less then 2
			if (itemOrder.length < 2) {
				continue;
			}

			final Integer itemStock = OnlineShoppingStock.getStock(itemOrder[0]);
			if (Integer.parseInt(itemOrder[1]) > itemStock) {
				final String errorMessage = "The purchase order '" + itemOrder[0]
						+ "' cannot be processed as it exceed the remaining stock '" + itemStock + "'";
				logger.warn(errorMessage);
				throw new IllegalArgumentException(errorMessage);
			}
		}

		for (final String item : items) {
			final String[] itemOrder = item.split(":");

			if (itemOrder.length < 2) {
				continue;
			}

			final String remainingStockOfItem = updateStock(itemOrder[0], Integer.parseInt(itemOrder[1]));

			// concatenate the remaining stock into the returned string
			remainingStock.append(remainingStockOfItem).append(",");
		}

		return remainingStock.toString();
	}

	private String updateStock(final String item, final int quantity) {
		Integer remainingStock = OnlineShoppingStock.getStock(item);
		if (remainingStock == null) {
			return "";
		}

		remainingStock = Integer.valueOf(remainingStock - quantity);
		OnlineShoppingStock.setStock(item, remainingStock);

		return "\"" + item + "\":\"" + remainingStock + "\"";
	}
}
