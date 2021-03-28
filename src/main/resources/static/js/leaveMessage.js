

    function putInLeaveMessage(data) {

        $('#comment').val('');
        var commentBottom = $('.comment-bottom');
        commentBottom.empty();
        if (data.length == 0) {
            var comments = $('<div class="comments">' +
                '<span class="noComment" style="color: black">还没有评论，快来抢沙发吧！</span>' +
                '</div>');
            commentBottom.append(comments);
        } else {
            var articleComment = $('<div class="article-comment"></div>');
            articleComment.append($('<div class="article-comment-top">' +
                '<span class="article-comment-word">留言</span>' +
                '<div class="article-comment-line"></div>' +
                '</div>' +
                '<div class="new-comment">' +
                '<i class="all-comment am-icon-ellipsis-v"></i>全部留言（<span class="leaveMessageNum">' + '' + '</span>）' +
                '</div>'));
            var allComments = $('<div class="all-comments"></div>');
            $.each(data, function (index, obj) {

                if(obj['pid'] != 0)
                    return true;
                var visitorComment = $('<div class="visitorComment" id="p' + obj['id'] + '"></div>');
                var amG = $('<div class="am-g" style="margin: 0"></div>');
                amG.append('<div class="visitorCommentImg am-u-sm-2 am-u-lg-1">' +
                    '<img src="' + obj['avatarImgUrl'] + '">' +
                    '</div>');
                var amUSm10 = $('<div class="am-u-sm-10 am-u-lg-11"></div>');
                amUSm10.append('<div class="visitorInfo">' +
                    '<span class="visitorFloor">#' + (data.length - index) + '楼</span>' +
                    '<span class="visitorName">' +
                    obj['answererUsername'] +
                    '</span>' +
                    '<span class="visitorPublishDate">' +
                    obj['leaveMessageDate'] +
                    '</span>' +
                    '</div>' +
                    '<div class="visitorSay">' +
                    obj['leaveMessageContent'] +
                    '</div>');

                var toolGroup1 = $('<div class="tool-group">' +

                    '<a>' +
                    '<i class="reply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                    '</a>' +
                    '</div>');


                amUSm10.append(toolGroup1);

                var subComment = $('<div class="sub-comment"></div>');
                var subCommentList = $('<div class="sub-comment-list"></div>');
                var visitorReplies = $('<div class="visitorReplies"></div>');
                $.each(obj['replies'], function (index1, obj1) {
                    var visitorReply = $('<div id="p' + obj1['id'] + '" class="visitorReply"></div>');
                    var visitorReplyWords = $('<div class="visitorReplyWords">' +
                        '<a class="answerer">' + obj1['answererUsername'] + '</a> ：' + obj1['leaveMessageContent'] +
                        '</div>');
                    var visitorReplyTime = $('<div class="visitorReplyTime">' +
                        '<span class="visitorReplyTimeTime">' + obj1['leaveMessageDate'] + '</span>' +
                        +
                            '</div>');
                    visitorReply.append(visitorReplyWords);
                    visitorReply.append(visitorReplyTime);
                    visitorReply.append($('<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed"/>'));
                    visitorReplies.append(visitorReply);
                });
                var moreComment = $('<div class="more-comment">' +
                    '<a>' +
                    '<i class="moreComment am-icon-edit"> 添加新评论</i>' +
                    '</a>' +
                    '</div>');
                subCommentList.append(visitorReplies);
                subCommentList.append(moreComment);
                if (obj['replies'].length != 0) {
                    subComment.append(subCommentList);
                }
                subComment.append($('<div class="reply-sub-comment-list am-animation-slide-bottom">' +
                    '<div class="replyWord">' +
                    '<div class="replyWordBtn">' +
                    '<textarea class="replyWordTextarea" placeholder="写下你的评论..."></textarea>' +
                    '<button type="button" class="sendReplyWordBtn am-btn am-btn-success">发送</button>' +
                    '<button type="button" class="quitReplyWordBtn am-btn">取消</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>'));

                amUSm10.append(subComment);
                amG.append(amUSm10);
                visitorComment.append(amG);
                visitorComment.append($('<hr>'));
                allComments.append(visitorComment);
            });
            articleComment.append(allComments);
            commentBottom.append(articleComment);
            //添加留言数组数

            $('.leaveMessageNum').html(data.length);

        }

        //保存被回复者
        var respondent;

        //点击回复按钮
        $('.reply').click(function () {

            var $this = $(this);


            $this.parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).val('');
            $this.parent().parent().parent().find($('.reply-sub-comment-list')).css("display", "block");
            $this.parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();

            respondent = $this.parent().parent().prev().prev().find('.visitorName').html();

        });

        //添加新评论显示评论框
        $('.moreComment').click(function () {
            var $this = $(this);


            $this.parent().parent().parent().next().find($('.replyWordTextarea')).val('');
            $this.parent().parent().parent().next().css("display", "block");
            $this.parent().parent().parent().next().find($('.replyWordTextarea')).focus();

            respondent = $this.parent().parent().parent().parent().parent().find('.visitorInfo').find('.visitorName').html();


        });


        //点击取消隐藏评论框
        $('.quitReplyWordBtn').click(function () {
            $(this).parent().parent().find($('.replyWordTextarea')).val('');
            $(this).parent().parent().parent().css("display", "none");
        });

        //发送评论中的回复
        $('.sendReplyWordBtn').click(function () {
            var $this = $(this);
            var replyContent = $this.parent().parent().find($('.replyWordTextarea')).val();
            var pId = $this.parent().parent().parent().parent().parent().parent().parent().attr("id");
            var pageName = window.location.pathname.substring(1);
            pId = pId.substring(1);
            if (replyContent == "") {
                alert("我没看清你要回复啥吖！");
            } else {

                $.ajax({
                    type: 'POST',
                    url: '/user/leaveMessage/reply',
                    dataType: 'json',
                    data: {
                        leaveMessageContent: replyContent,
                        pageName: pageName,
                        parentId: pId,
                    },
                    success: function (data) {
                        if (data['status'] == 101) {
                            $.get("/toLogin", function (data, status, xhr) {
                                window.location.replace("/login");
                            });
                        } else if (data['status'] == 103) {
                            dangerNotice(data['message'] + " 发表评论失败")
                        } else if (data['status'] == 801) {
                            alert("内容不能为空！");
                        } else {
                            var sub_comment = $this.parent().parent().parent().parent();
                            var visitorReply = $('<div class="visitorReply"></div>');
                            var visitorReplyWords = $('<div class="visitorReplyWords">' +
                                '<a class="answerer">' + data['data']['answerer'] + ' </a>' + data['data']['leaveMessageContent'] + '' +
                                '</div>');
                            var visitorReplyTime = $('<div class="visitorReplyTime">' +
                                '<span class="visitorReplyTimeTime">' + data['data']['leaveMessageDate'] + '</span>' +
                                '<a>' +
                                '<i class="replyReply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                                '</a>' +
                                '</div>');
                            visitorReply.append(visitorReplyWords);
                            visitorReply.append(visitorReplyTime);
                            visitorReply.append('<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed" />');

                            if (sub_comment.find('.sub-comment-list').length > 0) {
                                sub_comment.find('.visitorReplies').append(visitorReply);
                            } else {
                                var visitorReplies = $('<div class="visitorReplies"></div>');
                                var sub_comment_list = $('<div class="sub-comment-list"></div>');
                                visitorReplies.append(visitorReply);
                                sub_comment_list.append(visitorReplies);
                                sub_comment_list.append($('<div class="more-comment">' +
                                    ' <a>' +
                                    '<i class="moreComment am-icon-edit"> 添加新评论</i>' +
                                    '</a>' +
                                    '</div>'));
                                sub_comment.prepend(sub_comment_list);
                            }

                            //给新加入的评论中的回复和下面的添加新评论添加点击事件
                            $this.parent().parent().parent().parent().find('.visitorReplies>div:last-child').find('.replyReply ').click(function () {
                                respondent = $(this).parent().parent().prev().find($('.answerer')).html();
                                $(this).parent().parent().parent().parent().parent().next().css("display", "block");
                                $(this).parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).val('@' + respondent + ' ');
                                $(this).parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).focus();
                            });
                            $this.parent().parent().parent().parent().find('.sub-comment-list').find('.more-comment').find('.moreComment').click(function () {
                                $(this).parent().parent().parent().next().find($('.replyWordTextarea')).val('');
                                $(this).parent().parent().parent().next().css("display", "block");
                                $(this).parent().parent().parent().next().find($('.replyWordTextarea')).focus();

                                respondent = $(this).parent().parent().parent().parent().parent().find('.visitorInfo').find('.visitorName').html();
                            });
                            $this.parent().find($('.replyWordTextarea')).val('');
                            $this.parent().parent().parent().css("display", "none");
                        }
                    },
                    error: function () {
                        alert("回复失败！");
                    }
                });
            }

        });

    }
        $.ajax({
            type: 'GET',
            url: '/leaveMessage/page',
            dataType: 'json',
            data: {
                pageName: window.location.pathname.substring(1)
            },
            success: function (data) {
                if (data['status'] == 101) {
                    $.get("/toLogin", function (data, status, xhr) {
                        window.location.replace("/login");
                    });
                } else if (data['status'] == 103) {
                    dangerNotice(data['message'] + " 获得留言失败");
                } else {
                    putInLeaveMessage(data['data']);
                }
            },
            error: function () {
            }
        });


        //客官，来说两句把
        $('#commentBtn').click(function () {
            var leaveMessageContent = $('#comment').val();
            var url = window.location.pathname;
            leaveMessageContent = $.trim(leaveMessageContent);
            if (leaveMessageContent == "") {
                alert("客官，你还没说两句呢！");
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/user/leaveMessage/publish',
                    dataType: 'json',
                    data: {
                        leaveMessageContent: leaveMessageContent,
                        pageName: url.substring(1)
                    },
                    success: function (data) {
                        if (data['status'] == 101) {
                            $.get("/toLogin", function (data, status, xhr) {
                                window.location.replace("/login");
                            });
                        } else if (data['status'] == 103) {
                            dangerNotice(data['message'] + " 发表留言失败");
                        } else {
                            putInLeaveMessage(data['data']);
                        }
                    },
                    error: function () {
                        alert("发表失败！");
                    }
                });
            }
        });
