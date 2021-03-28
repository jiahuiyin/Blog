
var bloggerReward = "张张张张先森";

var deleteArticleId="";
var friendLinkId="";

$('.superAdminList .superAdminClick').click(function () {
    var flag = $(this).attr('class').substring(16);
    $('#statistics,#articleManagement,#articleThumbsUp,#articleCategories,#friendLink,#rewardManagement,#userFeedback,#privateWord').css("display","none");
    $("#" + flag).css("display","block");
});

//填充反馈信息
function putInAllFeedback(data) {
    var feedbackInfos = $('.feedbackInfos');
    feedbackInfos.empty();
    if(data['list'].length == 0){
        feedbackInfos.append('<div class="noNews">这里空空如也</div>');
    } else {
        $.each(data['list'], function (index, obj) {
            var feedbackInfo = $('<div class="feedbackInfo"></div>');
            feedbackInfo.append('<div class="feedbackInfoTitle">' +
                'uid:<span class="feedbackName">' + obj['personId'] + '</span>' +
                '<span class="feedbackTime">' + obj['feedbackDate'] + '</span>' +
                '</div>');
            feedbackInfo.append('<div class="feedbackInfoContent">' +
                '<span class="feedbackInfoContentWord">反馈内容：</span>' +
                obj['feedbackContent'] +
                '</div>');
            var feedbackInfoContact = $('<div class="feedbackInfoContact"></div>');
            if(obj['contactInfo'] !== ""){
                feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' +
                    obj['contactInfo']);
            } else {
                feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' + '无'
                );
            }
            feedbackInfo.append(feedbackInfoContact);
            feedbackInfos.append(feedbackInfo);
        });
        feedbackInfos.append($('<div class="my-row" id="page-father">' +
            '<div id="feedbackPagination">' +
            '<ul class="am-pagination  am-pagination-centered">' +
            '<li class="am-disabled"><a href="">&laquo; 上一页</a></li>' +
            '<li class="am-active"><a href="">1</a></li>' +
            '<li><a href="">2</a></li>' +
            '<li><a href="">3</a></li>' +
            '<li><a href="">4</a></li>' +
            '<li><a href="">5</a></li>' +
            '<li><a href="">下一页 &raquo;</a></li>' +
            '</ul>' +
            '</div>' +
            '</div>'));
    }

}
//填充文章管理
function putInArticleManagement(data) {

    var articleManagementTable = $('.articleManagementTable');
    articleManagementTable.empty();
    var table = $('<table class="table am-table am-table-bd am-table-striped admin-content-table  am-animation-slide-right"></table>');
    table.append($('<thead>' +
        '<tr>' +
        '<th>文章标题</th><th>发布时间</th><th>文章分类</th><th>浏览量</th><th>操作</th>' +
        '</tr>' +
        '</thead>'));
    var tables = $('<tbody class="tables"></tbody>');
    $.each(data['list'], function (index, obj) {
        tables.append($('<tr id="a' + obj['id'] + '"><td><a href="article/' +  obj['id'] + '">' + obj['articleTitle'] + '</a></td><td>' + obj['publishDate'] + '</td><td>' + obj['articleCategories'] + '</td> ' +
            '<td>' +
            '<div class="am-dropdown" data-am-dropdown>' +
            '<button class="articleManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
            '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
            '</div>' +
            '</td>' +
            '</tr>'));
    });
    table.append(tables);
    articleManagementTable.append(table);
    articleManagementTable.append($('<div class="my-row" id="page-father">' +
        '<div id="articleManagementPagination">' +
        '<ul class="am-pagination  am-pagination-centered">' +
        '</ul>' +
        '</div>' +
        '</div>'));

    $('.articleManagementBtn').click(function () {
        var $this = $(this);
        var id = $this.parent().parent().parent().attr("id").substring(1);
        window.location.replace("/editor?id=" + id);
    });
    $('.articleDeleteBtn').click(function () {
        var $this = $(this);
        deleteArticleId = $this.parent().parent().parent().attr("id").substring(1);
        $('#deleteAlter').modal('open');
    })
}
//填充点赞信息
function putInArticleThumbsUp(data) {
    var msgContent = $('.msgContent');
    msgContent.empty();
    if (data['result'].length == 0) {
        msgContent.append($('<div class="noNews">' +
            '这里空空如也' +
            '</div>'));
    } else {
        msgContent.append($('<div class="msgReadTop">' +
            '未读消息：<span class="msgIsReadNum">' + data['msgIsNotReadNum'] + '</span>' +
            '<a class="msgIsRead">全部标记为已读</a>' +
            '</div>'));
        $.each(data['result'], function (index, obj) {
            var msgRead = $('<div class="msgRead" id="p' + obj['id'] + '"></div>');
            if (obj['isRead'] == 1) {
                msgRead.append($('<span class="msgReadSign"></span>'));
            }
            msgRead.append($('<span class="am-badge msgType">点赞</span>'));
            msgRead.append($('<span class="msgHead"><a class="msgPerson">' + obj['praisePeople'] + '</a>点赞了你的博文</span>'));
            msgRead.append($('<div class="msgTxt">' +
                '<span><a class="articleTitle" href="/article/' + obj['articleId'] + '" target="_blank">' + obj['articleTitle'] + '</a></span>' +
                '<span class="msgDate">' + obj['likeDate'] + '</span>' +
                '</div>' +
                '<hr>'));
            msgContent.append(msgRead);
        });
        msgContent.append($('<div class="my-row" id="thumbsUpPage">' +
            '<div class="thumbsUpPagination">' +
            '</div>' +
            '</div>'))
    }
}



//删除文章
    $('.sureArticleDeleteBtn').click(function () {
        $.ajax({
            type: 'get',
            url: '/superAdmin/deleteArticle',
            dataType: 'json',
            data: {
                id: deleteArticleId
            },
            success: function (data) {
                if (data['status'] == 201) {
                    dangerNotice("删除文章失败")
                } else if (data['status'] == 103) {
                    dangerNotice(data['message'] + " 删除文章失败")
                } else {
                    successNotice("删除文章成功");
                    getArticleManagement(1);
                }
            },
            error: function () {
                alert("删除失败");
            }
        });
    });

//获得反馈信息
function getAllFeedback(currentPage) {
    $.ajax({
        type:'get',
        url:'/superAdmin/allFeedback',
        dataType:'json',
        data:{
            rows:10,
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得反馈失败")
            } else {
                putInAllFeedback(data['data']);
                scrollTo(0,0);//回到顶部

                //分页
                $("#feedbackPagination").paging({
                    rows:data['data']['pageSize'],//每页显示条数
                    pageNum:data['data']['pageNum'],//当前所在页码
                    pages:data['data']['pages'],//总页数
                    total:data['data']['total'],//总记录数
                    callback:function(currentPage){
                        getAllFeedback(currentPage);
                    }
                });
            }
        },
        error:function () {
            alert("获取反馈信息失败");
        }
    });
}
//获取统计信息
function getStatisticsInfo() {
    $.ajax({
        type:'get',
        url:'/superAdmin/statisticsInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获取统计信息失败")
            } else {
                $('.allVisitor').html(data['data']['allVisitor']);
                $('.yesterdayVisitor').html(data['data']['yesterdayVisitor']);
                $('.allUser').html(data['data']['allUser']);
                $('.articleNum').html(data['data']['articleNum']);
                if(data['data']['articleThumbsUpNum'] != 0){
                    $('.articleThumbsUp').find('a').append($('<span class="am-badge am-badge-warning am-margin-right am-fr articleThumbsUpNum">' + data['data']['articleThumbsUpNum'] + '</span>'));
                }
            }
        },
        error:function () {
            alert("获取统计信息失败");
        }
    });
}

function getArticleManagement(currentPage) {
    $.ajax({
        type:'get',
        url:'/superAdmin/articleManagement',
        dataType:'json',
        data:{
            rows:10,
            pageNum:currentPage
        },
        success:function (data) {

            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获取文章失败")
            } else {
                putInArticleManagement(data['data']);
                scrollTo(0,0);//回到顶部

                //分页
                $("#articleManagementPagination").paging({
                    rows:data['data']['pageSize'],//每页显示条数
                    pageNum:data['data']['pageNum'],//当前所在页码
                    pages:data['data']['pages'],//总页数
                    total:data['data']['total'],//总记录数
                    callback:function(currentPage){
                        getArticleManagement(currentPage);
                    }
                });
            }
        },
        // error:function () {
        //     alert("获取文章信息失败");
        // }
    });
}
//获得文章分类信息
function getArticleCategories() {
    $.ajax({
        type:'get',
        url:'/getArticleCategories',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得分类失败")
            } else {
                var categoryContent = $('.categoryContent');
                categoryContent.empty();
                categoryContent.append($('<div class="contentTop">' +
                    '目前分类数：<span class="categoryNum">' + data['data']['result'].length + '</span>' +
                    '<div class="updateCategory">' +
                    '<a class="addCategory"><i class="am-icon-plus-square"></i> 添加分类</a> / ' +
                    '<a class="subCategory"><i class="am-icon-minus-square"></i> 删除分类</a>' +
                    '</div>'));
                var categories = $('<div class="categories"></div>');
                $.each(data['data']['result'], function (index, obj) {
                    categories.append($('<span id="p' + obj['id'] + '" class="category">' + obj['categoryName'] + '</span>'));
                })
                categoryContent.append(categories);

                // 添加分类
                $('.addCategory').click(function () {
                    $('#addCategory').modal({
                        relatedTarget: this,
                        onConfirm: function(e) {
                            var categoryName = e.data;
                            categoryName = $.trim(categoryName);
                            if(categoryName == ""){
                                dangerNotice("分类名不能为空！");
                            }else {
                                updateCategory(categoryName, 1);
                            }
                        },
                        onCancel: function(e) {
                        }
                    });
                })

                // 删除分类
                $('.subCategory').click(function () {
                    $('#subCategory').modal({
                        relatedTarget: this,
                        onConfirm: function(e) {
                            var categoryName = e.data;
                            categoryName = $.trim(categoryName);
                            if(categoryName == ""){
                                dangerNotice("分类名不能为空！");
                            }else {
                                updateCategory(categoryName, 2);
                            }
                        },
                        onCancel: function(e) {
                        }
                    });
                })
            }
        },
        error:function () {

        }
    });
}

// 添加或者删除分类
function updateCategory(categoryName, type) {
    $.ajax({
        type:'post',
        url:'/updateCategory',
        dataType:'json',
        data:{
            categoryName:categoryName,
            type:type
        },
        success:function (data) {
            var categoryNum = $('.categoryNum').html();
            if(data['status'] == 401){
                $('.categories').append($('<span id="p' + data['data'] + '" class="category">' + categoryName + '</span>'));
                $('.categoryNum').html(++categoryNum);
                successNotice(data['message']);
            } else if(data['status'] == 103){
                dangerNotice(data['message'] + " 更新分类失败")
            } else if (data['status'] == 402 || data['status'] == 404 || data['status'] == 405){
               dangerNotice(data['message']);
            } else if (data['status'] == 403){
                var allCategories = $('.category');
                $('.categoryNum').html(--categoryNum);
                for(var i=0;i<allCategories.length;i++){
                    if(allCategories[i].innerHTML == categoryName){
                        allCategories[i].remove();
                    }
                }
                successNotice(data['message']);
            }
        },
        error:function () {
            alert("操作失败");
        }
    });
}
//点击反馈
$('.superAdminList .userFeedback').click(function () {
    getAllFeedback(1);
});
//点击文章管理
$('.superAdminList .articleManagement').click(function () {
    getArticleManagement(1);
});
//点击点赞管理
$('.superAdminList .articleThumbsUp').click(function () {
    getArticleThumbsUp(1);
});
//点击分类管理
$('.superAdminList .articleCategories').click(function () {
    getArticleCategories();
});
//点击友链管理
$('.superAdminList .friendLink').click(function () {
    $.ajax({
        type:'post',
        url:'/getFriendLink',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得友链失败")
            } else {
                var friendLinkContent = $('.friendLinkContent');
                friendLinkContent.empty();
                friendLinkContent.append($('<div class="contentTop">' +
                    '目前友链数：' +
                    '<span class="friendLinkNum">' + data['data'].length + '</span>' +
                    '<div class="updateFriendLink">' +
                    '<a class="addFriendLink"><i class="am-icon-plus-square"></i> 添加友链</a>' +
                    '</div>' +
                    '</div>'));
                var table = $('<table class="am-table am-table-bd am-table-striped admin-content-table  am-animation-slide-right"></table>');
                table.append($('<thead>' +
                    '<tr>' +
                    '<th>博主</th><th>博客地址</th><th>操作</th>' +
                    '</tr>' +
                    '</thead>'));
                var friendLinkManagementTable = $('<tbody class="friendLinkManagementTable"></tbody>');
                for(var i in data['data']){
                    var friendLink = $('<tr id="p' + data['data'][i]['id'] + '">' +
                        '<td class="blogger">' + data['data'][i]['blogger'] + '</td>' +
                        '<td class="url">' + data['data'][i]['url'] + '</td>' +
                        '<td>' +
                        '<div class="am-dropdown" data-am-dropdown="">' +
                        '<button class="friendLinkManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
                        '<button class="friendLinkDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
                        '</div>' +
                        '</td>' +
                        '</tr>');
                    friendLinkManagementTable.append(friendLink);
                }
                table.append(friendLinkManagementTable);
                friendLinkContent.append(table);

                //添加友链
                $('.addFriendLink').click(function () {
                    friendLinkId = "";
                    $('#addFriendLink').modal('open');
                    $('#blogger').val("");
                    $('#url').val("");
                });

                updateFriendLinkEditAndDelBtn();
            }
        },
        error:function () {
        }
    });
});
//点击募捐管理
$('.superAdminList .rewardManagement').click(function () {
    $.ajax({
        type:'post',
        url:'/getRewardInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if (data['status'] == 103){
                dangerNotice(data['message'] + " 获得募捐记录失败");
                return;
            }
            var rewardContent = $('.rewardContent');
            rewardContent.empty();
            rewardContent.append($('<div class="contentTop">募捐总金额：' +
                '<span class="rewardNum"> </span>元' +
                '<div class="updateReward">' +
                '<a class="addReward"><i class="am-icon-plus-square"></i> 添加募捐记录</a>' +
                '</div>' +
                '</div>'));
            var table = $('<table class="table am-table am-table-bd box-shadow am-table-hover"></table>');
            var thead = $('<thead>' +
                '<tr>' +
                '<th>昵称</th><th>募捐来源</th><th>募捐去处</th><th style="width: 60px;">金额</th><th class="am-hide-sm-down">备注</th><th>时间</th><th>操作</th>' +
                '</tr>' +
                '</thead>');
            var rewardTable = $('<tbody id="rewardTable"></tbody>');
            var rewardMoney = 0;
            if (data['data'].length > 0 && data['status'] == 0){
                $.each(data['data'], function (index,obj) {
                    var fundRaiser = obj['fundRaiser'];
                    var tr = $('<tr id=p' + obj['id'] +  '></tr>');
                    if(fundRaiser == bloggerReward){
                        tr.append($('<th>'+ fundRaiser +'<span class="is-me am-badge am-badge-danger am-radius am-round">?</span></th>'));
                    } else {
                        tr.append($('<th>'+ fundRaiser +'</th>'));
                    }
                    rewardMoney += obj['rewardMoney'];
                    tr.append($('<th>' + obj['fundRaisingSources'] + '</th>' +
                        '<th>'+ obj['fundraisingPlace'] + '</th>' +
                        '<th><i class="am-icon-cny">'+ obj['rewardMoney'] + '</i></th>' +
                        '<th class="am-hide-sm-down">'+ obj['remarks'] + '</th>' +
                        '<th>'+ timestampToYMDTime(obj['rewardDate']) + '</th>' +
                        '<th>' +
                        '<button type="button" class="deleteReward am-btn am-btn-warning am-btn-xs">删除</button>' +
                        '</th>'));
                    rewardTable.append(tr);
                })
            }
            table.append(thead);
            table.append(rewardTable);
            rewardContent.append(table);
            $('.rewardNum').html(rewardMoney.toFixed(2));

            //删除募捐记录
            updateRewardDelBtn();
            // $('.deleteReward').click(function () {
            //     removeRewardMoneyId = $(this).parent().parent().attr("id").substring(1);
            //     thisRewardMoney = $(this).parent().prev().prev().prev().find('.am-icon-cny').html();
            //     $('#deleteReward').modal('open');
            // })

            //添加募捐记录
            $('.addReward').click(function () {
                $('#addReward').modal('open');
            })
        },
        error:function () {
        }
    });
})

//获得仪表盘信息
getStatisticsInfo();

// 更新友链的编辑和删除按钮
function updateFriendLinkEditAndDelBtn() {
    //编辑友链
    $('.friendLinkManagementBtn').click(function () {
        $('#addFriendLink').modal('open');
        var $this = $(this).parent().parent().parent();
        var blogger = $this.find('.blogger').html();
        var url = $this.find('.url').html();
        friendLinkId = $this.attr('id').substring(1);
        $('#blogger').val(blogger);
        $('#url').val(url);
    });

    //删除友链
    $('.friendLinkDeleteBtn').click(function () {
        friendLinkId = $(this).parent().parent().parent().attr('id').substring(1);
        $('#deleteFriendLink').modal('open');
    });
}

// 更新募捐删除按钮
function updateRewardDelBtn() {
    $('.deleteReward').click(function () {
        removeRewardMoneyId = $(this).parent().parent().attr("id").substring(1);
        thisRewardMoney = $(this).parent().prev().prev().prev().find('.am-icon-cny').html();
        $('#deleteReward').modal('open');
    })
}

//时间转换为2019年7月13日
function timestampToYMDTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '年';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '月';
    D = date.getDate() + '日';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D;
}