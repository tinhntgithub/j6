let host2 = "http://localhost:8080/DTNsBike/rest";

var test2 = 0;
var key2 = null;
var form2 = {};


// List giảm giá controller
app.controller("sale-list", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách giảm giá');
    });


    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host2}/sales`;
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
        var url = `${host2}/sales/check/${id}`;

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
            text: "Bạn chắc chắn muốn xóa giảm giá " + name + " này!?",
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
                        text: 'Mã giảm giá này đã được sử dụng!?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host2}/sales/${id}`;
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
                        text: 'Đã xóa mã giảm giá ' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key2 = id;
        test2 = 1;
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
            $scope.index += 1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.load_All();

});

// From giảm giá controller
app.controller("sale-form", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Form Giảm Giá');
    });

    var loi = 0;

    if (test2 > 0) {
        $scope.showButton = false;
        test2 = 0;
    } else {
        $scope.showButton = true;
    }

    $scope.form = {};

    $scope.load = function () {
        if (key2 != null) {
            var url = `${host2}/sales/${key2}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;

                form2 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key2 = null;
        } else {
            $scope.reset();

        }
    }

    $scope.reset = function () {
        $scope.form = {};
        $scope.showButton = true;
        $scope.loi = '';
        $scope.loi1 = '';
        $scope.loi2 = '';
        $scope.loi3 = '';
    }
    $scope.create = function () {
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host2}/sales`;
            $http.post(url, item).then(resp => {
                $scope.items.push(item);
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm thành công!',
                text: 'Đã thêm mã giảm giá ' + item.code,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }

    $scope.update = function () {

        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host2}/sales/${$scope.form.id}`;

            $http.put(url, item).then(resp => {
                var index = $scope.items.findIndex(item => item.id == $scope.form.id);
                $scope.items[index] = resp.data;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật thành công!',
                text: 'Đã cập nhật mã giảm giá ' + item.code,
                icon: 'success'
            }
            )
        }


    }

    $scope.check = function () {
        var code = $scope.form.code;
        var value = $scope.form.value;
        var amount = $scope.form.amount;
        var saleDate = $scope.form.saleDate;


        if (code == null || code.length <= 0) {
            $scope.loi = '* Không được bỏ trống mã giảm giá';
            loi++;
        } else if (code.length < 3 || code.length > 6) {
            $scope.loi = '* Mã giảm giá phải từ 3 đến 6 ký tự';
            loi++;
        } else {
            $scope.loi = '';
            loi = 0;
        }

        if (value == null || value.length <= 0) {
            $scope.loi1 = '* Không được bỏ trống giảm giá';
            loi++;
        } else {
            if (value <= 0 || value >= 50) {
                $scope.loi1 = '* Giá trị giảm giá không được nhỏ hơn 0 và hơn 50';
                loi++;
            } else {
                $scope.loi1 = '';
                loi = 0;
            }
        }
        if (amount == null || amount.length <= 0) {
            $scope.loi2 = '* Số lượng không được bỏ trống';
            loi++;
        } else if (amount <= 0) {
            $scope.loi2 = '* Số lượng phải lớn hơn 0';
            loi++;
        } else {
            $scope.loi2 = '';
            loi = 0;
        }
        if (saleDate == null || saleDate.length <= 0) {
            $scope.loi3 = '* Ngày giảm giá không được bỏ trống';
            loi++;
        } else {
            $scope.loi3 = '';
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
