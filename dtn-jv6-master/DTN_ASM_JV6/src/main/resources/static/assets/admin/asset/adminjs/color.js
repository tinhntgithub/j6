let host1 = "http://localhost:8080/DTNsBike/rest";

var test1 = 0;
var key1 = null;
var form1 = {};
var url1 = "http://localhost:8080/DTNsBike/rest/uploads/productImg";
// List màu sắc controller
app.controller("color-list",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách màu sắc');
    });

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host1}/colors`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            // GET JSON ARRAY HEADERS.
            $scope.headers = Object.keys($scope.items[0]);
            $scope.columns = $scope.headers[1];
            // Search 

            $scope.changeFilterTo = function (pr) {
                if ($scope.search) {
                    var value = "";
                    for (var key in $scope.search) {
                        if ($scope.search[key]) {
                            value = angular.copy($scope.search[key]);
                            $scope.search[key] = undefined;
                        }
                    }
                    $scope.search[pr] = value;
                }
            }
            $scope.pageSize = 5;

            $scope.begin = 0;
            $scope.length = Object.keys($scope.items).length;
            $scope.pageCount = Math.ceil($scope.length / $scope.pageSize);
             if ($scope.length >= $scope.pageSize) {
            $scope.index2 = $scope.pageSize;
        } else {
            $scope.index2 = Math.ceil($scope.length - ($scope.pageCount / 2));
        }

            // $scope.prop ='id';
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

    var checkPrI = 0;
    $scope.checkPr = function (id) {
        var url = `${host1}/colors/check/${id}`;

        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == false) {
                checkPrI = 1;
            } else {
                checkPrI = 0;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.delete = function (id, name) {
        $scope.checkPr(id);
        Swal.fire({
            position: 'top',
            title: 'Thông báo?',
            text: "Bạn chắc chắn muốn xóa màu sắc " + name + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                if (checkPrI != 0) {
                    Swal.fire({
                        position: 'top',
                        title: 'Không thể xóa!',
                        text: 'Màu sắc này đã có sản phẩm !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host1}/colors/${id}`;
                    $http.delete(url).then(resp => {
                        var index = $scope.items.findIndex(item => item.id == id);
                        $scope.items.splice(index, 1);

                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Xóa thành công!',
                        text: 'Đã xóa màu sắc ' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key1 = id;
        test1 = 1;
    }


    $scope.index = 1;
    $scope.repaginate = function () {
        $scope.begin = 0;
        $scope.length = Object.keys($scope.items).length;
        $scope.pageCount = Math.ceil($scope.length / $scope.pageSize);
        if ($scope.length >= $scope.pageSize) {
            $scope.index2 = $scope.pageSize;
        } else {
            $scope.index2 = Math.ceil($scope.length - ($scope.pageCount / 2));
        }
    }
    $scope.prop2 = 0;
    $scope.sortBy = function (prop) {
        if ($scope.prop2 == 0) {
            $scope.prop = prop;
            $scope.prop2 = 1;
        } else {
            $scope.prop = '-' + prop;
            $scope.prop2 = 0;
        }
    }

    $scope.first = function () {
        $scope.begin = 0;
    }
    $scope.prev = function () {
        if ($scope.begin > 0) {
            $scope.begin -= $scope.pageSize;
            $scope.index -= 1;
        }
    }

    $scope.next = function () {
        if ($scope.begin < ($scope.pageCount - 1) * $scope.pageSize) {
            $scope.begin += $scope.pageSize;
            $scope.index +=1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.load_All();



});
// Form Màu Sắc Controller
app.controller("color-form",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Form Màu Sắc');
    });

    var loi = 0;

    if (test1 > 0) {
        $scope.showButton = false;
        test1 = 0;
    } else {
        $scope.showButton = true;
    }

    $scope.form = {};

    $scope.load = function () {
        if (key1 != null) {
            var url = `${host}/colors/${key1}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;
                form1 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key1 = null;
        } else {
            $scope.reset();

        }
    }

    $scope.reset = function () {
        $scope.form = {};
        $scope.showButton = true;
        $scope.loi = '';
    }
    $scope.create = function () {
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host}/colors`;
            $http.post(url, item).then(resp => {

                $scope.items.push(item);

                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm màu sắc thành công!',
                text: 'Đã thêm màu sắc ' + item.name,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }

    $scope.update = function () {
     
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host}/colors/${$scope.form.id}`;
            $http.put(url, item).then(resp => {
                var index = $scope.items.findIndex(item => item.id == $scope.form.id);
                $scope.items[index] = resp.data;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật màu sắc thành công!',
                text: 'Đã cập nhật màu sắc ' + item.name,
                icon: 'success'
            }
            )
        }


    }

    $scope.check = function () {
        var name = $scope.form.name;
        if (name == null || name.length <= 0) {
            $scope.loi = '* Vui lòng nhập tên màu sắc';
            loi++;
        } else {
            $scope.loi = '';
            loi = 0;
        }

        if (loi > 0) {
            loi =0;
            return false;
        } else {
            return true;
        }
    }



    $scope.load();

});

// Update màu sắc sản phẩm controller
app.controller("color-update",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Update Sản Phẩm màu sắc');
    });
    $scope.url = function (filename) {
        return`${url1}/${filename}`;
    }
    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host1}/product_colors`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            // GET JSON ARRAY HEADERS.
            $scope.headers = Object.keys($scope.items[0]);
            $scope.columns = $scope.headers[1];
            // Search 

            $scope.changeFilterTo = function (pr) {
                if ($scope.search) {
                    var value = "";
                    for (var key in $scope.search) {
                        if ($scope.search[key]) {
                            value = angular.copy($scope.search[key]);
                            $scope.search[key] = undefined;
                        }
                    }
                    $scope.search[pr] = value;
                }
            }
            $scope.pageSize = 5;

            $scope.begin = 0;
            $scope.length = Object.keys($scope.items).length;
            $scope.pageCount = Math.ceil($scope.length / $scope.pageSize);
             if ($scope.length >= $scope.pageSize) {
            $scope.index2 = $scope.pageSize;
        } else {
            $scope.index2 = Math.ceil($scope.length - ($scope.pageCount / 2));
        }

            // $scope.prop ='id';
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

    var loi = 0;
     $scope.form1 = {};
     $scope.form = {};
     $scope.load_Color = function () {
        var url = `${host1}/colors`;
        $http.get(url).then(resp => {
            $scope.colors = resp.data;
            $scope.form1 =  $scope.colors;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

     $scope.load_Products = function () {
        var url = `${host1}/products`;
        $http.get(url).then(resp => {
            $scope.products = resp.data;
            $scope.form = $scope.products;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.loadName = function(id){
        $scope.idd = id;
        var url = `${host1}/productss/${id}`;
             $http.get(url).then(resp => {
                $scope.name = resp.data.name;
                console.log("Success", resp.data.name)
            })
    }
    var checkPrC = 0;
    $scope.checkPrColor = function (id,productId) {
        var url = `${host1}/product_colors/check2/${id}/${productId}`;

        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == false) {
                checkPrC = 0;
            } else {
                checkPrC = 1;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }
    
    $scope.save = function(id){
        if($scope.check()==true){
            $scope.checkPrColor($scope.form1.colorPro.id,$scope.idd );
            Swal.fire({
            position: 'top',
            title: 'Thông báo',
            text: "Cập nhật màu sắc "+$scope.form1.colorPro.name+"cho sản phẩm " +  $scope.name + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                    if(checkPrC != 0){
                        Swal.fire({
                        position: 'top',
                        title: 'Không thể cập nhật!',
                        text: 'Sản phẩm '+ $scope.name+' đã có màu sắc này !?',
                        icon: 'error'
                    })
                    }else{
                        var item = angular.copy($scope.form1.colorPro);
                    var url = `${host1}/product_colors/${id}`;
                    $http.put(url,item).then(resp => {
                        console.log("Success", resp)
                        $scope.load_All();
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Cập nhật thành công!',
                        text: 'Đã cập nhật ' +  $scope.name,
                        icon: 'success'
                    }
                    )
                    }
                }
        })
        }
    }

    $scope.check = function () {
        var cate = $scope.form1.colorPro;
        var product = $scope.form.id;

        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi = '* Vui lòng chọn màu sắc';
             loi++;
        } else {
            $scope.loi = '';
        }
        if (product == null || product.length <= 0 || product == undefined) {
            $scope.loi1 = '* Vui lòng chọn sản phẩm';
             loi++;
        } else {
            $scope.loi1 = '';
        }

        if(loi > 0){
            loi = 0;
            return false;
        }else{
            return true;
        }
        
    }
    var checkPrI = 0;
    $scope.checkPr = function (id) {
        var url = `${host1}/product_colors/check/${id}`;

        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == false) {
                checkPrI = 1;
            } else {
                checkPrI = 0;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.delete = function (id, name) {
        $scope.checkPr(id);
        Swal.fire({
            position: 'top',
            title: 'Thông báo',
            text: "Xóa màu sắc của sản phẩm " + name + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                if(checkPrI != 0) {
                    Swal.fire({
                        position: 'top',
                        title: 'Không thể xóa!',
                        text: 'Màu sắc này đã có hóa đơn !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host1}/product_colors/${id}`;
                    $http.delete(url).then(resp => {
                        var index = $scope.items.findIndex(item => item.id == id);
                        $scope.items.splice(index, 1);

                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Xóa thành công!',
                        text: 'Đã xóa màu sắc của' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }



    $scope.index = 1;
    $scope.repaginate = function () {
        $scope.begin = 0;
        $scope.length = Object.keys($scope.items).length;
        $scope.pageCount = Math.ceil($scope.length / $scope.pageSize);
        if ($scope.length >= $scope.pageSize) {
            $scope.index2 = $scope.pageSize;
        } else {
            $scope.index2 = Math.ceil($scope.length - ($scope.pageCount / 2));
        }
    }
    $scope.prop2 = 0;
    $scope.sortBy = function (prop) {
        if ($scope.prop2 == 0) {
            $scope.prop = prop;
            $scope.prop2 = 1;
        } else {
            $scope.prop = '-' + prop;
            $scope.prop2 = 0;
        }
    }

    $scope.first = function () {
        $scope.begin = 0;
    }
    $scope.prev = function () {
        if ($scope.begin > 0) {
            $scope.begin -= $scope.pageSize;
            $scope.index -= 1;
        }
    }

    $scope.next = function () {
        if ($scope.begin < ($scope.pageCount - 1) * $scope.pageSize) {
            $scope.begin += $scope.pageSize;
            $scope.index +=1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.load_All();
    $scope.load_Color();
    $scope.load_Products();
});