//     ----------------       sweetAlter2    -------------------
function alt(type, title, text){
    swal({
        type : type,
        title : title,
        text : text,
        timer : 1000
    })
}

function cfm(title, text, successCallback){
    swal({
        title: title,
        text: text,
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then(function(result){
        if (result.value) {
            successCallback();
        }else{
            swal({
                type: 'warning',
                title: '操作已取消',
                showConfirmButton: false,
                timer: 1500
            });
        }
    })
}

function success(title, text, callback){
    if(callback){
        swal({
            type : "success",
            title : title,
            text : text,
            timer : 2000,
            showConfirmButton: false,
            onClose: callback
        })
    } else {
        alt("success", title, text);
    }

}

function error(title, text){
    swal({
        type : "error",
        title : title,
        text : text,
        timer : 6000
    })
}

function warn(title, text){
    swal({
        type : "warning",
        title : title,
        text : text,
        timer : 6000
    });
}

function show(html){
    swal({html:html});
}

/**
 * 提示输入框
 * @param title 标题
 * @param inputType 输入框类型
 * @param callback 处理输入元素
 * @param defaultValue 默认值
 * @param check 校验输入元素
 */
function ppt(title, inputType, callback, defaultValue, check){
    if(defaultValue === undefined){
        defaultValue = "";
    }
    swal({
        title: title,
        input: inputType,
        inputValue: defaultValue,
        showCancelButton: true,
        confirmButtonText: '确认',
        showLoaderOnConfirm: true,
        preConfirm: function(result) {
            return new Promise(function(resolve, reject) {
                if (check && !check(result)) {
                    reject('输入格式错误');
                } else {
                    resolve();
                }
            });
        },
        allowOutsideClick: false
    }).then(function(result) {
        if (result.value) {
            callback(result.value)
        }else{
            swal({
                type: 'warning',
                title: '操作已取消',
                showConfirmButton: false,
                timer: 1500
            });
        }
    })
}


// ----------------------   表单 & ajax   -------------------

$.fn.serializeObject = function() {
    let object = {};
    let array = this.serializeArray();
    $.each(array, function() {
        if (object[this.name]) {
            object[this.name] += ("," + (this.value || ''));
        } else {
            object[this.name] = this.value || '';
        }
    });
    return object;
};

/**
 * 防止重复点击
 * @param $btn
 */
function imposeRepeatClick($btn){
    $btn.attr("disabled");
    $btn.addClass("disabled");
    setTimeout(function(){
        $btn.removeAttr('disabled')
        $btn.removeClass("disabled");
    },3000);
}

/**
 * 校验表单
 * @param $from 表单
 * @returns {boolean}
 */
$.fn.checkForm = function() {
    let flag = true;
    this.find("input[required]").each(function(){
        let $this = $(this);
        if($this.val().trim() === ""){
            flag = false;
            if($this.attr("type") === "number" && !isNaN($this.val())){
                warn("表单格式错误", $this.attr("placeholder") + "(数字格式)");
            } else {
                warn("请完善表单", $this.attr("placeholder"));
            }
            return false;
        }
    });
    if(!flag){
        this.find("textarea[required]").each(function(){
            let $this = $(this);
            if($this.val().trim() === ""){
                flag = false;
                warn("请完善表单", $this.attr("placeholder"));
                return false;
            }
        });
    }
    if(!flag){
        this.find("label[data-checkbox-required]").each(function(){
            let $this = $(this);
            let name = $this.attr('data-checkbox-required');
            if($("input:checkbox[name='"+name+"']:checked").length === 0){
                flag = false;
                warn("请完善表单", "请至少选中一个" + $this.text());
                return false;
            }
        });
    }
    return flag;
};

/**
 * 提交表单 post json 的方式,且提交之前先校验表单
 * @param url
 * @param callback
 */
$.fn.submitForm = function(url, callback) {
    if(this.checkForm()){
        postJson(url, this.serializeObject(), callback)
    }
};

function loadView(url, callback){
    $.ajax({
        url: url,
        type: 'GET',
        success: function(html){
            if(callback){
                callback(html);
            }else{
                show(html);
            }
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function encodeSearchParams(condition) {
    const params = []
    Object.keys(condition).forEach((key) => {
        let value = condition[key]
        // 如果值为undefined我们将其置空
        if (typeof value === 'undefined') {
            value = ''
        }
        // 对于需要编码的文本（比如说中文）我们要进行编码
        params.push([key, encodeURIComponent(value)].join('='))
    })
    return params.join('&')
}

function get(url, callback){
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

/**
 * 以同步的方式获取
 * @param url
 * @param callback
 */
function getSync(url, callback){
    $.ajax({
        url: url,
        type: 'GET',
        // 关闭异步
        async: false,
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function postJson(url, jsonObject, callback){
    $.ajax({
        url: url,
        type: 'POST',
        contentType : "application/json",
        data : JSON.stringify(jsonObject),
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function putJson(url, jsonObject, callback){
    $.ajax({
        url: url,
        type: 'PUT',
        contentType : "application/json",
        data : JSON.stringify(jsonObject),
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function put(url, callback){
    $.ajax({
        url: url,
        type: 'PUT',
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function remove(url, callback){
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error: function(data){
            ajaxError(data);
        }
    });
}

function isOk(data){
    return data.status === 200;
}

function getResultData(data){
    return data.data;
}

function getResultMessage(data){
    return data.message;
}

function ajaxSuccess(data, callback){
    console.log(data);
    if(isOk(data) && callback){
        callback(getResultData(data));
    }else{
        error("请求失败!", getResultMessage(data));
    }
}

function ajaxError(data){
    console.log(data);
    error("请求失败!", getResultMessage(data));
}

/**
 * 获取文件名称
 * @param files
 * @param separator
 * @returns {string}
 */
function getFileName(files){
    let fileName = "";
    let len = files.length;
    if(len > 0){
        for(var i=0 ; i < len ; i++ ){
            fileName += files[i].name;
            if(i < len - 1){
                fileName += ",";
            }
        }
    }
    return fileName;
}

// 文件上传,单个文件
function doUploadFile(url, files, callback){
    let formData = new FormData();
    formData.append("file",files[0]);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function(data){
            ajaxSuccess(data, callback);
        },
        error : function(data){
            ajaxError(data);
        },
    });
}

//    ---------------------   时间函数   ------------------
/**
 * 获取 日期 yyyy-MM-dd
 * @returns {string}
 */
function getDateString() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    return year + "-" + month + "-" + strDate;
}

// ----------------------   字符串函数    -------------------
/**
 * 拼接字符串和参数
 * @param url
 * @param condition
 * @returns {string}
 */
function formatUrl(url, condition) {
    return `${url}?${encodeSearchParams(condition)}`;
}

/**
 * 移除字符串中的 前后空白以及 null , undefined 等无效字符串
 * @param str
 * @returns {*}
 */
function trimStr(str) {
    return str.replace(/undefined|null/g, "").trim();
}

//   ----------------------   数学函数    -------------------

