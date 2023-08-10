// Alert Toastify

alertSuccess = function (message) {
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
        onClick: function () { } // Callback after click
    }).showToast();
}

alertWarning = function (message) {
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
        onClick: function () { } // Callback after click
    }).showToast();
}

alertDanger = function (message) {
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
        onClick: function () { } // Callback after click
    }).showToast();
}

alertResetAndEdit = function (message) {
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
        onClick: function () { } // Callback after click
    }).showToast();
}
// Lắng nghe sự kiện khi click vào ô checkbox trên cùng
function checkAll() {
    var checkboxes = document.querySelectorAll('#cart-tableBody input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = $('#checkAllId').is(':checked');
    }, $('#checkAllId'));
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

$(document).ready(function () {

    $('textarea').keypress(function (event) {

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
app.filter('replace', [function () {

    return function (input, from, to) {

        if (input === undefined) {
            return;
        }

        var regex = new RegExp(from, 'g');
        return input.replace(regex, to);

    };


}]);

//Cart Controller
app.controller("cartCtr", function ($scope, $http, $rootScope, $window) {
    $scope.colorid = "";

    $scope.initializeStockQuantity = function () {
        var init = document.querySelector(".color-link")
        var initialQty = init.getAttribute("data-qty");
        var inittialId = init.getAttribute("data-id");
        var stockQuantitySpan = document.getElementById("stockQuantity");
        stockQuantitySpan.textContent = initialQty;

        var firstLink = document.querySelector(".color-link");
        firstLink.classList.add("active");

        $scope.colorid = inittialId;
    };

    angular.element(document).ready(function () {
        $scope.initializeStockQuantity();
    });

    $scope.updateStockQuantity = function (event) {
        var link = event.currentTarget;
        var clickedQty = link.getAttribute("data-qty");
        var clickedId = link.getAttribute("data-id");
        var stockQuantitySpan = document.getElementById("stockQuantity");
        stockQuantitySpan.textContent = clickedQty;

        var activeLinks = document.querySelectorAll(".color-link.active");
        activeLinks.forEach(function (activeLink) {
            activeLink.classList.remove("active");
        });

        link.classList.add("active");

        $scope.colorid = clickedId;
        console.log($scope.colorid);

    };


    $scope.addCart = function (id) {
        var url = `${host}Cart/create/${id}`;
        var urlLogin = "http://" + $window.location.host + "/signin.html";
        $http.get(url).then(resp => {
            console.log(resp);
            alertSuccess("Thêm vào giỏ hàng thành công");
            $rootScope.$emit("list", {});
        }
        ).catch(error => {
            if (error.status == 500) {
                alertDanger("Vui lòng đăng nhập");
                $window.location.href = urlLogin;
            }
            console.log(error);
        });

    }
    $scope.quantity = 1;
    $scope.addCartQty = function (id) {

        var url = `${host}rest/cart/${id}/${$scope.colorid}`
        var urlLogin = "http://" + $window.location.host + "/signin.html";
        console.log($scope.colorid, $scope.quantity, id);
        $http.post(url, $scope.quantity).then(resp => {
            console.log(resp);
            alertSuccess("Thêm vào giỏ hàng thành công");
            $rootScope.$emit("list", {});
        }
        ).catch(error => {
            alertDanger("Vui lòng đăng nhập");
            console.log(error);
        });
    }


    //Favorites
    $scope.like = function(id){
        alert(id);
		var urlLike = `http://localhost:8080/rest/favorites/like/${id}`;//${id}
		var urlLogin = "http://" + $window.location.host + "/signin.html";
		$http.post(urlLike).then(resp => {
			console.log("Data form server: ",resp);
            alertSuccess("Thêm sản phẩm vào mục yêu thích thành công");
		}).catch(err => {
			console.log("Error code: ", err.status);
			if(err.status == 424){
				alert("Mã sản phẩm không hợp lệ.");
			}else if(err.status == 500){
				$window.location.href = urlLogin;
			}
		});
	}

})

var itemChecked = [];
function checkAll() {
    var checkboxes = document.querySelectorAll('#cart-tableBody input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = $('#checkAllId').is(':checked');
    }, $('#checkAllId'));
}
function checkAll() {
    var checkboxes = document.querySelectorAll('#cart-tableBody input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = $('#checkAllId').is(':checked');
    }, $('#checkAllId'));
}
app.controller("pushCart", function ($scope, $http, $rootScope) {

    $rootScope.$on("list", function () {
        var url = `${host}Cart/listCart`;
        $scope.url = function (filename) {
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

    $scope.selectedIDs = [];
    // $scope.selectAll = false;
    $scope.selectAll = false;

    $scope.checkAll = function () {
        $scope.selectAll = !$scope.selectAll;
        $scope.selectedIDs = []; // Xóa tất cả các ID đã chọn trước đó
        for (var i = 0; i < $scope.Cart.length; i++) {
            $scope.Cart[i].isSelected = $scope.selectAll;
            if ($scope.selectAll) {
                $scope.selectedIDs.push($scope.Cart[i]); // Thêm ID vào mảng selectedIDs
            }
        }


        $scope.calculateTotalAmount();
    };

    $scope.calculateTotalAmount = function () {
        var total = 0;

        for (var i = 0; i < $scope.Cart.length; i++) {
            if ($scope.Cart[i].isSelected) {
                total += $scope.Cart[i].proCart.price * $scope.Cart[i].qty;
            }
        }

        $scope.totalAmount = total;
    };




    $scope.updateSelectedIDs = function (listCart) {
        if (listCart.isSelected) {
            $scope.selectedIDs.push(listCart);
        } else {
            var index = $scope.selectedIDs.indexOf(listCart);
            if (index !== -1) {
                $scope.selectedIDs.splice(index, 1);
            }
        }
        console.log($scope.selectedIDs);
    };

    var url = `${host}Cart/listCart`;
    $scope.url = function (filename) {
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
        $scope.getTotalTempoary = function () {
            var total = 0;

            if ($scope.selectAll) {
                for (var i = 0; i < $scope.Cart.length; i++) {
                    total += $scope.Cart[i].proCart.price * $scope.Cart[i].qty;
                }
            } else {
                var temparr = angular.copy($scope.selectedIDs);
                for (var i = 0; i < temparr.length; i++) {
                    var cart = temparr[i];
                    total += cart.price * cart.qty;
                }
            }

            return total;
        };

    }).catch(error => {
        console.log("Errors", error);
    });
    $scope.deleteCart = function (id, name) {
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
    $scope.updateCart = function (id) {
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

    $scope.loadCheckoutList = function () {
        var url = `${host}rest/cart/savetemplist`;
        var data = angular.copy($scope.selectedIDs)

        $http.post(url, data).then(resp => {
            console.log(resp.data);
        }
        ).catch(error => {
            console.log(error);
        });
    }

    // $scope.checkedItem = function (listCart) {
    //     console.clear();
    //     itemChecked.push(listCart);
    //     console.log(itemChecked);
    // };

})
//End Cart Controller

// Orders Controller
app.controller("checkoutCtrl", function ($scope, $http) {

    $scope.currentUser = {};
    $scope.addrList = [];
    $scope.addrSelected = "";

    $scope.getCurrentUser = function () {
        var url = `${host}rest/accounts/current`;
        var url2 = `${host}rest/address/`;
        $http.get(url).then((resp) => {
            $scope.currentUser = resp.data;
            $http.get(url2 + $scope.currentUser.username).then((resp) => {
                $scope.addrList = resp.data;
                console.log("Success o", $scope.currentUser);
            }).catch((error) => {
                console.log("Error", error);
            });

        }).catch((error) => {
            console.log("Error", error);
        });
    }

    $scope.checkout = function () {
        alertSuccess("Thanh toán thành công");
    }
    $scope.delelteProductFormCart = function () {

    }
    $scope.currentUser = {};
    $scope.selectedAddress = ''; 
    $scope.getCurrentUser = function () {
        var url = `${host}rest/accounts/current`;
        var url2 = `${host}rest/address/`;
        $http.get(url).then((resp) => {
            $scope.currentUser = resp.data;
            $http.get(url2 + $scope.currentUser.username).then((resp) => {
                $scope.currentUser.address = resp.data;
                
                console.log("Success o", resp.data);
            }).catch((error) => {
                console.log("Error", error);
            });

        }).catch((error) => {
            console.log("Error", error);
        });
    }
    $scope.payFromCart = [];

    $scope.loadCartProduct = function () {
        var url = `${host}rest/cart/gettemplist`;
        $http.get(url).then((resp) => {
            $scope.payFromCart = resp.data;
            //xử lý nếu resp.data rỗng thì thay bằng toàn bộ card
            //...
            //
            alertSuccess("Lấy API thành công");
            console.log("Success", $scope.payFromCart);
        }).catch((error) => {
            alertDanger("Lấy API thất bại");
            console.log("Error", error);
        });
    };

    $scope.voucher = {};

    $scope.apply_vourcher_code = function () {
        var url = `${host}rest/sales/checkcode/`;
        var item = angular.copy($scope.voucher);
        console.log($scope.voucher);
        $http.get(url + item.code).then(resp => {
            $scope.voucher = resp.data;
            console.log($scope.voucher);
            $scope.getDiscount = function () {
                var sub = $scope.getSubtotal();
                return $scope.voucher.value * sub / 100;
            };
        }).catch(error => {
            console.log("Error", error)

        })
    }

    $scope.handleCheckout = function () {
        var url = `${host}rest/order/neworder`;
        var data = angular.copy($scope.currentUser);
        data.voucher = angular.copy($scope.voucher.id);
        if (data.voucher == undefined) {
            data.voucher = 0;
        }
        data.list = angular.copy($scope.payFromCart);
        data.address = angular.copy($scope.addrSelected);
        if (!data.address) {
            data.address = data.newAddress;
            if(!data.address){
                data.address = null;
            }
        }
        console.log(data)
        $http.post(url, data).then(resp => {
            alertSuccess("Thanh toán thành công")
            console.log(resp.data);
        }).catch(error => {
            console.log(error);
        });

    }

    // Function to calculate the subtotal
    $scope.getSubtotal = function () {
        let subtotal = 0;
        for (const listCart of $scope.payFromCart) {
            subtotal += listCart.proCart.price * listCart.qty;
        }
        return subtotal;
    };


    $scope.getDiscount = function () {
        return 0;
    };

    // Function to calculate the total amount
    $scope.getTotal = function () {
        return $scope.getSubtotal() - $scope.getDiscount();
    };

    $scope.getCurrentUser();
    $scope.loadCartProduct();

    //alert(this.temp);
});
// End Orders Controller

//User update image
app.controller("userImage", function ($scope, $http) {
    var url = "http://localhost:8080/rest/uploads/accountImg";
    $scope.upload = function (files) {
        var form = new FormData();
        form.append('files', files[0]);
        $http.post(url, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            document.querySelector('input[type=hidden][name=photo]').value = resp.data.name;
            updatePreviewImage(resp.data.name);
            console.log("Success", resp.data);
        }).catch(error => {
            console.log("Errors", error);
        })
    }

    function updatePreviewImage(fileName) {
        var previewImage = document.getElementById('previewImage');
        previewImage.src = '/uploads/accountImg/' + fileName;
    }

});

// start product favorites controller
app.controller("favoritesCtrl", function ($scope, $http,$rootScope, $window) {
    
    $scope.favoriteProductsList = [];

    $scope.loadFavoriteProducts = function() {
        var url = `${host}rest/favorites/like/all`;
        $http.get(url).then((resp) => {
            $scope.favoriteProductsList = resp.data;
            alertSuccess("Lấy tất cả sản phẩm yêu thích thành công");
            console.log("Success", $scope.favoriteProductsList);
        }).catch((error) => {
            alertDanger("Lấy tất cả sản phẩm yêu thích thất bại");
            console.log("Error", error);
        });
    }

    $scope.unLikeFavoriteProducts = function(id) {

        var url = `${host}rest/favorites/unLike/${id}`;
        
        $http.delete(url).then( resp => {
            alertSuccess("Bỏ thích sản phẩm thành công, Favorite Id: " + id);
            console.log("Success", resp.data);
        }).catch((error) => {
            alertDanger("Bỏ thích sản phẩm thất bại");
            console.log("Error", error);
        });
    }

    $scope.addCart = function (id) {
        var url = `${host}Cart/create/${id}`;
        var urlLogin = "http://" + $window.location.host + "/login.html";
        $http.get(url).then(resp => {
			console.log(resp);
            alertSuccess("Thêm vào giỏ hàng thành công");
            $rootScope.$emit("list", {});
        }
        ).catch(error => {
            if(error.status == 500){
				$window.location.href = urlLogin;
			}
		    console.log(error);
        });
        
    }

    $scope.loadFavoriteProducts();

});
// end product favorites controller