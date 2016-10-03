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

import java.util.HashMap;
import java.util.Map;

/**
 * Persistence of online shopping stock. Ideally the data should be stored in DB or files.
 */
public class OnlineShoppingStock {

	private static Map<String, Integer> stock = new HashMap<>();

	static {
		stock.put("itemA", 20);
		stock.put("itemB", 10);
		stock.put("itemC", 50);
	}

	/**
	 * Synchronized operation to avoid dirty data caused by concurrency.
	 *
	 * @param item
	 * @param quantity
	 */
	public static synchronized void setStock(final String item, final int quantity) {
		stock.put(item, Integer.valueOf(quantity));
	}

	public static Integer getStock(final String item) {
		return stock.get(item);
	}
}
