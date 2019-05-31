//加载页面
$(function() {
    //改变选中状态和样式,标记当前页
    $("li.menu-item").click(function(){
        $("li.menu-item.active").removeClass('active');
        $(this).addClass('active');
        loadPage($(this).attr("data-url"),{});
    })
    //一级菜单
    $("#jbt-menu").click(function(){
        //获取当前打开的菜单,如果没有,则设置为主页
        var $surMenu = $("#jbt-menu").find("li.menu-open");
        var firstPath = "主页";
        if($surMenu.length > 0){
            firstPath = $($surMenu[0]).find(".menu-name").text();
        }
        $("#path-first-menu").html("<a href=\"javascript:void(0)\"><i class=\"fa fa-dashboard\"></i>"+firstPath+"</a>");
        $("#path-second-menu").text("");
    })
    //二级菜单
    $("#jbt-menu>li>ul>li").click(function(){
        $("#path-second-menu").text($(this).text());
        //阻止事件继续冒泡
        return false;
    })

    $("#index-change-password").click(() => {
        loadPage("/manager/change-password");
    })
});

var pageLoading = false;
function loadPage(url,condition){
    if(pageLoading){
        console.log("page loading...");
        var loadingHtml = "<div class=\"overlay\"><i class=\"fa fa-refresh fa-spin\"></i></div>";
        $("#mainContainerRepleace").html(loadingHtml);
        return false;
    }
    pageLoading = true;
    $.ajax({
        type: 'GET',
        url: url,
        data: condition,
        success: function(data){
            $("#mainContainerRepleace").html(data);
        },
        error : function() {
            $("#mainContainerRepleace").html("");
            error("服务异常!","请检查服务是否正常!");
        }
    });
    pageLoading = false;
}

// 重新加载本页数据
function reload(){
    let condition = {};
    let $form = $("#form");
    if($form.length > 0){
        let params = $form.serializeArray();
        for(let index in params){
            let value = params[index].value;
            //定义被忽略的值 - 999为Select 的无效值,筛选时无需提交该字段
            if(value != -999 && value.trim() != '')condition[params[index].name] = value;
        }
    }
    condition.number = page.number;
    condition.size = page.size;
    let $activeMenu = $("li.menu-item.active");
    if($activeMenu.length > 0){
        loadPage($activeMenu.attr("data-url") ,condition);
    }
}


