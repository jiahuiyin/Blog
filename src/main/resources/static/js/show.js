

    //填充文章
    function putInArticle(data) {
        document.getElementsByTagName("title")[0].innerText = data.articleTitle;
        $('.zhy-article-top').html('');
        $('.zhy-article-footer').html('');
        var articleTop = $('<article-top><div class="article-title">' +
            '<h1>' + data.articleTitle + '</h1>' +
            '</div>' +
            '<div class="article-info row">' +
            '<div class="article-info article-info-type am-badge am-badge-success">' +
            data.articleType +
            '</div>' +
            '<div class="article-info article-info-publishDate">' +
            '<i class="am-icon-calendar"><a class="articleCategoryColor" href="/archives?archive=' + data.publishDate + '"> ' + data.publishDate + '</a></i>' +
            '</div>' +
            '<div class="article-info article-info-originalAuthor">' +
            '<i class="am-icon-user"> ' + data.originalAuthor + '</i>' +
            '</div>' +
            '<div class="article-info article-info-categories">' +
            '<i class="am-icon-folder"> <a class="articleCategoryColor" href="/categories?category=' + data.articleCategories + '">' + data.articleCategories + '</a></i>' +
            '</div>' +
            '</div></article-top>');
        $('.zhy-article-top').append(articleTop);
        $("#mdText").text(data.articleContent);
        var wordsView;
        wordsView = editormd.markdownToHTML("wordsView", {
            htmlDecode: "true", // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,
            flowChart: true,
            sequenceDiagram: true
        });
        var articleFooter = $('<div class="end-logo">' +
            '<i class="am-icon-btn am-success am-icon-lg">完</i>' +
            '</div>' +
            '<div class="show-weixin">' +
            '<p><i class="weiXinQuoteLeft am-icon-quote-left "></i></p><br>' +
            '<p class="show-weixin-pic">' +
            '<img src="https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/static/img/weixin.jpg">' +
            '</p>' +
            '<p><i class="weiXinQuoteRight am-icon-quote-right "></i></p>' +
            '</div>' +
            '<div>' +
            '<ul class="post-copyright">' +
            '<li><strong>本文作者：</strong><span id="authorFooter">' + data.originalAuthor + '</span></li>' +
            '<li><strong>原文链接：</strong><span id="urlFooter"><a href="' + data.articleUrl + '">' + data.articleUrl + '</a></span></li>' +
            '<li><strong>版权声明：</strong> 本博客所有文章除特别声明外，均采用<span id="copyRightFooter"><a href="https://creativecommons.org/licenses/by/3.0/cn/" target="_blank"> CC BY 3.0 CN协议</a></span>进行许可。转载请署名作者且注明文章出处。</li>' +
            '</ul>' +
            '</div>' +
            '<div class="article-tags">' +

            '</div>' +
            '<hr>' +
            '<div class="two-article">' +
            '<span class="article-last">' +

            '</span>' +
            '<span class="article-next">' +
            '</span>' +
            '</div>');
        var tags = $('<div class="tags"></div>');
        for(var i=0;i<data.articleTagArray.length;i++){
            var tag = $('<i class="am-icon-tag"></i><a class="articleTagColor" href="/tags?tag=' + data.articleTagArray[i] + '"> ' + data.articleTagArray[i] + '</a>');
            tags.append(tag);
        }
        $('.article-tags').append(tags);
        if(data.lastStatus == "200"){
            var articleLast200 = $('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a class="lastAndNext" href="' + data.lastArticleUrl +'">' + data.lastArticleTitle + '</a>');
            $('.article-last').append(articleLast200);
        } else {
            var articleLast500 = $('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a  class="lastAndNext">' + data.lastInfo + '</a>');
            $('.article-last').append(articleLast500);
        }
        if(data.nextStatus == "200"){
            var articleNext200 = $('<a class="lastAndNext" href="' + data.nextArticleUrl +'">' + data.nextArticleTitle + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
            $('.article-next').append(articleNext200);
        } else {
            var articleNext500 = $('<a  class="lastAndNext">' + data.nextInfo + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
            $('.article-next').append(articleNext500);
        }


        $('.other').append($('<div class="social-share" data-initialized="true" data-url="https://www.zhyocean.cn/article/' + data.articleId  + '"  data-title="' + data.articleTitle + '">' +
            '<a href="#" class="social-share-icon icon-qq" data-am-popover="{content: \'分享至QQ好友\', trigger: \'hover focus\'}"></a>' +
            '<a href="#" class="social-share-icon icon-qzone" data-am-popover="{content: \'分享至QQ空间\', trigger: \'hover focus\'}"></a>' +
            '<a href="#" class="social-share-icon icon-wechat"></a>' +
            '<a href="#" class="social-share-icon icon-weibo" data-am-popover="{content: \'分享至微博\', trigger: \'hover focus\'}"></a>' +
            '</div>'))

        //选中所有需放大的图片加上data-src属性
        $("#wordsView img").each(function(index){
            if(!$(this).hasClass("emoji")){
                var a=$(this).attr('src');
                $(this).attr("data-src",a);

                $(this).addClass("enlargePicture");
            }
        });
        //放大图片框架
        lightGallery(document.getElementById('wordsView'));
    }



    function ajaxFirst() {

        var id="";
            var url = window.location.href;//获取url地址\
            var strs = url.split("/");


            var urlParmStr = strs[4];
            if (urlParmStr != "undefined") {
                id= urlParmStr;
            }


        //通过文章id请求文章信息
        $.ajax({
            type: 'get',
            url: '/article',
            dataType: 'json',
            async: false,
            data: {
                articleId: id,
            },
            success: function (data) {
                if (data['status'] == 0) {
                    putInArticle(data['data']);
                } else if (data['status'] == 103) {
                    dangerNotice(data['message'] + " 获得文章失败");
                } else {

                    $('.content').html('');
                    var error = $('<div class="article"><div class="zhy-article-top"><div class="error">' +
                        '<img src="https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/static/img/register_success.jpg">' +
                        '<p>没有找到这篇文章哦</p>' +
                        '<p>可能不小心被博主手残删掉了吧</p>' +
                        '<div class="row">' +
                        '<a href="/">返回首页</a>' +
                        '</div>' +
                        '</div></div></div>');
                    $('.content').append(error);
                }
            },
            error: function () {

            }
        });
    }
    ajaxFirst();
    // 文章点赞

