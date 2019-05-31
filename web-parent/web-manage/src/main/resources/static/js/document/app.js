$(function(){
    $("div[data-method]").click(function(){
        var serviceName = $(this).parents(".box").find(".box-title").text();
        var method = this.getAttribute('data-method');
        var version = $(this).next().find(".version").text();
        console.log(serviceName + ":" + method);
        var serviceDocument = getDocumentByName(serviceName,method,version);
        console.log(serviceDocument)
        $("#param-title").text(serviceDocument.description + "  -  参数列表");
        $("#service-method").text(serviceDocument.method);

        var href = "http://192.168.1.228:3000/huhuixin/"+gitName+"/src/doc/src/main/java/" + serviceDocument.response.replace(/\./g, "/") + ".java";
        $("#service-response").text(serviceDocument.response);


        $("#service-response").attr("href",href);

        var $paramList = $("#param-list");
        $paramList.find("tr.param").remove();
        serviceDocument.parameters.forEach(function(parameter){
            $paramList.append(getParamHtml(parameter));
        })


        var $responseList = $("#response-list");
        $responseList.find("tr.result").remove();
        serviceDocument.results.forEach(function(result){
            $responseList.append(getResultHtml(result));
        })

        if(serviceDocument.responseJson === "{}"){
            $("#response-json").text("暂未添加");
        }else{
            var options = {
                dom: '#response-json' //对应容器的css选择器
            };
            var jf = new JsonFormater(options); //创建对象
            jf.doFormat(JSON.stringify(JSON.parse(serviceDocument.responseJson))); //格式化json
           // $("#response-json").text(JSON.stringify(JSON.parse(serviceDocument.responseJson)));
        }
        var responseJsonVal = $("#response-json-value");
        responseJsonVal.find("tr.param").remove();
        if(serviceDocument.dictionaries.length >0){
            serviceDocument.dictionaries.forEach(function(jsonDict){
                responseJsonVal.append(getResponseJsonValueHtml(jsonDict));
            });
        }else{
            responseJsonVal.append("" +
                "<tr class='param'>" +
                "<td colspan='5'>暂未添加</td>" +
                "</tr>");
        }

    })

    $(".btn-box-tool[data-widget='collapse']").click(function(){
        var $box = $(this).parents(".box");
        console.log($box.html())
        if($box.hasClass('collapsed-box')){
            $box.removeClass('collapsed-box');
            $(this).find("i").removeClass("fa-plus").addClass("fa-minus");
            $box.find(".box-body").show();
        }else{
            $box.addClass('collapsed-box');
            $(this).find("i").removeClass("fa-minus").addClass("fa-plus");
            $box.find(".box-body").hide();
        }
    })

    $(".btn-box-tool[data-widget='remove']").click(function(){
        $(this).parents(".box").remove();
    })

    $(".changeVer").click(function(){
        $(this).parent().parent().find(".version").text($(this).text());
        $(this).parent().parent().prev().click();
    })

})
function getDocumentByName(serviceName,method,version){
    var curDocument = null;
    documentsGroups.forEach(function(documentsGroup){
        if(documentsGroup.serviceName == serviceName){
            documentsGroup.documents.forEach(function(document){
                if(document.method == method) {
                    var versions = document.versions;
                    console.log(versions);
                    curDocument = versions[version.toUpperCase()];
                }
            })
        }
    })
    return curDocument;
}
function getParamHtml(paramter){
    <!-- label-info YES   label-warning NO -->
    <!-- label-success value label-danger --- -->
    return  "<tr class='param'>" +
        "<td>"+paramter.name+"</td>" +
        "<td>"+paramter.description+"</td>" +
        "<td>"+paramter.type+"</td>" +
        "<td><span class='label "+(paramter.require ? 'label-info' : 'label-warning')+"'>"+(paramter.require ? 'YES' : 'NO')+"</span></td>" +
        "<td>"+(paramter.defaultValue == null ? '---' : paramter.defaultValue)+"</td>" +
        "</tr>"
}

function getResponseJsonValueHtml(paramter){
    return  "<tr class='param'>" +
        "<td>"+paramter.name+"</td>" +
        "<td>"+paramter.type+"</td>" +
        "<td><div>"+paramter.desc+"</div></td>" +
        "<td  colspan='2'>"+paramter.value+"</td>" +
        "</tr>"
}

function getResultHtml(result){
    return "<tr class='result'>" +
        "<td>"+result.name+"</td>" +
        "<td>"+result.result+"</td>" +
        "<td colspan='3'>"+result.message+"</td>" +
        "</tr>"
}