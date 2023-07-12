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
var host = "http://localhost:8080/DTNsBike/rest/";
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
        var urlLogin = "http://" + $window.location.host + "/DTNsBike/login.html";
        $http.get(url).then(resp => {
			console.log(resp);
            alert("Thêm vào giỏ hàng thành công");
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
         var urlLogin = "http://" + $window.location.host + "/DTNsBike/login.html";
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
		var urlLike = `http://localhost:8080/DTNsBike/rest/favorites/like/${id}`;//${id}
		var urlLogin = "http://" + $window.location.host + "/DTNsBike/login.html";
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

app.controller("pushCart", function ($scope, $http,$rootScope) {
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
    })
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
        
})
    // $rootScope.$emit("list", {});
   
//End Cart Controller

