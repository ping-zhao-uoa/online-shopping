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

public interface OnlineShoppingService {

	/**
	 * Query the remaining stock according to the provided items.
	 *
	 * @param stockItems based with the query will be done
	 * @return the remaining stock in jSon format
	 */
	public String query(String stockItems);

	/**
	 * Process online purchase order.
	 *
	 * @param order must be of the format 'itemA:quantityA, itemB:quantityB ...'"
	 * @return the remaining stock in jSon format
	 * @throws IllegalArgumentException when the order is null or of incorrect format
	 */
	public String purchase(String order) throws IllegalArgumentException;
}
