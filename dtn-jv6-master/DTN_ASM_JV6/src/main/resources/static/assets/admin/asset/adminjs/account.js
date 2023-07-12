let host5 = "http://localhost:8080/DTNsBike/rest";

var test5 = 0;
var key5 = null;
var form5 = {};
var url5 = "http://localhost:8080/DTNsBike/rest/uploads/accountImg";

// List tài khoản controller
app.controller("account-list", function ($scope, $http) {
    // alert("QUẢN LÝ TÀI KHOẢN");
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Danh sách tài khoản');
    });

    $scope.url = function (filename) {
        return `${url5}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host5}/accounts`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            // GET JSON ARRAY HEADERS.
            $scope.headers = Object.keys($scope.items[0]);
            $scope.columns = $scope.headers[6];
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
        var url = `${host5}/accounts/check/${id}`;

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

    $scope.delete = function (id) {
        $scope.checkPr(id);
        Swal.fire({
            position: 'top',
            title: 'Thông báo !',
            text: "Bạn chắc chắn muốn xóa tài khoản " + id + " này!?",
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
                        text: 'Bạn không thể xóa tài khoản này !?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host5}/accounts/${id}`;
                    $http.delete(url).then(resp => {
                        var index = $scope.items.findIndex(item => item.username == id);
                        $scope.items.splice(index, 1);

                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Xóa thành công!',
                        text: 'Đã xóa tài khoản ' + id,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }

    $scope.edit = function (id) {
        key5 = id;
        test5 = 1;
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

// Form cập nhật tài khoản controller
app.controller("account-form", function ($scope, $http) {
    // alert("QUẢN LÝ TÀI KHOẢN");
    $scope.$on('$routeChangeSuccess', function (event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Cập nhật tài khoản');
    });


    var url5 = "http://localhost:8080/DTNsBike/rest/uploads/accountImg";
    $scope.url = function (filename) {
        if (filename != null) {
            return `${url5}/${filename}`;
        } else {
            return 'asset/images/default.jpg';
        }
    }


    var loi = 0;


    if (test5 > 0) {
        $scope.showButton = false;
        $scope.showPass = false;
        test5 = 0;
    } else {
        $scope.showButton = true;
        $scope.showPass = true;
    }

    $scope.form = {};

    $scope.load = function () {
        if (key5 != null) {
            var url = `${host5}/accounts/${key5}`;
            $http.get(url).then(resp => {
                $scope.form = resp.data;
                form4 = $scope.form;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            key5 = null;
        } else {
            $scope.reset();

        }
    }
    $scope.upload = function (files) {
        var form = new FormData();
        form.append('files', files[0]);
        $http.post(url5, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.form.photo = resp.data.name;
            console.logo("Success", resp.data);
        }).catch(error => {
            console.log("Errors", error);
        })

    }

    $scope.reset = function () {
        $scope.form = {};
        $scope.showButton = true;
        $scope.showPass = true;
        $scope.loi = '';
        $scope.loi1 = '';
        $scope.loi2 = '';
        $scope.loi3 = '';
        $scope.loi4 = '';
        $scope.loi5 = '';
        $scope.loi6 = '';
    }
    $scope.checkEmail = function (email) {
        var regexEmail = /^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/;
        return String(email).search(regexEmail) != -1;
    }
    $scope.checkPhone = function (phone) {
        var regexPhone = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;
        return String(phone).search(regexPhone) != -1;
    }
    $scope.checkU = 1;
    $scope.checkUser = function (id) {
        var url = `${host5}/accounts/checkUser/${id}`;
        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == false) {
                $scope.checkU = 1;
            } else {
                $scope.checkU = 0;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }
    var checkE = 0;
    $scope.checkEmails = function (email) {
        var url = `${host5}/accounts/checkEmail/${email}`;
        $http.get(url).then(resp => {
            console.log("Success", resp)
             if (resp.data == false) {
                checkE = 0;
            } else {
                checkE = 1;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }
    var checkP = 0;
    $scope.checkPhones = function (phone) {
        var url = `${host5}/accounts/checkPhone/${phone}`;
        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == false) {
                checkP = 0;
            } else {
                checkP = 1;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.create = function () {
        $scope.check(true);
        var check = $scope.check(true);
        if (check == true) {
            var item = angular.copy($scope.form);
            var url = `${host5}/accounts`;
            $http.post(url, item).then(resp => {
                $scope.items.push(item);
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Thêm thành công!',
                text: 'Đã thêm tài khoản ' + item.username,
                icon: 'success'
            })
            $scope.reset();
            $scope.load();
        }
    }


    $scope.update = function () {
        $scope.check(false);
        if ($scope.check(false) == true) {
            var item = angular.copy($scope.form);
            var url = `${host5}/accounts/${$scope.form.username}`;
   
            $http.put(url, item).then(resp => {
                var index = $scope.items.findIndex(item => item.username == $scope.form.username);
                $scope.items[index] = resp.data;
                console.log("Success", resp)
            }).catch(error => {
                console.log("Error", error);
            });
            Swal.fire({
                position: 'top',
                title: 'Cập nhật thành công!',
                text: 'Đã cập nhật tài khoản ' + item.username,
                icon: 'success'
            }
            )
            $scope.reset();
            $scope.load();
        }


    }
   


    $scope.check = function (action) {

        var username = $scope.form.username;
        var fullname = $scope.form.fullname;
        var email = $scope.form.email;
        var phone = $scope.form.phone;
        var pass = $scope.form.password;
        var confirmPass = $scope.form.confirmPass;
        var active = $scope.form.active;

        $scope.checkUser(username);
        $scope.checkPhones(phone);
        $scope.checkEmails(email);


        if(action == true){
            if (username == null || username.length <= 0) {
            $scope.loi = '* Vui lòng nhập tên đăng nhập';
            loi++;
            } else if ($scope.checkU == 0) {
                $scope.loi = '* Tên đăng nhập đã tồn tại';
                loi++;
                $scope.checkU = 1;
            } else {
                $scope.loi = '';
            }
        }else{
             if (username == null || username.length <= 0) {
                $scope.loi = '* Vui lòng nhập tên đăng nhập';
                loi++;
            }  else {
                $scope.loi = '';
            }
        }
        if (fullname == null || fullname.length <= 0) {
            $scope.loi1 = '* Vui lòng nhập họ tên';
            loi++;
        } else {
            $scope.loi1 = '';
        }
        if (email == null || email.length <= 0) {
            $scope.loi2 = '* Vui lòng nhập email';
            loi++;
        } else if ($scope.checkEmail(email) == false) {
            $scope.loi2 = '* Định dạng email không hợp lệ';
            loi++;
        } 
        else if (checkE == 1) {
            $scope.loi2 = '* Email đã tồn tại';
            loi++;
            checkE = 0;
        } else {
            $scope.loi2 = '';
        }

        if (phone == null || phone.length <= 0) {
            $scope.loi3 = '* Vui lòng nhập số điện thoại';
            loi++;
        } else if (checkP == 1) {
            $scope.loi3 = '* Số điện thoại đã tồn tại';
            loi++;
             checkP = 0;
        } else if ($scope.checkPhone(phone) == false) {
            $scope.loi3 = '* Định dạng số điện thoại không hợp lệ';
            loi++;
        } else {
            $scope.loi3 = '';
        }

        if (action == true) {
            if (pass == null) {
                $scope.loi4 = '* Vui lòng nhập mật khẩu';
                loi++;
            } else if (pass.length < 3 || pass.length > 6) {
                $scope.loi4 = '* Vui lòng nhập mật khẩu từ 3 đến 6 ký';
                loi++;
            }
            else {
                $scope.loi4 = '';
            }
            if (confirmPass == null || confirmPass.length <= 0) {
                $scope.loi5 = '* Vui lòng nhập xác nhận mật khẩu';
                loi++;
            } else if (confirmPass != pass) {
                $scope.loi5 = '* Mật khẩu không trùng khớp';
                loi++;
            } else {
                $scope.loi5 = '';
            }
        }

        if (active == null || active.length <= 0 || active == undefined) {
            $scope.loi6 = '* Vui lòng chọn trạng thái';
            loi++;
        } else {
            $scope.loi6 = '';
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


// Phân quyền tài khoản controller
app.controller("account-auth",function($scope,$http){
    $scope.$on('$routeChangeSuccess', function(event, current, previous) {
        $scope.page.setTitle(current.$$route.title || ' Phân quyền tài khoản');
    });

    var loi = 0;
    $scope.form1 = {};
     $scope.load_Account = function () {
        var url = `${host5}/accounts`;
        $http.get(url).then(resp => {
            $scope.accounts = resp.data;
            $scope.form1 =  $scope.accounts;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }

     $scope.load_Roles = function () {
        var url = `${host5}/roles`;
        $http.get(url).then(resp => {
            $scope.roles = resp.data;
            $scope.form = $scope.roles;
            console.log("Success", resp)
        }).catch(error => {
            console.log("Error", error);
        });

    }
    $scope.loadName = function(id){
        var url = `${host5}/accounts/${id}`;
             $http.get(url).then(resp => {
                $scope.name = resp.data.username;
                console.log("Success", resp.data.username)
            })
    }
    $scope.loadNameRole = function(id){
        var url = `${host5}/roles/${id}`;
             $http.get(url).then(resp => {
                $scope.role = resp.data.name;
                console.log("Success", resp.data.name)
            })
    }
    var checkAc = 0;
    $scope.checkAcc = function (id,name) {
        var url = `${host5}/auths/check2/${id}/${name}`;
        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == true) {
                checkAc = 1;
            } else {
                checkAc = 0;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }
    $scope.save = function(id){

        if($scope.check()==true){
            $scope.checkAcc(id,$scope.name);
            Swal.fire({
            position: 'top',
            title: 'Thông báo !',
            text: "Phân quyền "+ $scope.role +" cho tài khoản " +  $scope.name + " này!?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                if (checkAc != 0) {
                    Swal.fire({
                        position: 'top',
                        title: 'Thông báo !',
                        text: 'Tài khoản '+ $scope.name +' đã có vai trò này!?',
                        icon: 'error'
                    })
                } else {
                    var item = angular.copy($scope.form1.account);
                    var url = `${host5}/auths/${id}`;
                    $http.put(url,item).then(resp => {
                        $scope.load_All();
                        console.log("Success", resp)
                    }).catch(error => {
                        console.log("Error", error);
                    });
                    Swal.fire({
                        position: 'top',
                        title: 'Cập nhật thành công!',
                        text: 'Đã cập nhật vai trò '+$scope.role+' cho tài khoản ' +  $scope.name,
                        icon: 'success'
                    }
                    )
                }
            }
        })
        }
    }
    var checkPrI = 0;
    $scope.checkPr = function (id) {
        var url = `${host5}/auths/check/${id}`;

        $http.get(url).then(resp => {
            console.log("Success", resp)
            if (resp.data == true) {
                checkPrI = 1;
            } else {
                checkPrI = 0;
            }
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.delete = function (id,role,user) {
        $scope.checkPr(id);
        Swal.fire({
            position: 'top',
            title: 'Thông báo !',
            text: "Thu hồi quyền "+role+" tài khoản " + user + " này!?",
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
                        title: 'Không thể thu hồi !',
                        text: 'Bạn không thể thu hồi quyền admin của tài khoản này!?',
                        icon: 'error'
                    })
                } else {
                    var url = `${host5}/auths/${id}`;
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
                        title: 'Thu hồi thành công!',
                        text: "Đã thu hồi quyền "+role+" của tài khoản " + user,
                        icon: 'success'
                    }
                    )
                }
            }
        })
    }


    $scope.check = function () {
        var cate = $scope.form1.account;
        var product = $scope.form.id;

        if (cate == null || cate.length <= 0 || cate == undefined) {
            $scope.loi = '* Vui lòng chọn tài khoản người dùng';
             loi++;
        } else {
            $scope.loi = '';
        }
        if (product == null || product.length <= 0 || product == undefined) {
            $scope.loi1 = '* Vui lòng chọn vai trò';
             loi++;
        } else {
            $scope.loi1 = '';
        }

        if(loi > 0){
            loi = 0;
            return false;
        }else{
            return true;
        }
        
    }
     $scope.url = function (filename) {
        return`${url5}/${filename}`;
    }

    $scope.items = [];
    $scope.headers = [];
    $scope.form = {};

    $scope.load_All = function () {
        var url = `${host5}/auths`;
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
            $scope.index +=1;
        }
    }

    $scope.last = function () {
        $scope.begin = ($scope.pageCount - 1) * $scope.pageSize;
    }

    $scope.load_All();

    $scope.load_Account();
    $scope.load_Roles();
});
