let host3 = "http://localhost:8080/DTNsBike/rest";

var test3 = 0;
var key3 = null;
var form3 = {};
var url3 = "http://localhost:8080/DTNsBike/rest/uploads/brandImg";
// List Thương Hiệu Controller
app.controller("brand-list",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách thương hiệu');
    });

    $scope.url = function (filename) {
        return`${url3}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host3}/brands`;
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
        var url = `${host3}/brands/check/${id}`;

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
            text: "Bạn chắc chắn muốn xóa thương hiệu " + name + " này!?",
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
                        text: 'Thương hiệu này đã có sản phẩm !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host3}/brands/${id}`;
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
                        text: 'Đã xóa thương hiệu ' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key3 = id;
        test3 = 1;
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

// Form THương hiệu controller
app.controller("brand-form",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Form Thương Hiệu');
    });
    var url3 = "http://localhost:8080/DTNsBike/rest/uploads/brandImg";
    $scope.url = function (filename) {
        if(filename != null){
            return`${url3}/${filename}`;
        }else{
            return 'asset/images/default.jpg';
        }
    }

    var loi = 0;

    if (test3 > 0) {
        $scope.showButton = false;
        test3 = 0;
    } else {
        $scope.showButton = true;
    }

    $scope.form = {};

    $scope.load = function () {
        if (key3 != null) {
            var url = `${host3}/brands/${key3}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;
                form3 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key3 = null;
        } else {
            $scope.reset();

        }
    }

    $scope.reset = function () {
        $scope.form = {};
        $scope.showButton = true;
        $scope.loi = '';
    }

    $scope.upload = function (files) {
        var form = new FormData();
        form.append('files', files[0]);
        $http.post(url3, form, {
            transformRequest:angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.form.logo = resp.data.name;
            console.logo("Success",resp.data);
        }).catch(error => {
            console.log("Errors", error);
        })

    }

    $scope.create = function () {
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host3}/brands`;
            $http.post(url, item).then(resp => {

                $scope.items.push(item);

                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm thương hiệu thành công!',
                text: 'Đã thêm thương hiệu ' + item.nameString,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }

    $scope.update = function () {
     
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host3}/brands/${$scope.form.id}`;
            $http.put(url, item).then(resp => {
                var index = $scope.items.findIndex(item => item.id == $scope.form.id);
                $scope.items[index] = resp.data;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật thương hiệu thành công!',
                text: 'Đã cập nhật thương hiệu ' + item.nameString,
                icon: 'success'
            }
            )
        }


    }

    $scope.check = function () {
        var name = $scope.form.nameString;
        if (name == null || name.length <= 0) {
            $scope.loi = '* Vui lòng nhập tên thương hiệu';
            loi++;
        } else {
            $scope.loi = '';
            loi = 0;
        }

        if (loi > 0) {
            loi = 0;
            return false;
        } else {
            return true;
        }
    }



    $scope.load();
});

// Form Update THương hiệu controller
app.controller("brand-update",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Update Thương Hiệu');
    });
    var url3 = "http://localhost:8080/DTNsBike/rest/uploads/productImg";
    $scope.url = function (filename) {
        if(filename != null){
            return`${url3}/${filename}`;
        }else{
            return 'asset/images/default.jpg';
        }
    }

    var loi = 0;
     $scope.form1 = {};
     $scope.form = {};
     $scope.load_Brand = function () {
        var url = `${host3}/brands`;
        $http.get(url).then(resp => {
            $scope.brands = resp.data;
            $scope.form1 =  $scope.brands;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

     $scope.load_Products = function () {
        var url = `${host3}/products`;
        $http.get(url).then(resp => {
            $scope.products = resp.data;
            $scope.form = $scope.products;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.loadName = function(id){
        var url = `${host3}/productss/${id}`;
             $http.get(url).then(resp => {
                $scope.name = resp.data.name;
                console.log("Success", resp.data.name)
            })
    }
    
    $scope.save = function(id){
        if($scope.check()==true){
            Swal.fire({
            position: 'top',
            title: 'Thông báo',
            text: "Cập nhật thương hiệu cho sản phẩm " +  $scope.name + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                    var item = angular.copy($scope.form1.brandPro);
                    var url = `${host3}/products_brand/${id}`;
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
        })
        }
    }

    $scope.check = function () {
        var cate = $scope.form1.brandPro;
        var product = $scope.form.id;

        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi = '* Vui lòng chọn thương hiệu';
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


    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host3}/products`;
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


    $scope.load_Brand();
    $scope.load_Products();

});
