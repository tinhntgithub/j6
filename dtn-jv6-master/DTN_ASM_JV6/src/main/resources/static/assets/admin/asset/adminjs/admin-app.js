var app= angular.module('admin-app',['ngRoute']);

app.config(function($routeProvider){
    $routeProvider.
    when("/",{
        templateUrl: "page/home/index.html",
        controller: "index"
    }).
    when("/account-form",{
        templateUrl: "page/account-manager/account-form.html",
        controller: "account-form"
    })
    .when("/account-list",{
        templateUrl: "page/account-manager/account-list.html",
        controller: "account-list"
    })
    .when("/account-auth",{
        templateUrl: "page/account-manager/account-auth.html",
        controller: "account-auth"
    })
    .when("/product-form",{
        templateUrl: "page/product-manager/product-form.html",
        controller: "product-form"
    })
    .when("/product-list",{
        templateUrl: "page/product-manager/product-list.html",
        controller: "product-list"
    })
    .when("/product-category",{
        templateUrl: "page/product-manager/product-category.html",
        controller: "product-category"
    })
    .when("/product-img",{
        templateUrl: "page/product-manager/product-img.html",
        controller: "product-img"
    })
    .when("/category-form",{
        templateUrl: "page/category-manager/category-form.html",
        controller: "category-form"
    })
    .when("/category-list",{
        templateUrl: "page/category-manager/category-list.html",
        controller: "category-list"
    })
    .when("/color-form",{
        templateUrl: "page/color-manager/color-form.html",
        controller: "color-form"
    })
    .when("/color-list",{
        templateUrl: "page/color-manager/color-list.html",
        controller: "color-list"
    })
    .when("/color-update",{
        templateUrl: "page/color-manager/color-update.html",
        controller: "color-update"
    })
    .when("/sale-form",{
        templateUrl: "page/sale-manager/sale-form.html",
        controller: "sale-form"
    })
    .when("/sale-list",{
        templateUrl: "page/sale-manager/sale-list.html",
        controller: "sale-list"
    })
    .when("/brand-form",{
        templateUrl: "page/brand-manager/brand-form.html",
        controller: "brand-form"
    })
    .when("/brand-list",{
        templateUrl: "page/brand-manager/brand-list.html",
        controller: "brand-list"
    })
    .when("/brand-update",{
        templateUrl: "page/brand-manager/brand-update.html",
        controller: "brand-update"
    })
    .when("/order-cancel",{
        templateUrl: "page/order-manager/order-cancel.html",
        controller: "order-cancel"
    })
    .when("/order-delivered",{
        templateUrl: "page/order-manager/order-delivered.html",
        controller: "order-delivered"
    })
    .when("/order-wait",{
        templateUrl: "page/order-manager/order-wait.html",
        controller: "order-wait"
    })
    .when("/order-done",{
        templateUrl: "page/order-manager/order-done.html",
        controller: "order-done"
    })
    .when("/order-details",{
        templateUrl: "page/order-manager/order-details.html",
        controller: "order-details"
    })
    .when("/historysale",{
        templateUrl: "page/history-sale/historysale.html",
        controller: "historysale"
    })
    .when("/report-order",{
        templateUrl: "page/report-manager/report-order.html",
        controller: "report-order"
    })
    .when("/report-favorite",{
        templateUrl: "page/report-manager/report-favorite.html",
        controller: "report-favorite"
    })
    .when("/report-customer",{
        templateUrl: "page/report-manager/report-customer.html",
        controller: "report-customer"
    })
    .otherwise({
        template:"<h1 class='text-center'>Home page</h1>"
    })
});
app.run(['$rootScope', function($rootScope) {
    $rootScope.page = {
        setTitle: function(title) {
            this.title =  'DTNsBike |' + title;
          
        }
    }
   
    $rootScope.$on('$routeChangeSuccess', function(event, current, previous) {
        $rootScope.page.setTitle(current.$$route.title || ' Trang quản trị');
        
    });
}]);


// Admin Index controller
app.controller("index",function($scope,$http){
    let host = "http://localhost:8080/DTNsBike/rest";
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

});
app.controller("account",function($scope,$http){
    let host = "http://localhost:8080/DTNsBike/rest";
    var url = `${host}/fullname`;
        $http.get(url).then(resp => {
            $scope.name = resp.data.fullname;
            $scope.photo = `${host}/uploads/accountImg/${resp.data.photo}`
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });
});