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
      onClick: function(){} // Callback after click
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
      onClick: function(){} // Callback after click
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
      onClick: function(){} // Callback after click
    }).showToast();
  }
// End Alert
//Start Delete Address
function deleteAddress(adr, id) {
    $(".address").text(adr);
    $("#deleteAddress").attr("href", "/update_account.html/delete/" + id);
}
//End Delete Address

//Start Update Address
function updateAddress(adr, id) {
    $('#editAddress').val(adr);
    $("#updateAddress").attr("formaction", "/update_account.html/update/" + id);
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
app.controller("updateAddressCtrl", function ($scope, $http, $timeout, $rootScope) {
    function refesh() {
        $http.get(`${host}update_profile/address`).then(resp => {
            $scope.addressList = resp.data;
            console.log("DẤTs", resp.data);
        }).catch(error => {
            console.log("Error", error);
        });
        $http.get('https://provinces.open-api.vn/api/p/').then(resp => {
            $scope.provinceList = resp.data;
            $timeout(function () {
                $('.selectpicker').selectpicker('refresh');
            }, 1)
        }).catch(error => {
            console.log("Error", error);
        })
        $scope.add = true;
        $scope.update = false;
        $scope.address = {};
    }
    refesh();
    $scope.getdistrict = function () {
        if ($scope.selectProvince != undefined) {
            $http.get(`https://provinces.open-api.vn/api/p/${$scope.selectProvince}?depth=2`).then(resp => {
                $scope.districtList = resp.data.districts;
                $scope.wardList = null;
                $timeout(function () {
                    $('.selectpicker').selectpicker('refresh');
                }, 1)
                console.log("HUYỆN", resp.data.districts);
            }).catch(error => {
                console.log("Error", error);
            });
        }
    }
    $scope.getward = function () {
        if ($scope.selectDistrict != undefined) {
            $http.get(`https://provinces.open-api.vn/api/d/${$scope.selectDistrict}?depth=2`).then(resp => {
                $scope.wardList = resp.data.wards;
                $timeout(function () {
                    $('.selectpicker').selectpicker('refresh');
                }, 1)
                console.log("XÃ PHƯỜNG", resp.data.wards);
            }).catch(error => {
                console.log("Error", error);
            });
        }

    }
    $scope.refeshList = function () {
        $timeout(function () {
            $('.selectpicker').selectpicker('refresh');
            console.log("Selected Ward:", $scope.selectWard);
        }, 1)
    }

    $scope.addAddress = function () {
        var loi = 0;

        if ($scope.selectProvince == undefined) {
            $scope.reqProvince = true;
            loi++;
        }
        if ($scope.selectDistrict == undefined) {
            $scope.reqDistrict = true;
            loi++;
        }
        if ($scope.selectWard == undefined) {
            $scope.reqWard = true;
            loi++;
        }
        if ($scope.address.adr == null || $scope.address.adr == '') {
            $scope.reqAdr = true;
            loi++;
        }

        if ($scope.af.$valid == true && loi == 0) {
            var item = angular.copy($scope.address);
            for (var i = 0; i < $scope.provinceList.length; i++) {
                if ($scope.provinceList[i].code == $scope.selectProvince) {
                    $scope.selectProvince = $scope.provinceList[i].name;
                    break;
                }
            }
            for (var i = 0; i < $scope.districtList.length; i++) {
                if ($scope.districtList[i].code == $scope.selectDistrict) {
                    $scope.selectDistrict = $scope.districtList[i].name;
                    break;
                }
            }
            for (var i = 0; i < $scope.wardList.length; i++) {
                if ($scope.wardList[i].code == $scope.selectWard) {
                    $scope.selectWard = $scope.wardList[i].name;
                    break;
                }
            }
            console.log("Item before modifications:", item);

            // Gán giá trị cho trường address trong item
            item.address = $scope.address.adr + ", " + $scope.selectWard + ", " + $scope.selectDistrict + ", " + $scope.selectProvince;
            // item.adr = $scope.address.adr + ", " + $scope.selectWard + ", " + $scope.selectDistrict + ", " + $scope.selectProvince;
            var url = `${host}update_profile/address`;
            $http.post(url, item).then(resp => {
                $('#addressModal').modal('hide');
                $scope.address = {};
                console.log("Address creation successful:", resp);
                refesh();
                $('#message').modal('show');
                $('#message>.modal-dialog>.modal-content>.modal-body').html("<i class='far fa-check-circle text-success mb-1' style='font-size: 60px;'></i><p>Thêm địa chỉ thành công</p>");
            }).catch(error => {

                console.log("Lỗi", error)
            })
            setTimeout(function () {
                $('#message').modal('hide');
            }, 2000);
        }
    }
    $scope.updateModel = function(i) {
		$scope.add = false;
		$scope.update = true;
		$scope.address = {};
		$scope.updateForm = $scope.addressList[i];
		var adr = String($scope.updateForm.address);
		var province = adr.substring(adr.lastIndexOf(","), adr.length);
		adr = adr.substring(0, adr.lastIndexOf(","));
		var district = adr.substring(adr.lastIndexOf(","), adr.length);
		adr = adr.substring(0, adr.lastIndexOf(","));
		var ward = adr.substring(adr.lastIndexOf(","), adr.length);
		adr = adr.substring(0, adr.lastIndexOf(","));
		
		$scope.address.adr = adr;
		$(".loading-gem").removeClass("sr-only");
		$('#adrManager').modal('hide');
		$('#addressModal').modal('show');
		for (var i = 0; i < $scope.provinceList.length; i++) {
			if ($scope.provinceList[i].name == province.replace(", ", "")) {
				$scope.selectProvince = $scope.provinceList[i].code;
				$timeout(function() {
					$('.selectpicker').selectpicker('refresh');
				}, 1);
				$http.get(`https://provinces.open-api.vn/api/p/${$scope.provinceList[i].code}?depth=2`).then(resp => {
					$scope.districtList = resp.data.districts;
					$timeout(function() {
						$('.selectpicker').selectpicker('refresh');
					}, 1)
					for (var i = 0; i < $scope.districtList.length; i++) {
						if ($scope.districtList[i].name == district.replace(", ", "")) {
							$scope.selectDistrict = $scope.districtList[i].code;
							$timeout(function() {
								$('.selectpicker').selectpicker('refresh');
							}, 1);
							$http.get(`https://provinces.open-api.vn/api/d/${$scope.districtList[i].code}?depth=2`).then(resp => {
								$scope.wardList = resp.data.wards;
								$timeout(function() {
									$('.selectpicker').selectpicker('refresh');
								}, 1)
								for (var i = 0; i < $scope.wardList.length; i++) {
									if ($scope.wardList[i].name == ward.replace(", ", "")) {
										$scope.selectWard = $scope.wardList[i].code;
										$timeout(function() {
											$('.selectpicker').selectpicker('refresh');
											$(".loading-gem").addClass("sr-only");
										}, 1);
										break;
									}
								}
							})
							break;
						}
					}
				})
				break;
			}
		}


	}

	$scope.updateAddress = function() {
		if ($scope.updateForm != undefined || $scope.updateForm != null) {
			var loi = 0;
			
			if ($scope.selectProvince == undefined) {
				$scope.reqProvince = true;
				loi++;
			}
			if ($scope.selectDistrict == undefined) {
				$scope.reqDistrict = true;
				loi++;
			}
			if ($scope.selectWard == undefined) {
				$scope.reqWard = true;
				loi++;
			}
			if ($scope.address.adr == null || $scope.address.adr == '') {
				$scope.reqAdr = true;
				loi++;
			}
			if ($scope.af.$valid == true && loi == 0) {
				var item = angular.copy($scope.address);
				for (var i = 0; i < $scope.provinceList.length; i++) {
					if ($scope.provinceList[i].code == $scope.selectProvince) {
						$scope.selectProvince = $scope.provinceList[i].name;
						break;
					}
				}
				for (var i = 0; i < $scope.districtList.length; i++) {
					if ($scope.districtList[i].code == $scope.selectDistrict) {
						$scope.selectDistrict = $scope.districtList[i].name;
						break;
					}
				}
				for (var i = 0; i < $scope.wardList.length; i++) {
					if ($scope.wardList[i].code == $scope.selectWard) {
						$scope.selectWard = $scope.wardList[i].name;
						break;
					}
				}
                item.address = $scope.address.adr + ", " + $scope.selectWard + ", " + $scope.selectDistrict + ", " + $scope.selectProvince;
				$http.put(`${host}update_profile/address?id=${$scope.updateForm.id}`, item).then(resp => {
					$('#addressModal').modal('hide');
					$scope.address = {};
					refesh();
					$('#message').modal('show');
					$('#message>.modal-dialog>.modal-content>.modal-body').html("<i class='far fa-check-circle text-success mb-1' style='font-size: 60px;'></i><p>Cập nhật địa chỉ thành công</p>");
				}).catch(error => {
					$('#addressModal').modal('hide');
					$('#message').modal('show');
					$('#message>.modal-dialog>.modal-content>.modal-body').html("<i class='far fa-times-circle text-danger mb-1' style='font-size: 60px;'></i>");
					// var loi = error.data.message;
					if (error.status == 302) {
						$('#message>.modal-dialog>.modal-content>.modal-body').append("<p>" + loi + "</p>");
					} else {
						$('#message>.modal-dialog>.modal-content>.modal-body').append("<p>Thêm thất bại! Do một số lỗi dữ liệu</p>");
					}
				})
				setTimeout(function() {
					$('#message').modal('hide');
				}, 2000);
			}
		}
	}
    $scope.confirmRemove = function (i) {
        if ($scope.addressList[i] != undefined) {
            $('#addressDel').modal('show');
            $scope.delTarget = $scope.addressList[i];
        } else {
            $('#message').modal('show');
            $('#message>.modal-dialog>.modal-content>.modal-body').html("<i class='far fa-times-circle text-danger mb-1' style='font-size: 60px;'></i><p>Lỗi khi xóa</p>");
            setTimeout(function () {
                $('#message').modal('hide');
            }, 2000);
        }
    }
    $scope.delAddress = function () {
        if ($scope.delTarget != undefined) {
            var id = $scope.delTarget.id;
            $http.delete(`${host}update_profile/deleteAddress/${id}`).then(resp => {
                $('#addressDel').modal('hide');
                console.log("Xóa Thành Công")
                refesh();
            }
            ).catch(error => {
                $('#cartDel').modal('hide');
                $('#message').modal('show');
                $('#message>.modal-dialog>.modal-content>.modal-body').html("<i class='far fa-times-circle text-danger mb-1' style='font-size: 60px;'></i><p>Lỗi khi xóa</p>");
                setTimeout(function () {
                    $('#message').modal('hide');
                }, 2000);
            });
        }
    }
})
//End Upload Avatar
//filter Replace
app.filter('replace', [function() {

    return function (input, from, to) {
      
      if(input === undefined) {
        return;
      }
  
      var regex = new RegExp(from, 'g');
      return input.replace(regex, to);
       
    };


}]);


//Cart Controller
app.controller("cartCtr", function ($scope, $http,$rootScope, $window) {
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
    $scope.quantity=1;
    $scope.addCartQty = function (id) {
    	 var url = `${host}Cart/create/${id}`
         var urlLogin = "http://" + $window.location.host + "/login.html";
         var data = $scope.Cart;
		 $http.post(url,$scope.quantity).then(resp => {
				console.log(resp);
				if(data.status==200){
					alert("Thêm vào giỏ hàng thành công");
				}
		        $rootScope.$emit("list", {});
		    }
		).catch(error => {
			if(error.status == 500){
				$window.location.href = urlLogin;
			}
		    console.log(error);
		});
    }
    
    
    //Favorites
    $scope.like = function(id){
		var urlLike = `http://localhost:8080/favorites/like/${id}`;//${id}
		var urlLogin = "http://" + $window.location.host + "/login.html";
		$http.post(urlLike).then(resp => {
			console.log("Data form server: ",resp);
			alert(resp.data);
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
app.controller("pushCart", function($scope, $http, $rootScope) {

app.controller("pushCart", function ($scope, $http, $rootScope) {

    $rootScope.$on("list", function(){
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
        $scope.getTotalTempoary=function(){
            var total =0;
            for(var i = 0; i < $scope.Cart.length; i++){
                var cart = $scope.Cart[i];
                total += (cart.price * cart.qty);
            }
            return total;
        }
    }).catch(error => {
        console.log("Errors", error);
    });
    $scope.deleteCart = function (id,name) {
        if(confirm("Bạn có muốn xóa sản phẩm "+name+" khỏi giỏ hàng không ?") == true){
        var url = `${host}Cart/delete/${id}`
        $http.get(url).then(resp => {
            alert("Xóa sản phẩm "+name+" trong giỏ hàng thành công");
            $rootScope.$emit("list", {});
        }
        ).catch(error => {
            console.log(error);
            alert("Lỗi khi xóa sản phẩm "+name+" trong giỏ hàng");
        });}
        
    }
    $scope.updateCart= function (id) {
        var url = `${host}Cart/update`
        var data = $scope.Cart;
        for(var i =0 ;i < data.length; i++){
            if(data[i].id == id){
             $http.put(url,data[i]).then(
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
app.controller("checkoutCtrl", function ($scope, $http, $rootScope) {

	$scope.currentUser = {};
	$scope.differentAddress = false;
	$scope.addressList = [];


	$scope.autocompleteItems = $rootScope.vouchercopi; // Dữ liệu từ $rootScope
	$scope.autocompleteVisible = false; // Ban đầu ẩn danh sách autocomplete

	$scope.showAutocomplete = function () {
		$scope.autocompleteVisible = true; // Hiển thị danh sách autocomplete
	};

	$scope.selectAutocomplete = function (item) {
		$scope.voucher.code = item; // Gán giá trị từ danh sách autocomplete vào input
		$scope.autocompleteVisible = false; // Ẩn danh sách autocomplete
	};


    $scope.delelteProductFormCart = function(){

    }

	$scope.payFromCart = [];

    $scope.loadCartProduct = function () {
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
app.controller("favoritesCtrl", function($scope, $http, $rootScope, $window) {

	$scope.favoriteProductsList = [];

	$scope.loadFavoriteProducts = function () {
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

	$scope.unLikeFavoriteProducts = function (id) {

		var url = `${host}rest/favorites/like/all/${id}`;

		$http.delete(url).then(resp => {
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
			if (error.status == 500) {
				$window.location.href = urlLogin;
			}
			console.log(error);
		});

	}

	$scope.loadFavoriteProducts();

});
// end product favorites controller

app.controller("registerCtrl", function ($scope, $http,$rootScope, $window) {

    let otpChecked = "";

    $scope.generateOTP = function (){

        const otpLength = 6;
        const otpChars = '0123456789';
        let otp = '';

        for (let i = 0; i < otpLength; i++) {
            const randomIndex = Math.floor(Math.random() * otpChars.length);
            otp += otpChars[randomIndex];
        }
        otpChecked = otp;

        //const otpResultElement = document.getElementById('otpResult');
        //otpResultElement.textContent = otp;

        $scope.startExpiryCountdown(otp);
    }

    $scope.verifyOTP = function () {
        const enteredOTP = document.getElementById('inputOTP').value;
        const generatedOTP = otpChecked;

        const resultElement = document.getElementById('verifyResult');
        const continueButton = document.querySelector('.return-customer-btn'); // Chọn nút "Tiếp tục"

        if (enteredOTP === generatedOTP) {
            resultElement.textContent = 'Mã OTP hợp lệ';
            resultElement.style.color = 'green';
            continueButton.hidden = false; // Cho phép nút "Tiếp tục" được nhấn
        } else {
            resultElement.textContent = 'Mã OTP không hợp lệ';
            resultElement.style.color = 'red';
            continueButton.hidden = true; // Vô hiệu hóa nút "Tiếp tục"
        }
    }

    $scope.startExpiryCountdown = function (otp) {
        const countdownElement = document.getElementById('expiryCountdown');
        countdownElement.textContent = 'Thời gian còn lại: 60 giây';
        const continueButton = document.querySelector('.return-customer-btn'); // Chọn nút "Tiếp tục"

        let countdown = 60;
        const countdownInterval = setInterval(function () {
            countdown--;
            countdownElement.textContent = `Thời gian còn lại: ${countdown} giây`;

            if (countdown === 0) {
                clearInterval(countdownInterval);
                countdownElement.textContent = 'Mã OTP đã hết hạn';
                continueButton.hidden = true; // Vô hiệu hóa nút "Tiếp tục"
                otpChecked = "";
            }
        }, 1000);
        alertSuccess("Tạo mã OTP thành công");
		document.getElementById("inputOTP").disabled = false;
        $scope.sendOTPToMail(otp);
    }

    $scope.sendOTPToMail = function (otp) {
        const enteredEmail = document.getElementById("email").value;
        const enteredOTP = otp;

        // alert(enteredEmail);
        // alert(enteredOTP);

        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/sendOTPToMail/${enteredEmail}/${enteredOTP}`, true);
        xhr.send();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Request successfully triggered.");
                    // Xử lý kết quả nếu cần
                } else {
                    console.error("Request failed.");
                    // Xử lý lỗi nếu cần
                }
            }
        };
    }
})
// End OTP