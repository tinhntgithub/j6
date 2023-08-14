
app.controller("report-favorite", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Mặt Hàng Yêu Thích');
    });

    var url5 = "http://localhost:8080/uploads/productImg";

    $scope.reportFavorites = [];
    $scope.url = function (filename) {
        return`${url5}/${filename}`;
    }

    // Function to initialize DataTables
    $scope.initDataTable = function () {
        $('#example').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'copy', 'csv', 'excel', 'pdf', 'print'
            ],
            data: $scope.reportFavorites,
            searching: false,
            paging: false,
            info: false,
            ordering: false ,// Tắt tính năng sắp xếp'
            columns: [
                { data: 'id', title: 'Mã sản phẩm' },
                { data: 'name', title: 'Tên sản phẩm' },
                { data: 'countLike', title: 'Số lượt thích' },
                { data: 'formattedPrice', title: 'Giá tiền' } // Sử dụng giá tiền đã định dạng
            ]
        });
    };
    
    
    // Function to load all order data
    $scope.loadAllreportFavorites = function () {
        var url = "http://localhost:8080/rest/reportFavorites";
        $http.get(url).then(function (response) {
            $scope.reportFavorites = response.data.map(function(item) {
                return {
                    id: item[0],
                    name: item[1],
                    countLike: item[4],
                    formattedPrice: formatCurrency(item[3]) // Định dạng giá tiền
                };
            });
            console.log(response.data);
            $scope.initDataTable();
        }).catch(function (error) {
            console.log(error);
        });
    };
    
    // Hàm định dạng giá tiền thành chuỗi có dấu phẩy ngăn cách hàng nghìn và hai chữ số sau dấu phẩy
    function formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
    }
    

    $scope.loadAllreportFavorites();
});
app.controller("report-order", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Đơn Hàng');
    });
 var url5 = "http://localhost:8080/uploads/productImg";
    $scope.reportOrder = [];
    $scope.url = function (filename) {
        return`${url5}/${filename}`;
    }
    // Function to load all order data
    $scope.loadAllOrder = function () {
        var url = "http://localhost:8080/rest/reportOrder";
        $http.get(url).then(function (response) {
            $scope.reportOrder = response.data.map(function(item, index) {
                return {
                    stt: index + 1,
                    name: item[0],
                    imgUrl: $scope.url(item[1]), // Đường dẫn hình ảnh
                    numberOfSales: item[2]
                };
            });
            console.log(response.data);
            $scope.initDataTable1();
        }).catch(function (error) {
            console.log(error);
        });
    };

    $scope.initDataTable1 = function() {
        $('#example1').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'copy', 'csv', 'excel', 'pdf', 'print'
            ],
            data: $scope.reportOrder,
            searching: false,
            paging: true,
            info: false,
            columns: [
                { data: 'stt', title: 'STT' },
                { data: 'name', title: 'Tên sản phẩm' },
                { data: 'imgUrl', title: 'Hình ảnh', render: function(data) {
                    return `<img class="img-fluid" src="${data}" style="width: 64px;height: 64px;">`;
                } },
                { data: 'numberOfSales', title: 'Số lượng bán' }
            ]
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

    // Function to initialize DataTable
    $scope.initDataTable = function () {
        $('#example2').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'copy', 'csv', 'excel', 'pdf', 'print'
            ],
            searching: false,
            paging: true,
            info: false,
            data: $scope.reportCustomer,
            columns: [
                { data: 'name', title: 'Tên Khách Hàng' },
                { data: 'createDate', title: 'Ngày Đặt Hàng' },
                { data: 'productCount', title: 'Số Lượng Sản Phẩm' },
                { data: 'totalSum', title: 'Tổng Tiền' }
            ]
        });
    };

    // Function to load all customer data
    $scope.loadAllCustomer = function () {
        var url = `${host}/reportCustomerAll`;
        $http.get(url).then(function (response) {
            $scope.reportCustomer = response.data.map(function(item) {
                return {
                    name: item[0],
                    createDate: formatDate(new Date(item[1])),
                    productCount: item[2],
                    totalSum: formatCurrency(item[3])
                };
            });
            $scope.initDataTable();
            console.log(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    };
// Cập nhật dữ liệu trong DataTable hiện tại
$scope.updateDataTable = function(newData) {
    var table = $('#example2').DataTable();
    table.clear().rows.add(newData).draw();
};
    // Function to load customer report data based on date range
    
    $scope.dates = function () {
        var dateS = $filter('date')($scope.dateStart, "yyyy-MM-dd");
        var dateE = $filter('date')($scope.dateEnd, "yyyy-MM-dd");
    
        if (!dateS) {
            dateS = $("#start").text();
        }
        if (!dateE) {
            dateE = $("#end").text();
        }
    
        var url = `${host}/reportCustomer?start=${dateS}&end=${dateE}`;
        $http.get(url)
            .then(function (response) {
                // Success callback
                $scope.reportCustomer = response.data.map(function(item) {
                    return {
                        name: item[0],
                        createDate: formatDate(new Date(item[1])),
                        productCount: item[2],
                        totalSum: formatCurrency(item[3])
                    };
                });
                $scope.updateDataTable($scope.reportCustomer);
                console.log(response.data);
                // $scope.initDataTable();
                
            })
            .catch(function (error) {
                // Error callback
                console.log(error);
            });
    
        console.log(dateS, dateE);
    };
    

    // Helper function to format date
    function formatDate(date) {
        if (!date) {
            return '';
        }
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        return `${day}/${month}/${year}`;
    }

    // Helper function to format currency
    function formatCurrency(value) {
        if (typeof value !== 'number') {
            return '';
        }
        return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }

    // Calling the function to load all customer data (assuming you want to load data when the controller initializes)
    $scope.loadAllCustomer();
});

