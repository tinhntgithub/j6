
app.controller("report-favorite", function($scope, $http) {
	$scope.$on('$routeChangeSuccess', function(event, current, previous) {
		$scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Mặt Hàng Yêu Thích');
	});

	var url2 = "http://localhost:8080/uploads/productImg";

	$scope.reportFavorites = [];

	$scope.url = function(filename) {
		return `${url2}/${filename}`;
	}
	// Function to initialize DataTables
	$scope.initDataTable = function() {
		$('#example').DataTable({
			dom: 'Bfrtip',
			buttons: [
				'copy', 'csv', 'excel', 'pdf', 'print'
			],
			data: $scope.reportFavorites,
			searching: false,
			paging: false,
			info: false,
			ordering: false,// Tắt tính năng sắp xếp'
			columns: [
						{ data: 'id', title: 'Mã sản phẩm' },
				{ data: 'name', title: 'Tên sản phẩm' },
				{
					data: 'img', title: 'Hình ảnh', render: function(data) {
						return `<img class="img-fluid" src="${data}" style="width: 64px;height: 64px;">`;
					}
				},
				{ data: 'countLike', title: 'Số lượt thích' },
				{ data: 'formattedPrice', title: 'Giá tiền' } // Sử dụng giá tiền đã định dạng
			]
		});
	};


	// Function to load all order data
	$scope.loadAllreportFavorites = function() {
		var url = "http://localhost:8080/rest/reportFavorites";
		$http.get(url).then(function(response) {
			$scope.reportFavorites = response.data.map(function(item) {
				return {
					id: item[0] ,
					name: item[1],
					img: $scope.url(item[2]),
					countLike: item[4],
					formattedPrice: formatCurrency(item[3] ) // Định dạng giá tiền
				};
			});
			console.log(response.data);
			$scope.initDataTable();
		}).catch(function(error) {
			console.log(error);
		});
	};

	// Hàm định dạng giá tiền thành chuỗi có dấu phẩy ngăn cách hàng nghìn và hai chữ số sau dấu phẩy
	function formatCurrency(amount) {
		return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
	}


	$scope.loadAllreportFavorites();
});
app.controller("report-order", function($scope, $http) {
	$scope.$on('$routeChangeSuccess', function(event, current, previous) {
		$scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Đơn Hàng');
	});
	var url5 = "http://localhost:8080/uploads/productImg";
	$scope.reportOrder = [];
	$scope.url = function(filename) {
		return `${url5}/${filename}`;
	}
	// Function to load all order data
	$scope.loadAllOrder = function() {
		var url = "http://localhost:8080/rest/reportOrder";
		$http.get(url).then(function(response) {
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
		}).catch(function(error) {
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
				{
					data: 'imgUrl', title: 'Hình ảnh', render: function(data) {
						return `<img class="img-fluid" src="${data}" style="width: 64px;height: 64px;">`;
					}
				},
				{ data: 'numberOfSales', title: 'Số lượng bán' }
			]
		});
	};

	$scope.loadAllOrder();
});

app.controller("report-customer", function($scope, $http, $filter) {
	let host = "http://localhost:8080/rest";
	$scope.$on('$routeChangeSuccess', function(event, current, previous) {
		$scope.page.setTitle(current.$$route.title || ' Tổng Hợp - Thống Kê | Khách Hàng');
	});
	$scope.dateNow = new Date();
	$scope.reportCustomer = [];



	// Function to load all customer data
	$scope.loadAllCustomer = function() {
		var url = `${host}/reportCustomerAll`;
		$http.get(url).then(function(response) {
			$scope.reportCustomer = response.data;
			// $scope.initDataTable();
			console.log(response.data);
		}).catch(function(error) {
			console.log(error);
		});
	};
	// Cập nhật dữ liệu trong DataTable hiện tại
	// $scope.updateDataTable = function (newData) {
	//     var table = $('#example2').DataTable();
	//     table.clear().rows.add(newData).draw();
	// };
	// Function to load customer report data based on date range

	$scope.dates = function() {
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
			.then(function(response) {
				// Success callback
				$scope.reportCustomer = response.data;
				console.log(response.data);
			})
			.catch(function(error) {
				// Error callback
				console.log(error);
			});

		console.log(dateS, dateE);
	};
	$scope.printPDF = function() {
		var headerTable = {
			table: {
				headerRows: 1,
				widths: ['auto', 'auto', 'auto', 'auto'],
				body: [
					['#', 'Tên Khách Hàng', 'Tổng Đơn Hàng', 'Tổng Tiền']
				]
			}
		};

		var bodyTable = {
			table: {
				widths: ['auto', 'auto', 'auto', 'auto'],
				body: $scope.reportCustomer.map((item, index) => [
					index + 1,
					item[0],
					item[1],
					formatCurrency(item[2])
				])
			}
		};

		var docDefinition = {
			content: [
				{ text: 'Báo cáo danh sách đơn hàng khách hàng', style: 'header' },
				' ',
				{ text: 'Ngày in: ' + formatDate(new Date()), alignment: 'right' },
				' ',
				headerTable,
				bodyTable
			],
			styles: {
				header: { fontSize: 18, bold: true, alignment: 'center' }
			}
		};

		pdfMake.createPdf(docDefinition).open();
		//...
	};



	$scope.exportExcel = function() {
		var data = [
			['BÁO CÁO - THỐNG KÊ THEO KHÁCH HÀNG'], // Header
			[], // Empty row for spacing
			['#', 'Tên Khách Hàng', 'Tổng Đơn Hàng', 'Tổng Tiền'],
			...$scope.reportCustomer.map((item, index) => [
				index + 1,
				item[0],
				item[1],
				formatCurrency(item[2])
			])
		];

		var ws = XLSX.utils.aoa_to_sheet(data);
		var wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, 'Danh sách đơn hàng');

		XLSX.writeFile(wb, 'danh_sach_don_hang.xlsx');
	};



	$scope.printDetail = function(username) {

		var url = `${host}/reportPrintCustomer/${username}`;

		$http.get(url)
			.then(function(response) {
				var invoiceData = response.data; // Dữ liệu chi tiết hóa đơn từ API
				var printWindow = window.open('', '_blank');
				var customerName = invoiceData[0][0];
				var totalAmount = 0;
				for (var i = 0; i < invoiceData.length; i++) {
					totalAmount += invoiceData[i][2]; // Cột 2 là tổng tiền đơn hàng
				}

				var content = `
                <!DOCTYPE html>
<html>
<head>
    <title>Chi tiết hóa đơn khách hàng</title>
    <!-- Liên kết đến tệp CSS của Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .invoice-container {
            margin: 0 auto;
            border: 1px solid #ccc;
            padding: 20px;
        }
        .left-column {
            text-align: left;
        }
        h2 {
            margin-bottom: 10px;
        }
        p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <div class="container invoice-container">
        <h2>Chi tiết hóa đơn khách hàng</h2> <p>Tên khách hàng: ${customerName}</p>
        <table class="table">
            <thead>
                <tr>
                    <th>Mã Hóa Đơn</th>
                   
                    <th>Tổng Tiền</th>
                    <th>Ngày Đặt Hàng</th>
                </tr>
            </thead>
            <tbody>
                ${invoiceData.map(item => `
                    <tr>
                        <td>${item[1]}</td>
                       
                        <td>${formatCurrency(item[2])}</td>
                        <td>${formatDate(new Date(item[3]))}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
        <p>Tổng tiền của tất cả đơn hàng: ${formatCurrency(totalAmount)}</p>
    </div>

    <!-- Liên kết đến các tệp JavaScript của Bootstrap và jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

                `;
				console.log("success", response.data)
				printWindow.document.write(content);
				printWindow.print();
			})
			.catch(function(error) {
				console.log(error);
			});
	};

	// Helper function to format date
	function formatDate(date) {
		if (!date) {
			return '';
		}
		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		return `${day}-${month}-${year}`;
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

