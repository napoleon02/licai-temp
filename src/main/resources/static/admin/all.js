/**
 * HTML编码
 * @param str   字符串
 * @returns     编码后的字符串
 */
function htmlEncode(str) {
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(str));
    return div.innerHTML;
}

/**
 * HTML解码
 * @param str   字符串
 * @returns     解码后的字符串
 */
function htmlDecode(str) {
    var div = document.createElement('div');
    div.innerHTML = str;
    return div.innerHTML;
}

/**
 * 添加一个新Tab项
 * @param target    目标tabs控件
 * @param url       URL
 * @param title     标题
 * @param bool      是否外部链接
 */
function addToTabs(target, url, title, bool) {
    var option = {
        title: title,
        closable: true
    };
    if (bool) {
        option.href = url;
    } else {
        option.content = '<iframe src="' + url + '" frameborder="0" width="100%" height="100%"></iframe>';
    }
    var tab = $('#' + target).tabs('getTab', title);
    var index = $('#' + target).tabs('getTabIndex', tab);
    if (index != -1) {
        $('#' + target).tabs('select', title);
    } else {
        $('#' + target).tabs('add', option);
    }
}

/**
 * 创建一个窗口
 * @param id        窗口ID
 * @param title     窗口标题
 * @param url       窗口URL
 * @param width     窗口可视宽度
 * @param height    窗口可视高度
 * @param func      关闭该窗口回调函数
 */
function createWindow(id, title, url, width, height, func) {
    $("<div id='" + id + "'></div>").window({
        title: title,
        href: url,
        width: width,
        height: height,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        onClose: function () {
            $(this).window('destroy');
            if (func != undefined) {
                func();
            }
        }
    });
}

/**
 * 关闭一个窗口
 * @param id        窗口ID
 * @param callBack  回调函数
 */
function closeWindow(id, callBack) {
    $("#" + id).window('close');
    if (callBack != null) {
        callBack();
    }
}

/**
 * 弹出消息
 * @param msg 消息
 * @param type 类型
 * @param fn 回调函数
 */
function showMsg(msg, type, fn) {
    var icon = 'info';
    if (type == 1) {
        icon = 'info';
    } else if (type == 2) {
        icon = 'error'
    } else if (type == 3) {
        icon = 'question';
    } else if (type == 4) {
        icon = 'warning';
    }
    $.messager.alert('提示', msg, icon, fn);
}

/**
 * 复制数据到剪贴板
 * @param value 原始值
 */
function copyToCliboard(value, func) {
    new Clipboard("#copy", {
        text: function () {
            return value;
        }
    });
    $("#copy").trigger("click");
    if (null != func && undefined != func) {
        func();
    }
}