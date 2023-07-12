let host7 = "http://localhost:8080/DTNsBike/rest";

var url7 = "http://localhost:8080/DTNsBike/rest/uploads/accountImg";
var key7 = null;
var form7 = {};


// Đơn hàng chờ controller
app.controller("order-wait", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Đơn hàng đang chờ');
    });


    $scope.url = function (filename) {
        return `${url7}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host7}/order-wait`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;

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
    $scope.orderConfirm = function (id) {
        Swal.fire({
            position: 'top',
            title: 'Thông báo !',
            text: "Xác nhận giao hàng cho đơn hàng DH00" + id + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm !'
        }).then((result) => {
            if (result.isConfirmed) {
                    var url = `${host3}/order/${id}/2`;
                    $http.put(url).then(resp => {
                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Xác nhận giao hàng thành công!',
                        text: 'Đã xác nhận giao hàng cho đơn hàng DH00' + id,
                        icon: 'success'
                    }
                    )
                
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
            $scope.index += 1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.details = function (id) {
        key7 = id;
    }

    $scope.load_All();


});

// Đơn hàng đang giao controller
app.controller("order-delivered", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Đơn hàng đang giao');
    });

    $scope.url = function (filename) {
        return `${url7}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host7}/order-delivered`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;

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
    $scope.orderConfirm = function (id) {
        Swal.fire({
            position: 'top',
            title: 'Thông báo !',
            text: "Xác nhận đã giao hàng cho đơn hàng DH00" + id + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm !'
        }).then((result) => {
            if (result.isConfirmed) {
                     var url = `${host3}/order/${id}/3`;
                    $http.put(url).then(resp => {
                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Đã giao hàng thành công!',
                        text: 'Đã giao hàng cho đơn hàng DH00' + id,
                        icon: 'success'
                    }
                    )
                
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
            $scope.index += 1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.details = function (id) {
        key7 = id;
    }

    $scope.load_All();
});

// Đơn hàng đã giao controller
app.controller("order-done", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Đơn hàng đã giao');
    });

     $scope.url = function (filename) {
        return `${url7}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host7}/order-done`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;

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
            $scope.index += 1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.details = function (id) {
        key7 = id;
    }

    $scope.load_All();
});

// Đơn hàng đã hủy controller
app.controller("order-cancel", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Đơn hàng đã huỷ');
    });

     $scope.url = function (filename) {
        return `${url7}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host7}/order-cancel`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;

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
            $scope.index += 1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.details = function (id) {
        key7 = id;
    }

    $scope.load_All();
});

// Chi tiết đơn hàng controller
app.controller("order-details", function ($scope, $http) {


    var url = "http://localhost:8080/DTNsBike/rest/uploads/productImg";
    $scope.url = function (filename) {
        if (filename != null) {
            return `${url}/${filename}`;
        } else {
            return 'asset/images/default.jpg';
        }
    }
    $scope.id = key7;

    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Thông tin chi tiết đơn hàng | DH00' + $scope.id);
    });

    $scope.load = function () {
        if (key7 != null) {
            var url = `${host7}/order/${key7}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;
                form7 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
        }
    }
    $scope.loadProduct = function () {
        if (key7 != null) {
            var url = `${host7}/order-details/${key7}`;
            $http.get(url).then(resp => {
                $scope.form2 = resp.data;
                console.log("Success", resp.data)
            }).catch(error => {
                console.log("Error", error);
            });
        }
    }
    $scope.loadAmount = function () {
        if (key7 != null) {
            var url = `${host7}/orders-details/${key7}`;
            $http.get(url).then(resp => {
                $scope.amount = resp.data;
                console.log("Success", resp.data)
            }).catch(error => {
                console.log("Error", error);
            });
            key7 = null;
        }
    }

    $scope.load();
    $scope.loadProduct();
    $scope.loadAmount();
});