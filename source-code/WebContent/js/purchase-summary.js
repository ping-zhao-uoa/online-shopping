angular.module('onlineshopping', [])
  .controller('SummaryController', function() {
		var summary = this;
		summary.items = [
			{item_name: 'itemA', item_quantity: 0},
			{item_name: 'itemB', item_quantity: 0}];
		
		summary.add = function(itemName, itemQuantity) {
			angular.forEach(summary.items, function(item) {
				if (itemName === item.item_name && typeof(itemQuantity) != 'undefined') {
					item.item_quantity = Number(item.item_quantity) + Number(itemQuantity);
				}
			});
		};
		
		summary.total = function(itemName) {
			if (itemName === 'itemA') {
				return summary.items[0].item_quantity;
			} else {
				return summary.items[1].item_quantity;
			}
		};
	});