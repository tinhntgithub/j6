
app.controller("report-favorite", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Mặt Hàng Yêu Thích');
    });

    var url5 = "http://localhost:8080/uploads/productImg";

    $scope.reportFavorites = [];
    $scope.url = function (filename) {
        return`${url5}/${filename}`;
    }
    // Function to load all order data
    $scope.loadAllreportFavorites = function () {
        var url = "http://localhost:8080/rest/reportFavorites";
        $http.get(url).then(function (response) {
            $scope.reportFavorites = response.data;
            console.log(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    };
    $scope.loadAllreportFavorites();
});

app.controller("report-order", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Đơn Hàng');
    });
    // var url4 = "http://localhost:8080/uploads/productImg";
    $scope.reportOrder = [];
    $scope.url = function (filename) {
        return`${url4}/${filename}`;
    }
    // Function to load all order data
    $scope.loadAllOrder = function () {
        var url = "http://localhost:8080/rest/reportOrder";
        $http.get(url).then(function (response) {
            $scope.reportOrder = response.data;
            console.log(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    };
    $scope.loadAllOrder();
});

app.controller("report-customer", function ($scope, $http, $filter) {
    let host = "http://localhost:8080/rest";
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Khách Hàng');
    });
    $scope.dateNow = new Date();
    $scope.reportCustomer = [];

    // Function to load all customer data
    $scope.loadAllCustomer = function () {
        var url = `${host}/reportCustomerAll`;
        $http.get(url).then(function (response) {
            $scope.reportCustomer = response.data;
            console.log(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    };
    $scope.loadAllCustomer();
    // Function to load customer report data based on date range
    $scope.dates = function () {
        var dateS = $filter('date')($scope.dateStart, "yyyy-MM-dd");
        var dateE = $filter('date')($scope.dateEnd, "yyyy-MM-dd");

        if (!dateS) {
            dateS = $("#start").text();
        }
        if (!dateE) {
            dateE = $("#start").text();	
        }

        var url = `${host}/reportCustomer?start=${dateS}&end=${dateE}`;
        $http.get(url)
            .then(function (response) {
                // Success callback
                $scope.reportCustomer = response.data;
                console.log(response.data);
            })
            .catch(function (error) {
                // Error callback
                console.log(error);
            });

        console.log(dateS, dateE);
    };

    // Calling the function to load all customer data (assuming you want to load data when the controller initializes)
   
});
