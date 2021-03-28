
    //网站最后更新时间（版本更新需更改）
    var siteLastUpdateTime = '2021年2月25日';

    //网站开始时间
    var siteBeginRunningTime = '2021-02-19 20:00:00';

    // 广告上下滚动
    function getStyle(obj,name){
        if(obj.currentStyle)
        {
            return obj.currentStyle[name];
        }
        else
        {
            return getComputedStyle(obj,false)[name];
        }
    }
    function startMove(obj,json,doEnd){
        clearInterval(obj.timer);
        obj.timer=setInterval(function(){
            var oStop=true;
            for(var attr in json)
            {
                var cur=0;
                if(attr=='opacity')
                {
                    cur=Math.round(parseFloat(getStyle(obj,attr))*100);
                }
                else
                {
                    cur=parseInt(parseInt(getStyle(obj,attr)));
                }
                var speed=(json[attr]-cur)/6;
                speed=speed>0?Math.ceil(speed):Math.floor(speed);
                if(cur!=json[attr])
                {
                    oStop=false;
                }
                if(attr=='opacity')
                {
                    obj.style.filter='alpha(opacity:'+(speed+cur)+')';
                    obj.style.opacity=(speed+cur)/100;
                }
                else
                {
                    obj.style[attr]=speed+cur+'px';
                }
            }
            if(oStop)
            {
                clearInterval(obj.timer);
                if(doEnd) doEnd();
            }
        },30);
    }
    window.onload=function(){
        var oDiv=document.getElementsByClassName('roll')[0];
        var oUl=oDiv.getElementsByTagName('ul')[0];
        var aLi=oUl.getElementsByTagName('li');

        var now=0;
        for(var i=0;i<aLi.length;i++)
        {
            aLi[i].index=i;
        }

        function next(){
            now++;
            if(now==aLi.length)
            {
                now=0;
            }
            startMove(oUl,{top:-26*now})
        }
        //设置广播滚动时间
        var timer=setInterval(next,3000);
        oDiv.onmouseover=function(){
            clearInterval(timer);
        };
        oDiv.onmouseout=function(){
            timer=setInterval(next,3000);
        }
    };

    function putInNewLeaveWord(data) {
        var newLeaveWord = $('.new-leaveWord');
        newLeaveWord.empty();
        var listNews = $('<div data-am-widget="list_news" class="am-list-news am-list-news-default" ></div>');
        var newCommentTitle = $('<div class="am-list-news-hd am-cf">' +
            '<a class="newLeaveWord">' +
            '<h2 style="color: #110101">最新留言</h2>' +
            '</a>' +
            '</div>');
        listNews.append(newCommentTitle);
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        var ul = $('<ul class="fiveNewComments am-list"></ul>');
        $.each(data['list'], function (index, obj) {
            ul.append($('<li class="am-g am-list-item-dated">' +
                '<a class="newLeaveWordTitle" href="/ '+ obj['pageName']+ ' " title="' + obj['leaveWordContent'] + '">' + obj['answererUsername'] + '：' + obj['leaveMessageContent'] + '</a>\n' +
                '<span class="am-list-date">' + obj['leaveMessageDate'] + '</span>' +
                '</li>'));
        });
        amListNewsBd.append(ul);
        listNews.append(amListNewsBd);
        newLeaveWord.append(listNews);
        newLeaveWord.append($('<div class="my-row" id="page-father">' +
            '<div class="newLeaveWordPagination">' +
            '</div>' +
            '</div>'));
    }

    //填充文章
    function putInArticle(data) {
    $('.articles').empty();
    var articles = $('.articles');
    $.each(data["list"], function (index, obj) {
        if(index != (data.length) - 1){
            var center = $('<div class="center">' +
                '<header class="article-header">' +
                '<h1 itemprop="name">' +
                '<a class="article-title" href="/article/' + obj['id'] + '" target="_blank">' + obj['articleTitle'] + '</a>' +
                '</h1>' +
                '<div class="article-meta row">' +
                '<span class="articleType am-badge am-badge-success">' + obj['articleType'] + '</span>' +
                '<div class="articlePublishDate">' +
                '<i class="am-icon-calendar"><a class="linkColor" href="/archives?archive=' + obj['publishDate'] + '"> ' + obj['publishDate'] + '</a></i>' +
                '</div>' +
                '<div class="originalAuthor">' +
                '<i class="am-icon-user"> ' + obj['originalAuthor'] + '</i>' +
                '</div>' +
                '<div class="categories">' +
                '<i class="am-icon-folder"><a class="linkColor" href="/categories?category=' + obj['articleCategories'] + '"> ' + obj['articleCategories'] + '</a></i>' +
                '</div>' +
                '</div>' +
                '</header>' +
                '<div class="article-entry">' +
                obj['articleTabloid'] +
                '</div>' +
                '<div class="read-all">' +
                '<a href="/article/' + obj['id'] + '" target="_blank">阅读全文 <i class="am-icon-angle-double-right"></i></a>' +
                '</div>' +
                '<hr>' +
                '<div class="article-tags">' +

               '</div>' +
                '</div>');
            articles.append(center);
            var articleTagArray = $('.article-tags');
            for(var i=0;i<obj['articleTagArray'].length;i++){
                var articleTag = $('<i class="am-icon-tag"><a class="tag" href="/tags?tag=' + obj['articleTagArray'][i] + '"> ' + obj['articleTagArray'][i] + '</a></i>');
                articleTagArray.eq(index).append(articleTag);
            }
            // var likes = $('<span class="likes"><i class="am-icon-heart"> ' + obj['likes'] + '个喜欢</i></span>');
            // articleTagArray.eq(index).append(likes);
        }
    })

}


    //首页文章分页请求
    function ajaxFirst(currentPage) {
    //加载时请求
    $.ajax({
        type: 'GET',
        url: '/myArticles',
        dataType: 'json',
        data: {
            rows:"10",
            pageNum:currentPage
        },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得文章信息失败");
            } else {
                //放入数据
                putInArticle(data['data']);
                scrollTo(0,0);//回到顶部

                var length = data['data'].length;
                //分页
                $("#pagination").paging({
                    rows:data['data']['pageSize'],//每页显示条数
                    pageNum:data['data']['pageNum'],//当前所在页码
                    pages:data['data']['pages'],//总页数
                    total:data['data']['total'],//总记录数
                    callback:function(currentPage){
                        ajaxFirst(currentPage);
                    }
                });
            }
        },
        error: function () {
            alert("获得文章信息失败！");
        }
    });
}

    function newLeaveWordAjax(currentPage) {
        //最新留言
        $.ajax({
            type: 'GET',
            url: '/newLeaveWord',
            dataType: 'json',
            data: {
                rows:"5",
                pageNum:currentPage
            },
            success: function (data) {
                if(data['status'] == 103){
                    dangerNotice(data['message'] + " 获得最新留言失败")
                } else {
                    putInNewLeaveWord(data['data']);

                    //分页
                    $(".newLeaveWordPagination").paging({
                        rows:data['data']['pageSize'],//每页显示条数
                        pageNum:data['data']['pageNum'],//当前所在页码
                        pages:data['data']['pages'],//总页数
                        total:data['data']['total'],//总记录数
                        flag:0,
                        callback:function(currentPage){
                            newLeaveWordAjax(currentPage);
                        }
                    });
                }
            },
            error: function () {
            }
        });
    }


    ajaxFirst(1);

    // newCommentAjax(1);
    newLeaveWordAjax(1);


    //网站信息
    $.ajax({
        type: 'GET',
        url: '/siteInfo',
        dataType: 'json',
        data: {
        },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得网站信息失败")
            } else {
                var siteInfo = $('.site-info');
                siteInfo.empty();
                siteInfo.append('<h5 class="site-title">' +
                    '<i class="am-icon-info site-icon"></i>' +
                    '<strong style="margin-left: 15px">网站信息</strong>' +
                    '</h5>');
                var siteDefault = $('<ul class="site-default"></ul>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-file site-default-icon"></i><span class="site-default-word">文章总数</span>：' + data['data']['archivesNum'] + ' 篇' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-tags site-default-icon"></i><span class="site-default-word">标签总数</span>：' + data['data']['tagsNum'] + ' 个' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-commenting-o site-default-icon"></i><span class="site-default-word">分类总数</span>：' + data['data']['categoriesNum'] + ' 个' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-comments-o site-default-icon"></i><span class="site-default-word">留言总数</span>：' + data['data']['leaveWordNum'] + ' 条' +
                    '</li>');

                siteDefault.append('<li>' +
                    '<i class="am-icon-pencil-square site-default-icon"></i><span class="site-default-word">网站最后更新</span>：<span class="siteUpdateTime">' + siteLastUpdateTime + '</span>' +
                    '</li>');
                siteDefault.append('<li>' +
                    '<i class="am-icon-calendar site-default-icon"></i><span class="site-default-word">网站运行天数</span>：<span class="siteRunningTime"> </span>' +
                    '</li>');
                siteInfo.append(siteDefault);
            }
        },
        error: function () {
        }
    });

    //网站运行时间
    //beginTime为建站时间的时间戳
    function siteRunningTime(time) {
        var theTime;
        var strTime = "";
        if (time >= 86400){
            theTime = parseInt(time/86400);
            strTime += theTime + "天";
            time -= theTime*86400;
        }
        if (time >= 3600){
            theTime = parseInt(time/3600);
            strTime += theTime + "时";
            time -= theTime*3600;
        }
        if (time >= 60){
            theTime = parseInt(time/60);
            strTime += theTime + "分";
            time -= theTime*60;
        }
        strTime += time + "秒";

        $('.siteRunningTime').html(strTime);
    }

    var nowDate = new Date().getTime();
    //网站开始运行日期
    var oldDate = new Date(siteBeginRunningTime.replace(/-/g,'/'));
    var time = oldDate.getTime();
    var theTime = parseInt((nowDate-time)/1000);
    setInterval(function () {
        siteRunningTime(theTime);
        theTime++;
    },1000);