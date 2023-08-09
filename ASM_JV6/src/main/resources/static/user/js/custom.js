// Alert Toastify
alertSuccess = function(message) {
	Toastify({
		text: message,
		duration: 3000,
		//destination: "https://github.com/apvarun/toastify-js",
		newWindow: true,
		//close: true,
		gravity: "top", // `top` or `bottom`
		position: "right", // `left`, `center` or `right`
		stopOnFocus: true, // Prevents dismissing of toast on hover
		style: {
			background: "#34c240",
			color: "white",
		},
		onClick: function() { } // Callback after click
	}).showToast();
}

alertWarning = function(message) {
	Toastify({
		text: message,
		duration: 3000,
		//destination: "https://github.com/apvarun/toastify-js",
		newWindow: true,
		//close: true,
		gravity: "top", // `top` or `bottom`
		position: "right", // `left`, `center` or `right`
		stopOnFocus: true, // Prevents dismissing of toast on hover
		style: {
			background: "#fa9f47",
			color: "white",
		},
		onClick: function() { } // Callback after click
	}).showToast();
}

alertDanger = function(message) {
	Toastify({
		text: message,
		duration: 3000,
		//destination: "https://github.com/apvarun/toastify-js",
		newWindow: true,
		//close: true,
		gravity: "top", // `top` or `bottom`
		position: "right", // `left`, `center` or `right`
		stopOnFocus: true, // Prevents dismissing of toast on hover
		style: {
			background: "#d64242",
			color: "white",
		},
		onClick: function() { } // Callback after click
	}).showToast();
}

alertResetAndEdit = function(message) {
	Toastify({
		text: message,
		duration: 3000,
		//destination: "https://github.com/apvarun/toastify-js",
		newWindow: true,
		//close: true,
		gravity: "top", // `top` or `bottom`
		position: "right", // `left`, `center` or `right`
		stopOnFocus: true, // Prevents dismissing of toast on hover
		style: {
			background: "#0090e0",
			color: "white",
		},
		onClick: function() { } // Callback after click
	}).showToast();
}
// End Alert
//Start Delete Address
function deleteAddress(adr, id) {
	$(".address").text(adr);
	$("#deleteAddress").attr("href", "/DTNsBike/update_account.html/delete/" + id);
}
//End Delete Address

//Start Update Address
function updateAddress(adr, id) {
	$('#editAddress').val(adr);
	$("#updateAddress").attr("formaction", "/DTNsBike/update_account.html/update/" + id);
}
function updateAdr() {
	var adr = String($("#editAddress").val());
	if (adr.trim().length == 0) {
		$(".errorsAdr").text("Không bỏ trống ô nhập địa chỉ");
		return false;
	} else {
		return true;
	}
}
//End Update Address
//Start Create Address
function createAdr() {
	var adr = String($("#newAddress").val());
	if (adr.trim().length == 0) {
		$("#errorsAdr").text("Không bỏ trống ô nhập địa chỉ");
		return false;
	} else {
		$("#errorsAdr").text("");
		return true;
	}
}

$(document).ready(function() {

	$('textarea').keypress(function(event) {

		if (event.keyCode == 13) {
			event.preventDefault();
		}
	});
});
//End Create Address

//Angular.js 
var app = angular.module("gemApp", []);
var host = "http://localhost:8080/";
//Upload Avatar
// app.controller("myctr", function ($scope, $http) {
//     var url = "http://localhost:8080/DTNsBike/update_account.html/avatar"
//     $scope.url = function (filename) {
//         return url + '/' + filename;
//     }
//     $scope.list = function () {
//         $http.get(url).then(resp => {
//             $scope.filename = resp.data;
//         }).catch(error => {
//             console.log("Errors", error);
//         });
//     }
//     $scope.upload = function (files) {
//         var form = new FormData();
//         for (var i = 0; i < files.length; i++) {
//             form.append("files", files[i]);
//         }
//         $http.post(url, form, {
//             transformRwquest: angular.identity,
//             headers: { 'Content-Type': undefined }
//         }).then(resp => {
//             $scope.filenames.push(...resp.data);
//         }).catch(error => {
//             console.log("Errors", error);
//         })
//     }
// })
//End Upload Avatar
//filter Replace
app.filter('replace', [function() {

	return function(input, from, to) {

		if (input === undefined) {
			return;
		}

		var regex = new RegExp(from, 'g');
		return input.replace(regex, to);

	};


}]);

//Cart Controller
app.controller("cartCtr", function($scope, $http, $rootScope, $window) {
	$scope.addCart = function(id) {
		var url = `${host}Cart/create/${id}`;
		var urlLogin = "http://" + $window.location.host + "/login.html";
		$http.get(url).then(resp => {
			console.log(resp);
			alertSuccess("Thêm vào giỏ hàng thành công");
			$rootScope.$emit("list", {});
		}
		).catch(error => {
			if (error.status == 500) {
				$window.location.href = urlLogin;
			}
			console.log(error);
		});

	}
	$scope.quantity = 1;
	$scope.addCartQty = function(id) {
		var url = `${host}Cart/create/${id}`
		var urlLogin = "http://" + $window.location.host + "/login.html";
		var data = $scope.Cart;
		$http.post(url, $scope.quantity).then(resp => {
			console.log(resp);
			if (data.status == 200) {
				alert("Thêm vào giỏ hàng thành công");
			}
			$rootScope.$emit("list", {});
		}
		).catch(error => {
			if (error.status == 500) {
				$window.location.href = urlLogin;
			}
			console.log(error);
		});
	}


	//Favorites
	$scope.like = function(id) {
		var urlLike = `http://localhost:8080/favorites/like/${id}`;//${id}
		var urlLogin = "http://" + $window.location.host + "/login.html";
		$http.post(urlLike).then(resp => {
			console.log("Data form server: ", resp);
			alert(resp.data);
		}).catch(err => {
			console.log("Error code: ", err.status);
			if (err.status == 424) {
				alert("Mã sản phẩm không hợp lệ.");
			} else if (err.status == 500) {
				$window.location.href = urlLogin;
			}
		});
	}
})

var itemChecked = [];

app.controller("pushCart", function($scope, $http, $rootScope) {

	$rootScope.$on("list", function() {
		var url = `${host}Cart/listCart`;
		$scope.url = function(filename) {
			return `${host}uploads/productImg/${filename}`;
		}
		$http.get(url).then(resp => {
			$scope.Cart = resp.data;
			if ($scope.Cart.length <= 99) {
				$scope.total = $scope.Cart.length;
			}
			else {
				$scope.total = "99+";
			}
		}).catch(error => {
			console.log("Errors", error);
		})
	});

	var url = `${host}Cart/listCart`;
	$scope.url = function(filename) {
		return `${host}uploads/productImg/${filename}`;
	}
	$http.get(url).then(resp => {
		$scope.Cart = resp.data;
		if ($scope.Cart.length <= 99) {
			$scope.total = $scope.Cart.length;
		}
		else {
			$scope.total = "99+";
		}
		$scope.getTotalTempoary = function() {
			var total = 0;
			for (var i = 0; i < $scope.Cart.length; i++) {
				var cart = $scope.Cart[i];
				total += (cart.price * cart.qty);
			}
			return total;
		}
	}).catch(error => {
		console.log("Errors", error);
	});
	$scope.deleteCart = function(id, name) {
		if (confirm("Bạn có muốn xóa sản phẩm " + name + " khỏi giỏ hàng không ?") == true) {
			var url = `${host}Cart/delete/${id}`
			$http.get(url).then(resp => {
				alert("Xóa sản phẩm " + name + " trong giỏ hàng thành công");
				$rootScope.$emit("list", {});
			}
			).catch(error => {
				console.log(error);
				alert("Lỗi khi xóa sản phẩm " + name + " trong giỏ hàng");
			});
		}

	}
	$scope.updateCart = function(id) {
		var url = `${host}Cart/update`
		var data = $scope.Cart;
		for (var i = 0; i < data.length; i++) {
			if (data[i].id == id) {
				$http.put(url, data[i]).then(
				).catch(error => {
					console.log(error);
					alert("Lỗi khi cập nhật");
				});
				break;
			}
		}

	}

	// $scope.checkedItem = function (listCart) {
	//     console.clear();
	//     itemChecked.push(listCart);
	//     console.log(itemChecked);
	// };

})
//End Cart Controller

// Orders Controller
app.controller("checkoutCtrl", function($scope, $http) {

	$scope.delelteProductFormCart = function() {

	}

	$scope.coupon = {
		code: null
	};

	$scope.payFromCart = [];

	$scope.loadCartProduct = function() {
		var url = `${host}Cart/listCart`;
		$http.get(url).then((resp) => {
			// Access the itemChecked array from SharedService
			$scope.payFromCart = resp.data;
			alertSuccess("Lấy API thành công");
			console.log("Success", $scope.payFromCart);
		}).catch((error) => {
			alertDanger("Lấy API thất bại");
			console.log("Error", error);
		});
	};

	$scope.apply_vourcher_code = function() {
		console.log("apply_vourcher_code")
		var item = angular.copy($scope.coupon);
		console.log($scope.coupon);

		$http.get(`/cart/${item.code}`).then(resp => {
			if (resp.data.id != null) {
				$scope.coupon = {};
				$scope.coupon = resp.data;
				console.log("ADD THÀNH CÔNG: " + $scope.coupon.value)
				alertSuccess("THÊM VOUCHER THÀNH CÔNG.");
				$scope.getDiscount = function() {
					// You can calculate the discount here based on your business logic

					return $scope.coupon.value;
				};
				$scope.getTotal = function(){
					return $scope.getSum();
				}
			}
		}).catch(error => {
			if (error.data.type === 'coupon_code') {
				console.warn("Lổi ở apply")
				alertDanger("THÊM VOUCHER THẤT BẠI.");
			}

		})
	}

	// Function to calculate the subtotal
	$scope.getSubtotal = function() {
		let subtotal = 0;
		for (const listCart of $scope.payFromCart) {
			subtotal += listCart.proCart.price * listCart.qty;
		}
		return subtotal;
	};

	// Function to calculate the discount (assuming discount is 0 for now)
	$scope.getDiscount = function() {
		// You can calculate the discount here based on your business logic
		return 0;
	};

	$scope.getSum = function() {
		var a = ((100 - $scope.getDiscount()) / 100);
		console.log("Giảm: ", a)
		console.warn("Tổng tiền: ", $scope.getSubtotal() * a);
		return $scope.getSubtotal() * a;
	}

	// Function to calculate the total amount
	$scope.getTotal = function() {

		return $scope.getSubtotal() 	;
	};

	$scope.loadCartProduct();

	//alert(this.temp);
});

// End Orders Controller
