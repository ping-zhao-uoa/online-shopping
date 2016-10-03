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
$(document).ready(function() {
	
	queryStock();
	
	$("#place-order #buy-button").entwine({
		onclick : function() {
			purchase();
		}
	});
	
	function queryStock() {
		var path = 'http://localhost:28080/onlineshopping/rest/query?stockItems=itemA,itemB';
		ajaxCall(path);
	}
	
	function purchase() {
		var itemA = $("#place-order #itemA").val().trim();
		var itemB = $("#place-order #itemB").val().trim();
		
		var path = 'http://localhost:28080/onlineshopping/rest/purchase?order=itemA:' + itemA + ',itemB:' + itemB;
		var result = ajaxCall(path);

		// update summary read
		if (result) {
			updateSummary(itemA, itemB);
		}
	}
	
	function ajaxCall(path) {
		console.log("Ajax call to: " + path);
		var result = false;
		
		$.ajax({
			method : "GET",
			url : path,
			async: false
		}).done(function(response) {
			console.log("Received response: " + response);
			
			// update stock read
			var itemA_stock = $("#stock #itemA");
			var itemB_stock = $("#stock #itemB");
			itemA_stock.text(response.itemA);
			itemB_stock.text(response.itemB);
			
			result = true;
			
		}).fail(function(response) {
			var errorMessage = "Request failed. Status: " + response.status + ", Response text: " + response.responseText;
			console.log(errorMessage);
			alert(errorMessage);
		});
		
		return result;
	}
	
	// call the AngularJS controller to update view
	function updateSummary(itemA, itemB) {
		angular.element(document.querySelector('#purchase-summary .content')).controller().add('itemA', itemA);
		angular.element(document.querySelector('#purchase-summary .content')).controller().add('itemB', itemB);

		var totalA = angular.element(document.querySelector('#purchase-summary .content')).controller().total('itemA');
		var totalB = angular.element(document.querySelector('#purchase-summary .content')).controller().total('itemB');
		
		var itemA = $("#purchase-summary #itemA").text(totalA);
		var itemA = $("#purchase-summary #itemB").text(totalB);
	}
});