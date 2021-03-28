

function ajaxFirst(currentPage) {
    var keyword="";
    var url = window.location.href;//获取url地址\
    var strs= url.split("&");

    var urlParmStr = strs[0].split("=")[1];
    urlParmStr=decodeURI(urlParmStr);
    if (urlParmStr!="undefined"){
        keyword=urlParmStr;
    }

    //加载时请求
    $.ajax({
        type: 'get',
        url: '/query',
        dataType: 'json',
        data: {
            rows:"10",
            pageNum:currentPage,
            keyword:keyword
        },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得文章信息失败");
            } else {
                //放入数据
                putInArticle(data['data']);
                scrollTo(0,0);//回到顶部

            }
        },
        error: function () {
            alert("获得文章信息失败！");
        }
    });
}
ajaxFirst(1);


function putInArticle(data) {
    $('.articles').empty();
    var articles = $('.articles');
    if(data==null)
    {

        $('.my-row').empty();
        // var center = $('<div className="errorWords">'+
        //    ' <div className="error404Page">'+
        //       ' <img th:src="@{/img/errorpage/404.png}"/>'+
        //          '</div>'+
        //        ' <p><a th:href="@{/}"><u>返回首页</u></a><a href="#" style="margin-left: 30px"></a></p>'+
        //
        // '</div>');
        // articles.append(center);
        document.getElementById("errorWords").style.display="inline";
        return;
    }

    $.each(data, function (index, obj) {

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


    })

}