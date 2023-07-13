app.controller("report-favorite",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Mặt Hàng Yêu Thích');
    });
});
app.controller("report-order",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Đơn Hàng');
    });
});
app.controller("report-customer",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Khách Hàng');
    });
});