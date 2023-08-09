// var app= angular.module('admin-app',['ngRoute']);
var app = angular.module('admin-app', ['ngRoute', 'angularUtils.directives.dirPagination']);
app.config(function ($routeProvider) {
    $routeProvider.
        when("/", {
            templateUrl: "page/home/index.html",
            controller: "index"
        }).
        when("/account-form", {
            templateUrl: "page/account-manager/account-form.html",
            controller: "account-form"
        })
        .when("/account-list", {
            templateUrl: "page/account-manager/account-list.html",
            controller: "account-list"
        })
        .when("/account-auth", {
            templateUrl: "page/account-manager/account-auth.html",
            controller: "account-auth"
        })
        .when("/product-form", {
            templateUrl: "page/product-manager/product-form.html",
            controller: "product-form"
        })
        .when("/product-list", {
            templateUrl: "page/product-manager/product-list.html",
            controller: "product-list"
        })
        .when("/product-category", {
            templateUrl: "page/product-manager/product-category.html",
            controller: "product-category"
        })
        .when("/product-img", {
            templateUrl: "page/product-manager/product-img.html",
            controller: "product-img"
        })
        .when("/category-form", {
            templateUrl: "page/category-manager/category-form.html",
            controller: "category-form"
        })
        .when("/category-list", {
            templateUrl: "page/category-manager/category-list.html",
            controller: "category-list"
        })
        .when("/color-form", {
            templateUrl: "page/color-manager/color-form.html",
            controller: "color-form"
        })
        .when("/color-list", {
            templateUrl: "page/color-manager/color-list.html",
            controller: "color-list"
        })
        .when("/color-update", {
            templateUrl: "page/color-manager/color-update.html",
            controller: "color-update"
        })
        .when("/sale-form", {
            templateUrl: "page/sale-manager/sale-form.html",
            controller: "sale-form"
        })
        .when("/sale-list", {
            templateUrl: "page/sale-manager/sale-list.html",
            controller: "sale-list"
        })
        .when("/brand-form", {
            templateUrl: "page/brand-manager/brand-form.html",
            controller: "brand-form"
        })
        .when("/brand-list", {
            templateUrl: "page/brand-manager/brand-list.html",
            controller: "brand-list"
        })
        .when("/brand-update", {
            templateUrl: "page/brand-manager/brand-update.html",
            controller: "brand-update"
        })
        .when("/order-cancel", {
            templateUrl: "page/order-manager/order-cancel.html",
            controller: "order-cancel"
        })
        .when("/order-delivered", {
            templateUrl: "page/order-manager/order-delivered.html",
            controller: "order-delivered"
        })
        .when("/order-wait", {
            templateUrl: "page/order-manager/order-wait.html",
            controller: "order-wait"
        })
        .when("/order-done", {
            templateUrl: "page/order-manager/order-done.html",
            controller: "order-done"
        })
        .when("/order-details", {
            templateUrl: "page/order-manager/order-details.html",
            controller: "order-details"
        })
        .when("/historysale", {
            templateUrl: "page/history-sale/historysale.html",
            controller: "historysale"
        })
        .when("/report-order", {
            templateUrl: "page/report-manager/report-order.html",
            controller: "report-order"
        })
        .when("/report-favorite", {
            templateUrl: "page/report-manager/report-favorite.html",
            controller: "report-favorite"
        })
        .when("/report-customer", {
            templateUrl: "page/report-manager/report-customer.html",
            controller: "report-customer"
        })
        .otherwise({
            template: "<h1 class='text-center'>Home page</h1>"
        })
});
app.run(['$rootScope', function ($rootScope) {
    $rootScope.page = {
        setTitle: function (title) {
            this.title = 'J6TEAM |' + title;

        }
    }

    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.page.setTitle(current.$$route.title || ' Trang quản trị');

    });
}]);


// Admin Index controller
app.controller("index", function ($scope, $http) {
    let host = "http://localhost:8080/rest";
    $scope.load_All = function () {
        var url = `${host}/indexCount`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.load_All();
    // Hàm lấy dữ liệu từ API
    $scope.getRevenueData = function () {
        var url = `${host}/revenueData`;
        $http.get(url).then(function (response) {
            // Lấy dữ liệu từ API response
            var data = response.data;
            var years = [];
            var revenues = [];
            for (var i = 0; i < data.length; i++) {
                years.push(data[i][0]);
                revenues.push(data[i][1]);
            }
            console.log(response);
            // Cấu hình biểu đồ
            var ctx = document.getElementById('myChart').getContext('2d');
            var chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: years,
                    datasets: [{
                        label: 'Doanh thu theo năm',
                        data: revenues,
                        backgroundColor: 'rgba(255, 69, 0, 0.7)',
                        borderColor: '#FF0000',
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: 'Bảng 1.1 BIỂU ĐỒ DOANH THU NĂM', // Đặt tiêu đề ở đây
                            position: 'bottom',
                            fontSize: 16
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }).catch(function (error) {
            console.log(error);
        });
    };
    $scope.getRevenueData1 = function () {
        var url = `${host}/revenueDataMonth`;
        $http.get(url).then(function (response) {
            // Lấy dữ liệu từ API response
            var data = response.data;
            var years = [];
            var revenues = [];
            for (var i = 0; i < data.length; i++) {
                years.push(data[i][0]);
                revenues.push(data[i][1]);
            }
            console.log(response);
            // Cấu hình biểu đồ line chart
            var ctx = document.getElementById('myChart1').getContext('2d');
            var chart = new Chart(ctx, {
                type: 'line', // Thay đổi type thành 'line'
                data: {
                    labels: years,
                    datasets: [{
                        label: 'VNĐ',
                        data: revenues,
                        borderColor: '#FF0000',
                        borderWidth: 1,
                        fill: false // Thiết lập fill: false để không tô màu dưới đường line
                    }]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: 'Bảng 1.2 BIỂU ĐỒ DOANH THU THÁNG', // Đặt tiêu đề ở đây
                            position: 'bottom',
                            fontSize: 16
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }).catch(function (error) {
            console.log(error);
        });
    };

    // Gọi hàm để lấy dữ liệu khi controller khởi tạo
    $scope.getRevenueData();
    $scope.getRevenueData1();
});
app.controller("account", function ($scope, $http) {
    let host = "http://localhost:8080/rest";
    var url = `${host}/fullname`;
    $http.get(url).then(resp => {
        $scope.name = resp.data.fullname;
        $scope.photo = `http://localhost:8080/uploads/accountImg/${resp.data.photo}`
        console.log("Success", resp)
    }).catch(error => {
        console.log("Error", error);
    });
});


app.filter('customCurrency', function () {
    return function (input) {
        // Kiểm tra nếu input không phải là số hoặc null/undefined, trả về input ban đầu
        if (isNaN(input) || input === null || input === undefined) {
            return input;
        }

        // Format số tiền thành chuỗi, cách nhau bởi dấu phẩy
        var formattedValue = input.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        // Thêm chữ VND vào cuối chuỗi và trả về kết quả
        return formattedValue + ' VND';
    };
});


app.filter('range', function () {
    return function (input, total) {
        total = parseInt(total);
        for (var i = 0; i < total; i++)
            input.push(i);
        return input;
    };
});

// app.directive('worldwideSalesChart', function () {
//     return {
//         restrict: 'A',
//         link: function (scope, element, attrs) {
//             var chartData = scope.$eval(attrs.worldwideSalesData);
//             var chartLabels = scope.$eval(attrs.worldwideSalesLabels);
//             var chartBackgroundColor = scope.$eval(attrs.worldwideSalesBackgroundColor);
//             var chartTitle = attrs.chartTitle;
//             var ctx = element[0].getContext('2d');
//             var myChart = new Chart(ctx, {
//                 type: 'bar',
//                 data: {
//                     labels: chartLabels,
//                     datasets: [{
//                         label: 'VNĐ',
//                         data: chartData,
//                         backgroundColor: chartBackgroundColor
//                     }]
//                 },
//                 options: {
//                     responsive: true,
//                     plugins: {
//                         title: {
//                             display: true,
//                             text: chartTitle, // Sử dụng giá trị của thuộc tính chart-title làm tiêu đề của biểu đồ
//                             position: 'bottom',
//                             fontSize: 50
//                         }
//                     }

//                 }
//             });
//         }
//     };
// });

app.directive('lineChart', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var chartData = scope.$eval(attrs.chartData);
            var chartLabels = scope.$eval(attrs.chartLabels);
            var chartBorderColor = scope.$eval(attrs.chartBorderColor);
            var chartTitle = attrs.chartTitle;
            var ctx = element[0].getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: chartLabels,
                    datasets: [{
                        label: 'VNĐ',
                        data: chartData,
                        backgroundColor: 'transparent',
                        borderColor: chartBorderColor,
                        borderWidth: 2,
                        fill: false
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        title: {
                            display: true,
                            text: chartTitle,
                            position: 'bottom',
                            fontSize: 50
                        }
                    }
                }
            });
        }
    };
});


