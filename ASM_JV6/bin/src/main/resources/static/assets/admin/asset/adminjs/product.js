let host4 = "http://localhost:8080/rest";

var test4 = 0;
var key4 = null;
var cllist = null;
var form4 = {};
var url4 = "http://localhost:8080/uploads/productImg";
var url5 = "http://localhost:8080/uploadmulti/productDtImg";


// List sản phẩm controller
app.controller("product-list", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách sản phẩm');
    });


    $scope.url = function (filename) {
        return `${url4}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host4}/products`;
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
        var url = `${host4}/products/check/${id}`;

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
            title: 'Thông báo !',
            text: "Bạn chắc chắn muốn xóa sản phẩm " + name + " này!?",
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
                        text: 'Bạn không thể xóa sản phẩm này !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host4}/products/${id}`;
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
                        text: 'Đã xóa sản phẩm ' + name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key4 = id;
        test4 = 1;
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

// Form sản phẩm controller
app.controller("product-form", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Form Sản Phẩm');
    });

    var url5 = "http://localhost:8080/rest/uploadmulti/productDtImg";

    var url4 = "http://localhost:8080/rest/uploads/productImg";
    $scope.url = function (filename) {
        if (filename != null) {
            return `${url4}/${filename}`;
        } else {
            return 'asset/images/default.jpg';
        }
    }

    $scope.urldt = function (filename) {
        if (filename != null) {
            return `http://localhost:8080/rest/uploads/productDtImg/${filename}`;
        } else {
            return 'asset/images/default.jpg';
        }
    }


    var loi = 0;
    $scope.load_Cate = function () {
        var url = `${host4}/categories`;
        $http.get(url).then(resp => {
            $scope.cates = resp.data;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.load_Brand = function () {
        var url = `${host4}/brands`;
        $http.get(url).then(resp => {
            $scope.brands = resp.data;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.pdcolor = {};
    $scope.colorlist = [];

    // $scope.getColorlist = function () {
    //     var url = `${host4}/colors`;
    //     $http.get(url).then(resp => {
    //         $scope.colorlist = resp.data.map(color => {
    //             color.isChecked = false;
    //             return color;
    //         });
    //     }).catch(error => {
    //         console.log("Error", error);
    //         return null;
    //     });


    // }

    $scope.load_ColorList = function () {


    }

    $scope.toggleInput = function (cl) {
        console.log(cl.isChecked);
        if (!cl.isChecked) {
            cl.quantity = null;
        }
    }




    if (test4 > 0) {
        $scope.showButton = false;
        test4 = 0;
    } else {
        $scope.showButton = true;
    }

    $scope.form = {};
    $scope.imgdt = [];

    $scope.load = function () {
        if (key4 != null) {
            var url = `${host4}/products/${key4}`;
            var url2 = `${host4}/pdimg/${key4}`;
            var url3 = `${host4}/pdcl/${key4}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;

                $http.get(url2).then(resp => {//Product image
                    $scope.imgdt = resp.data;
                    console.log("Success", resp)
                }).catch(error => {
                    console.log("Error", error);
                });
                $http.get(url3).then(resp => {// product colors
                    $scope.pdcolor = resp.data;

                    //load color list 
                    var urlcl = `${host4}/colors`;
                    $http.get(urlcl).then(resp => {
                        $scope.colorlist = resp.data.map(color => {
                            color.isChecked = false;
                            return color;
                        });

                        $scope.pdcolor.forEach((pdColorItem) => {
                            const colorId = pdColorItem.color.id;
                            const existingColor = $scope.colorlist.find((colorItem) => colorItem.id === colorId);
                            if (existingColor) {
                                existingColor.quantity = pdColorItem.qty;
                                existingColor.isChecked = true;
                            } else {
                                $scope.colorlist.push({
                                    id: colorId,
                                    name: pdColorItem.color.name,
                                    hex: pdColorItem.color.hex,
                                    isChecked: false,
                                    quantity: pdColorItem.qty,
                                });
                            }
                        });

                         console.log($scope.pdcolor, $scope.colorlist)
                    }).catch(error => {
                        console.log("Error", error);
                    });




                }).catch(error => {
                    console.log("Error", error);
                });

                form4 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key4 = null;
        } else {
            //load color list 
            var urlcl = `${host4}/colors`;
            $http.get(urlcl).then(resp => {
                $scope.colorlist = resp.data.map(color => {
                    color.isChecked = false;
                    return color;
                });
            }).catch(error => {
                console.log("Error", error);
            });
            $scope.reset();

        }
    }
    $scope.upload = function (files) {
        var form = new FormData();
        form.append('files', files[0]);
        $http.post(url4, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.form.img = resp.data.name;
            console.log("Success", resp.data);
        }).catch(error => {
            console.log("Errors", error);
        })

    }



    // $scope.datalist = [];

    // $scope.updateDataList = function (cl) {
    //     var inputId = cl.id + "input";
    //     var inputElement = document.getElementById(inputId);
    //     if (inputElement) {
    //         var quantityValue = parseInt(inputElement.value);
    //         cl.quantity = quantityValue;
    //     }

    //     var checkElement = document.getElementById(cl.id);

    //     if (checkElement.checked && $scope.datalist.indexOf(cl.id) === -1) {
    //         $scope.datalist.push({ id: cl.id, quantity: cl.quantity });
    //     } else if (!checkElement.checked && $scope.datalist.indexOf(cl.id) !== -1) {
    //         const index = $scope.datalist.indexOf(cl.id);
    //         $scope.datalist.splice(index, 1);
    //     } else {    
    //         console.log('sai rồi');
    //     }
    //     console.log($scope.datalist);
    // };


    $scope.imgdts = function (files) {
        var form = new FormData();
        for (var i = 0; i < files.length; i++) {
            form.append("files", files[i]);
        }

        $http.post(url5, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.imgdt.push(...resp.data);
            console.log("Success", resp.data);
        }).catch(error => {
            console.log("Errors", error);
        })

    }


    $scope.deleteImage = function (imageName) {
        var index = -1;
        for (var i = 0; i < $scope.imgdt.length; i++) {
            if ($scope.imgdt[i].name === imageName) {
                index = i;
                break;
            }
        }

        if (index !== -1) {
            $scope.imgdt.splice(index, 1);
            console.log("Delete success");
        }
    };


    $scope.reset = function () {
        $scope.form = {};
        $scope.showButton = true;
        $scope.imgdt = [];
        $scope.loi = '';
        $scope.loi1 = '';
        $scope.loi2 = '';
        $scope.loi3 = '';
        $scope.loi4 = '';
        $scope.loi5 = '';
        $scope.loi6 = '';
        $scope.loi7 = '';
    }

    $scope.create = function () {
        // var imglist = angular.copy($scope.imgdt);
        // var data = [];
        // for (var i = 0; i < imglist.length; i++) {
        //     var item = $scope.imgdt[i];
        //     for (var key in item) {
        //         if (key.startsWith('name')) {
        //             data.push(item[key]);
        //         }
        //     }
        // }
        // console.log(data, imglist, $scope.imgdt)
        // var cllist = angular.copy($scope.colorlist)
        //         var cldata = cllist.filter((item) => item.isChecked === true)
        //             .map(({ id, quantity }) => ({ id, quantity }));;

        //         var clsjon = JSON.stringify(cldata);
        //         console.log(cldata);
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host4}/products`;
            $http.post(url, item).then(resp => {
                // $scope.items.push(item);
                var imglist = angular.copy($scope.imgdt);
                var data = [];
                for (var i = 0; i < imglist.length; i++) {
                    var item = $scope.imgdt[i];
                    for (var key in item) {
                        if (key.startsWith('name')) {
                            data.push(item[key]);
                        }
                    }
                }

                $http.post(`${host4}/pdimg/` + resp.data.id, data).then(resp => {
                    console.log("Success - Product img", resp)
                }).catch(error => {
                    console.log("Error", error);
                });

                var cllist = angular.copy($scope.colorlist)
                var cldata = cllist.filter((item) => item.isChecked === true)
                    .map(({ id, quantity }) => ({ id, quantity }));

                var clsjon = JSON.stringify(cldata);

                $http.post(`${host4}/pdcolor/` + resp.data.id, clsjon, {
                    headers: { 'Content-Type': 'application/json' }
                }).then(resp => {
                    console.log("Success - Product Color", resp)
                }).catch(error => {
                    console.log("Error", error);
                });

                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm thành công!',
                text: 'Đã thêm sản phẩm ' + item.name,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }

    $scope.update = function () {

    
        if ($scope.check() == true) {
            var item = angular.copy($scope.form);
            var url = `${host4}/products/${$scope.form.id}`;
            var url2 = `${host4}/pdimg/${$scope.form.id}`;
            var url3 = `${host4}/pdcolor/${$scope.form.id}`;

            $http.put(url, item).then(resp => {
                // var index = $scope.items.findIndex(item => item.id == $scope.form.id);
                // $scope.items[index] = resp.data;

                var imglist = $scope.imgdt;
                var data = [];
                for (var i = 0; i < imglist.length; i++) {
                    var item = $scope.imgdt[i];
                    for (var key in item) {
                        if (key.startsWith('name')) {
                            data.push(item[key]);
                        }
                    }
                }
                $http.post(url2, data).then(resp => { //uodate product image
                    console.log("Success", resp)
                }).catch(error => {
                    console.log("Error", error);
                });

                var cllist = angular.copy($scope.colorlist)
                var cldata = cllist.filter((item) => item.isChecked === true)
                    .map(({ id, quantity }) => ({ id, quantity }));
                
                var clsjon = JSON.stringify(cldata);

                $http.put(url3, clsjon, {
                    headers: { 'Content-Type': 'application/json' }
                }).then(resp => {
                    console.log("Success - Update pdcl", resp)
                }).catch(error => {
                    console.log("Error", error);
                });

                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật thành công!',
                text: 'Đã cập nhật sản phẩm ' + item.name,
                icon: 'success'
            }
            )
        }


    }
    $scope.check = function () {
        var name = $scope.form.name;
        var price = $scope.form.price;
        var qty = $scope.form.qty;
        var sale = $scope.form.sale;
        var madein = $scope.form.madein;
        var cate = $scope.form.catePro;
        var brand = $scope.form.brandPro;
        var available = $scope.form.avaliable;


        if (name == null || name.length <= 0) {
            $scope.loi = '* Không được bỏ trống tên sản phẩm';
            loi++;
        } else {
            $scope.loi = '';
        }
        if (price == null || price.length <= 0) {
            $scope.loi1 = '* Không được bỏ trống đơn giá';
            loi++;
        } else if (price <= 0) {
            $scope.loi1 = '* Đơn giá phải lớn hơn 0';
            loi++;
        } else {
            $scope.loi1 = '';
        }
        // if (qty == null || qty.length <= 0) {
        //     $scope.loi2 = '* Không được bỏ trống số lượng';
        //      loi++;
        // } 
        // else if(qty <= 0){
        //     $scope.loi2 = '* Số lượng phải lớn hơn 0';
        //      loi++;
        // }
        // else if{
        //     $scope.loi2 = '';
        // }
        if (sale == null || sale.length <= 0) {
            $scope.loi3 = '* Không được bỏ trống giảm giá';
            loi++;
        } else if (sale < 0 || sale > 40) {
            $scope.loi3 = '* Giảm giá phải từ 0 đến 40 %';
            loi++;
        } else {
            $scope.loi3 = '';
        }
        if (madein == null || madein.length <= 0) {
            $scope.loi4 = '* Không được bỏ trống xuất xứ';
            loi++;
        } else {
            $scope.loi4 = '';
        }
        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi5 = '* Vui lòng chọn danh mục sản phẩm';
            loi++;
        } else {
            $scope.loi5 = '';
        }
        if (brand == null || brand.length <= 0 || brand == undefined) {
            $scope.loi6 = '* Vui lòng chọn thương hiệu sản phẩm';
            loi++;
        } else {
            $scope.loi6 = '';
        }
        if (available == null || available.length <= 0 || available == undefined) {
            $scope.loi7 = '* Vui lòng chọn trạng thái';
            loi++;
        } else {
            $scope.loi7 = '';
        }

        if (loi > 0) {
            return false;
            loi = 0;
        } else {
            return true;
        }
    }





    $scope.load();

    $scope.load_Cate();
    $scope.load_Brand();
    $scope.load_ColorList();
});

// Update danh mục sản phẩm controller
app.controller("product-category", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Update Danh Mục');
    });

    var loi = 0;
    $scope.form1 = {};
    $scope.load_Cate = function () {
        var url = `${host4}/categories`;
        $http.get(url).then(resp => {
            $scope.cates = resp.data;
            $scope.form1 = $scope.cates;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

    $scope.load_Products = function () {
        var url = `${host4}/products`;
        $http.get(url).then(resp => {
            $scope.products = resp.data;
            $scope.form = $scope.products;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.loadName = function (id) {
        var url = `${host4}/productss/${id}`;
        $http.get(url).then(resp => {
            $scope.name = resp.data.name;
            console.log("Success", resp.data.name)
        })
    }

    $scope.save = function (id) {
        if ($scope.check() == true) {
            Swal.fire({
                position: 'top',
                title: 'Thông báo !',
                text: "Cập nhật danh mục cho sản phẩm " + $scope.name + " này!?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes !'
            }).then((result) => {
                if (result.isConfirmed) {
                    var item = angular.copy($scope.form1.catePro);
                    var url = `${host4}/productss/${id}`;
                    $http.put(url, item).then(resp => {
                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Cập nhật thành công!',
                        text: 'Đã cập nhật ' + $scope.name,
                        icon: 'success'
                    }
                    )
                }
            })
        }
    }

    $scope.check = function () {
        var cate = $scope.form1.catePro;
        var product = $scope.form.id;

        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi = '* Vui lòng chọn danh mục sản phẩm';
            loi++;
        } else {
            $scope.loi = '';
        }
        if (product == null || product.length <= 0 || product == undefined) {
            $scope.loi1 = '* Vui lòng chọn sản phẩm';
            loi++;
        } else {
            $scope.loi1 = '';
        }

        if (loi > 0) {
            loi = 0;
            return false;
        } else {
            return true;
        }

    }
    $scope.url = function (filename) {
        return `${url4}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host4}/products`;
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

    $scope.load_Cate();
    $scope.load_Products();
});
// Update danh mục sản phẩm controller
app.controller("product-img", function ($scope, $http) {
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Update Ảnh Sản Phẩm');
    });

    var loi = 0;
    $scope.form1 = {};
    $scope.load_Cate = function () {
        var url = `${host4}/categories`;
        $http.get(url).then(resp => {
            $scope.cates = resp.data;
            $scope.form1 = $scope.cates;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

    $scope.load_Products = function () {
        var url = `${host4}/products`;
        $http.get(url).then(resp => {
            $scope.products = resp.data;
            $scope.form = $scope.products;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.loadName = function (id) {
        var url = `${host4}/productss/${id}`;
        $http.get(url).then(resp => {
            $scope.name = resp.data.name;
            console.log("Success", resp.data.name)
        })
    }

    $scope.save = function (id) {
        if ($scope.check() == true) {
            Swal.fire({
                position: 'top',
                title: 'Thông báo !',
                text: "Cập nhật danh mục cho sản phẩm " + $scope.name + " này!?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes !'
            }).then((result) => {
                if (result.isConfirmed) {
                    var item = angular.copy($scope.form1.catePro);
                    var url = `${host4}/productss/${id}`;
                    $http.put(url, item).then(resp => {
                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Cập nhật thành công!',
                        text: 'Đã cập nhật ' + $scope.name,
                        icon: 'success'
                    }
                    )
                }
            })
        }
    }

    $scope.check = function () {
        var cate = $scope.form1.catePro;
        var product = $scope.form.id;

        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi = '* Vui lòng chọn danh mục sản phẩm';
            loi++;
        } else {
            $scope.loi = '';
        }
        if (product == null || product.length <= 0 || product == undefined) {
            $scope.loi1 = '* Vui lòng chọn sản phẩm';
            loi++;
        } else {
            $scope.loi1 = '';
        }

        if (loi > 0) {
            loi = 0;
            return false;
        } else {
            return true;
        }

    }
    $scope.url = function (filename) {
        return `${url4}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host4}/products`;
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

    $scope.load_Cate();
    $scope.load_Products();
});