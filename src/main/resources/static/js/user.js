
$('.userList .clickLi').click(function () {
    var flag = $(this).attr('class').substring(8);
    $('#personalDate,#basicSetting,#commentMessage,#leaveMessage,#leaveWord,#privateWord').css("display","none");
    $("#" + flag).css("display","block");
});

$('.basicSetting').click(function () {
    $('#phone').val("");
    $('#authCode').val("");
    $('#password').val("");
    $('#surePassword').val("");
});
//
// function imgChange(e) {
//     var dom =$("input[id^='imgTest']")[0];
//     var reader = new FileReader();
//     reader.onload = (function (file) {
//         return function (e) {
//             $.ajax({
//                 type:'POST',
//                 url:'/uploadHead',
//                 dataType:'json',
//                 data:{
//                     img:this.result
//                 },
//                 success:function (data) {
//                     if(data['status'] == 101){
//                         $.get("/toLogin",function(data,status,xhr){
//                             window.location.replace("/login");
//                         });
//                     } else if(data['status'] == 103){
//                         dangerNotice(data['message'] + " 更改头像失败");
//                     } else {
//                         $('#headPortrait').attr("src",data['data']);
//                         successNotice("更改头像成功");
//                     }
//
//                 },
//                 error:function () {
//                 }
//             });
//         };
//     })(e.target.files[0]);
//     reader.readAsDataURL(e.target.files[0]);
// }
function imgChange() {
    var form = new FormData();
    form.append("file", document.getElementById("imgTest").files[0]);
    $.ajax({
        url: "/user/uploadHead",        //后台url
        data: form,
        cache: false,
        async: false,
        type: "POST",                   //类型，POST或者GET
        dataType: 'json',              //数据返回类型，可以是xml、json等
        processData: false,
        contentType: false,
        success: function (data) {
            if (data['status'] == 101) {

            } else if (data['status'] == 103) {
                dangerNotice(data['message'] + " 更改头像失败");
            } else {
                $('#headPortrait').attr("src", data['data']);
                successNotice("更改头像成功");
            }

        },
        error: function () {
        }
//
    });
}
//获得个人信息
function getUserPersonalInfo() {
    $.ajax({
        type:'get',
        url:'/user/userPersonalInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 101){
                // $.get("/toLogin",function(data,status,xhr){
                //     window.location.replace("/login");
                // });
            } else if (data['status'] == 103){
                dangerNotice(data['message'] + " 获得个人信息失败")
            } else {
                $('#username').attr("value",data['data']['username']);
                var personalPhone = data['data']['phone'];
                $('#personalPhone').html(personalPhone.substring(0,3) + "****" + personalPhone.substring(7));

                $('#birthday').attr("value",data['data']['birthday']);
                var gender = data['data']['gender'];
                if(gender == "male"){
                    $('.genderTable input').eq(0).attr("checked","checked");
                } else {
                    $('.genderTable input').eq(1).attr("checked","checked");
                }
                $('#email').attr("value",data['data']['email']);


                $('#headPortrait').attr("src",data['data']['avatarImgUrl']);
            }
        },
        error:function () {
        }
    });
}

//保存个人资料
var savePersonalDateBtn = $('#savePersonalDateBtn');
var username = $('#username');
var birthday = $('#birthday');
var gender = $('.genderTable input');
var email = $('#email');
savePersonalDateBtn.click(function () {
    var usernameValue = username.val();
    var genderValue = "male";
    if(usernameValue.length === 0){
        dangerNotice("昵称不能为空");
    } else if(!gender[0].checked && !gender[1].checked){
        dangerNotice("性别不能为空");
    } else {
        if(gender[0].checked){
            genderValue = "male";
        } else {
            genderValue = "female";
        }
        $.ajax({
            type:'post',
            url:'/user/savePersonalDate',
            dataType:'json',
            data:{
                username:username.val(),
                birthday:birthday.val(),
                gender:genderValue,
                email:email.val(),
            },
            success:function (data) {
                if(data['status'] == 101){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else if (data['status'] == 103){
                    dangerNotice(data['message'] + " 保存信息失败");
                }else{
                    $('#username').attr("value",data['data']['username']);
                    var personalPhone = data['data']['phone'];
                    $('#personalPhone').html(personalPhone.substring(0,3) + "****" + personalPhone.substring(7));

                    $('#birthday').attr("value",data['data']['birthday']);
                    var gender = data['data']['gender'];
                    if(gender == "male"){
                        $('.genderTable input').eq(0).attr("checked","checked");
                    } else {
                        $('.genderTable input').eq(1).attr("checked","checked");
                    }
                    $('#email').attr("value",data['data']['email']);


                    $('#headPortrait').attr("src",data['data']['avatarImgUrl']);
                }
            },
            error:function () {
            }
        });
    }
});

var phone = $('#phone');
var authCode = $('#authCode');
var password = $('#password');
var surePassword = $('#surePassword');

phone.blur(function () {
    var pattren = /^1[345789]\d{9}$/;
    var phoneValue = phone.val();
    if(pattren.test(phoneValue)){
        phone.removeClass("wrong");
        phone.addClass("right");
    } else {
        phone.removeClass("right");
        phone.addClass("wrong");
    }
});
phone.focus(function () {
    $('.notice').css("display","none");
});

// 定义发送时间间隔(s)
var my_interval;
my_interval = 60;
var timeLeft = my_interval;
//重新发送计时函数
var timeCount = function() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $('#authCodeBtn').html(timeLeft + "秒重新发送");
            timeCount();
        } else {
            $('#authCodeBtn').html("重新发送");
            timeLeft=60;
            $("#authCodeBtn").attr('disabled',false);
        }
    }, 1000);
};
//发送短信验证码
$('#authCodeBtn').click(function () {
    $('.notice').css("display","none");
    $('#authCodeBtn').attr('disabled',true);
    var phoneLen = phone.val().length;
    if(phoneLen == 0){
        dangerNotice("手机号不能为空");
        $('#authCodeBtn').attr('disabled',false);
    } else {
        if(phone.hasClass("right")){
            $.ajax({
                type:'post',
                url:'/getCode',
                dataType:'json',
                data:{
                    phone:$('#phone').val(),
                    sign:"changePassword"
                },
                success:function (data) {
                    if(parseInt(data['status']) == 0) {
                        successNotice("短信验证码发送成功");
                        timeCount();
                    } else {
                        dangerNotice("短信验证码发送异常")
                    }
                },
                error:function () {
                }
            });
        } else {
            dangerNotice("手机号不正确");
            $('#authCodeBtn').attr('disabled',false);
        }
    }

});

//修改密码
$('#changePasswordBtn').click(function () {
    $('.notice').css("display","none");
    if(phone.val().length === 0){
        dangerNotice("手机号不能为空");
    } else if (phone.hasClass("wrong")){
        dangerNotice("手机号不正确");
    } else if (authCode.val().length === 0){
        dangerNotice("验证码不能为空");
    } else if (password.val().length === 0){
        dangerNotice("新密码不能为空");
    } else if (surePassword.val().length === 0){
        dangerNotice("确认密码不能为空");
    } else{
        if (password.val() !== surePassword.val()){
            dangerNotice("确认密码不正确");
        } else {
            $.ajax({
                type:'post',
                url:'/changePassword',
                dataType:'json',
                data:{
                    phone:phone.val(),
                    authCode:authCode.val(),
                    newPassword:password.val()
                },
                success:function (data) {
                    if(data['status'] == 902){
                        dangerNotice("验证码不正确")
                    }else if (data['status'] == 506){
                        dangerNotice("手机号不存在")
                    }else if (data['status'] == 103){
                        dangerNotice(data['message'] + " 密码修改失败")
                    }else if(data['status'] == 901){
                        dangerNotice("手机号不正确");
                    } else {
                        successNotice("密码修改成功,请重新登录");
                        setTimeout(function () {
                            location.reload();
                        },3000);
                    }
                },
                error:function () {
                    dangerNotice("修改密码失败");
                }
            })
        }
    }
});

//填充用户留言
function putInLeaveWordInfo(data) {
    var msgContent = $('.msgContent');
    msgContent.empty();
    if(data['list'].length == 0){
        msgContent.append($('<div class="noNews">' +
            '这里空空如也' +
            '</div>'));
    } else {

        $.each(data['list'], function (index, obj) {
            var msgRead = $('<div id="p' + obj['id'] + '" class="msgRead"></div>');

            msgRead.append($('<span class="am-badge msgType">留言</span>'));
            var msgHead = $('<span class="msgHead"></span>');

            msgHead.append($('<span class="msgHead">'+ obj['leaveMessageContent'] +
                '</span>'));

            msgHead.append($('<a href="/' + obj['pageName'] + '#p' + obj['id'] + '" target="_blank" class="msgHref">戳这里去看看</a>'));
            msgRead.append(msgHead);
            msgRead.append($('<span class="msgDate">' + obj['leaveMessageDate'] + '</span><hr>'));
            msgContent.append(msgRead);
        })
        msgContent.append($('<div class="my-row" id="leaveWordPage">' +
            '<div class="leaveWordPagePagination">' +
            '</div>' +
            '</div>'))
    }




}
//获得留言
function getUserLeaveWord(currentPage) {
    $.ajax({
        type:'get',
        url:'/user/userLeaveWord',
        dataType:'json',
        data:{
            rows:"10",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 101){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            } else if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得留言信息失败");
            } else {
                putInLeaveWordInfo(data['data']);
                scrollTo(0,0);//回到顶部

                //分页
                $(".leaveWordPagePagination").paging({
                    rows:data['data']['pageSize'],//每页显示条数
                    pageNum:data['data']['pageNum'],//当前所在页码
                    pages:data['data']['pages'],//总页数
                    total:data['data']['total'],//总记录数
                    callback:function(currentPage){
                        getUserLeaveWord(currentPage);
                    }
                });
            }
        },
        error:function () {
            alert("获取留言信息失败");
        }
    })
}


//点击留言管理
$('.leaveWord').click(function () {
    getUserLeaveWord(1);
});

getUserPersonalInfo();

