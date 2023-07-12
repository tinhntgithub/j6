let host6 = "http://localhost:8080/DTNsBike/rest";

var url6 = "http://localhost:8080/DTNsBike/rest/uploads/productImg";


//  Lịch sử đổi giá controller
app.controller("historysale",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Lịch Sử Đổi Giá');
    });

    $scope.url = function (filename) {
        return `${url6}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host6}/pricehistory`;
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

    $scope.load_All();
});