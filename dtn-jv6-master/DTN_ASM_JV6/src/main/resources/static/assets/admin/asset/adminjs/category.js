let host = "http://localhost:8080/DTNsBike/rest";
var test = 0;
var key = null;
var form = {};

// Category List Controller
app.controller("category-list", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách danh mục');
    });

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host}/categories`;
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
        var url = `${host}/categories/check/${id}`;

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
            text: "Bạn chắc chắn muốn xóa danh mục " + name + " này!?",
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
                        text: 'Danh mục này đã có sản phẩm !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host}/categories/${id}`;
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
                        text: 'Đã xóa danh mục ' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key = id;
        test = 1;
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


// Category Form Controller :)))
app.controller("category-form", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Form Danh mục');
    });

    var loi = 0;

    if (test > 0) {
        $scope.showButton = false;
        test = 0;
    } else {
        $scope.showButton = true;
    }

    $scope.form = {};

    $scope.load = function () {
        if (key != null) {
            var url = `${host}/categories/${key}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;
                form = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key = null;
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
            var url = `${host}/categories`;
            $http.post(url, item).then(resp => {

                $scope.items.push(item);

                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm danh mục thành công!',
                text: 'Đã thêm danh mục ' + item.name,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }

    $scope.update = function () {
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host}/categories/${$scope.form.id}`;
            $http.put(url, item).then(resp => {
                var index = $scope.items.findIndex(item => item.id == $scope.form.id);
                $scope.items[index] = resp.data;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật danh mục thành công!',
                text: 'Đã cập nhật danh mục ' + item.name,
                icon: 'success'
            }
            )
        }


    }

    $scope.check = function () {
        var name = $scope.form.name;
        if (name == null || name.length <= 0) {
            $scope.loi = '* Vui lòng nhập tên danh mục';
            loi++;
        } else {
            $scope.loi = '';
            loi= 0;
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

app.directive('convertToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, atrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }

    }
})
