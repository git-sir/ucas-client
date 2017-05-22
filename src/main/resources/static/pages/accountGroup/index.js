/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 51);
/******/ })
/************************************************************************/
/******/ ({

/***/ 0:
/***/ (function(module, exports) {

var GridTable = UcsmyUI.GridTable;

var Grid = React.createClass({displayName: "Grid",
	_current: 1,
	_parameters: {},
	getInitialState:function () {
        return {
            pageSize: this.props.pageSize ? this.props.pageSize : 10,
    		pageProperty: this.props.pageProperty ? this.props.pageProperty : 'pageNum',// 分页参数，第几页参数
			limitProperty: this.props.limitProperty ? this.props.limitProperty : 'pageSize',// 分页参数，一页几条记录参数
			retDataProperty: this.props.retDataProperty ? this.props.retDataProperty : 'resultList',
			retTotalProperty: this.props.retTotalProperty ? this.props.retTotalProperty : 'totalCount',
			retCurrentProperty: this.props.retCurrentProperty ? this.props.retCurrentProperty : 'pageNo',
            callback: this.props.callback ? this.props.callback : function () {}
        }
    },
    componentDidMount:function(){
		this._getData(this.props.parameters);
	},
	load: function(parameters) {
		this._current = 1;
		this._getData(parameters);
	},
	reload: function(parameters) {
		this._getData(parameters);
	},
	_paging: function(p) {
		this._current = p.currentPage;
		this._getData(this._parameters);
	},
    _getData: function(parameters) {
    	var me = this;
    	parameters = parameters ? parameters : {};
    	parameters[this.state.pageProperty] = this._current;
    	parameters[this.state.limitProperty] = this.state.pageSize;
    	this._parameters = parameters;
    	$.post(this.props.url, parameters, function(data) {
    		var totalNum = me._get(data, me.state.retTotalProperty);
    		var total = parseInt(totalNum / me.state.pageSize) + (totalNum % me.state.pageSize == 0 ? 0 : 1);
    		me.refs.grid.setValue({
                gridData: [],
                totalPage: 0,
                currentPage: 1
            });
    		var retCurrent = me._get(data, me.state.retCurrentProperty);
    		me.refs.grid.setValue({
                gridData: me._get(data, me.state.retDataProperty),
                totalPage: total,
                currentPage: retCurrent ? retCurrent : me._current
            });
            me.state.callback();
		}, "json");
    },
    _get: function(data, p) {
    	p.split(".").map(function(d) {
    		data = data[d];
    	});
    	return data;
    },
    _hasPermission: function(column) {// or模式
		if(!column.permissionName)
			return true;
		var f = false;
		column.permissionName.split(",").map(function(data) {
			if(myRoles[data] == true)
				f = true;
		});
		return f;
	},
    render: function() {
		var cs = [];
		var me = this;
		this.props.columns.map(function(data) {
			if(me._hasPermission(data))
				cs.push(data);
		});
		var classNames = {
            rootDiv: 'custom-rootDiv',
            itemsPerPage: 'custom-itemsPerPage',
            table: 'custom-table',
            pagination: 'custom-pagination',
            filter: 'custom-filter'
        };
		return(
			React.createElement(GridTable, {
	            ref: "grid", 
	            classNames: classNames, 
	            striped: true, 
	            itemsPerPage: this.state.pageSize, 
	            currentPage: this._current, 
	            totalPage: 0, 
	            gridData: [], 
	            getData: this._paging, 
	            columns: cs, 
				isTextOverflowHidden: this.props.isTextOverflowHidden}
	        )
		)
	}
});

module.exports = Grid;

/***/ }),

/***/ 1:
/***/ (function(module, exports) {

var PermissionLink = React.createClass({displayName: "PermissionLink",
    render : function() {
		if(!this.props.permissionName || myRoles[this.props.permissionName] == true) {
			return (
				React.createElement("a", React.__spread({},  this.props), this.props.children)
			);
    	} else {
    		return (
				React.createElement("a", null)
			);
    	}
    }
});
module.exports = PermissionLink;

/***/ }),

/***/ 11:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/24.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Checkbox = UcsmyUI.Checkbox;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            gridUrl: '',
            saveUrl: '',
            title: '',
            sign: '',
            data: {},
            accgUuid: ''
        }
    },
    init: function (sign, data, successFn) {// sign: bind or unbind
        var title, saveUrl;
        var gridUrl = "accountGroup/queryClientGroupInfo";
        var queryData = {};
        if (sign == "bind") {
            title = "绑定应用组";
            queryData = {"status" : "bind",accgUuid : data.accgUuid};
            saveUrl = "accountGroup/manageClientGroup";
        } else {
            if (sign == "unbind") {
                title = "解绑应用组";
            } else {
                title = "查看应用组"
            }
            queryData = {accgUuid : data.accgUuid};
            saveUrl = "accountGroup/manageClientGroup";
        }
        this.setState({
            gridUrl: gridUrl,
            saveUrl: saveUrl,
            accgUuid: data.accgUuid,
            title: title,
            data: data,
            sign: sign
        }, () => {
            this.refs.grid.load(queryData);
        });
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    _search: function () {
        var status = '';
        if (this.state.sign == 'bind') {
            status = 'bind';
        }
        this.refs.grid.load({
            status: status,
            accgUuid: this.state.accgUuid,
            groupName : this.refs.groupName.getValue()
        });
    },
    _onClick: function (event) {
        var me = this;
        var ids = [];
        var obj = document.getElementsByName("selectedId");
        for (k in obj) {
            if (obj[k].checked)
                ids.push(obj[k].value);
        }
        if (ids.length == 0) {
            UcsmyIndex.alert("异常消息", "请选择要绑定的应用组");
            return;
        }
        var d;
        if (this.state.sign == "bind") {
            d = {"accgUuid": this.state.data.accgUuid, "cligUuids": ids.join(","),"type":"bind"};
        } else {
            d = {"accgUuid": this.state.data.accgUuid,"cligUuids": ids.join(","),"type":"unbind"};
        }
        UcsmyIndex.confirm("确定", "是否确定操作选中的应用组？", function () {
            $.post(me.state.saveUrl, d, function (data) {
                if (data.retcode == "0") {
                    UcsmyIndex.alert("成功", data.retmsg);
                    me._return();
                } else {
                    UcsmyIndex.alert("失败", data.retmsg);
                }
            }, "json").error(function (xhr, errorText, errorType) {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    render: function () {
        var btn = [];
        if (this.state.sign == 'bind' || this.state.sign == 'unbind') {
            btn.push(React.createElement(Button, {buttonType: "bidnow", onClick: this._onClick}, this.state.title));
        }
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, this.state.title), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "应用组名称"}, React.createElement(Input, {ref: "groupName"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._search}, "查询"), 
                    btn, 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
                ), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {
                        url: this.state.gridUrl, ref: "grid", 
                        columns: [{
                            name: 'cligUuid',
                            header: "",
                             width: 50,
                            content: function (item) {
                                if (this.state.sign == 'bind' || this.state.sign == 'unbind') {
                                    return (
                                        React.createElement(Checkbox, {value: item.cligUuid, name: "selectedId"})
                                    )
                                }
                            }.bind(this)
                        }, {
                            name: 'groupName', header: '应用组名称', width: 200
                        },{
                            name: 'descRibe', header: '应用组描述'
                        }]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        );
    }
});

/***/ }),

/***/ 12:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var configFormData = {

    "groupName": [
        {type: "required", msg: "账号组名称不能为空"},
        {type : "maxlength", maxlength : 50, msg : "账号组名称长度不能超过50"}
    ],
    "descRibe": [
        {type: "required", msg: "账号组描述不能为空"},
        {type : "maxlength", maxlength : 2000, msg : "账号组描述长度不能超过2000"}
    ]

};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            ucasAccountGroup: {}
        }
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "groupName", "descRibe"
        ];
        var fn = function (result) {
            if(result) {
                callback();
            }
        };

        this.refs.saveForm.validate(fn, validateField);

        return status;
    },
    _saveOrUpdate: function () {

        var me = this;
        this._validate(function(){
            $.post(me.state.url,
                $('#saveForm').serialize(),
                function (result) {
                if (result && result.retcode && result.retcode == "0") {
                    UcsmyIndex.alert("提示", result.retmsg);
                    UcsmyIndex.closeChildrenPage();
                    me.state.callback();
                } else {
                    UcsmyIndex.alert("提示","失败");
                }
            });
        });
    },
    init: function (url, title, data, callback) {
        var me = this;
        this.setState({
            title: title,
            url: url,
            ucasAccountGroup: data,
            callback: callback,
        });
        // this.refs.saveForm.setValues(data);
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
                            React.createElement("input", {type: "hidden", name: "accgUuid", value: this.state.ucasAccountGroup.accgUuid}), 
                            React.createElement(FormItem, {label: "用户组名称"}, React.createElement(Input, {name: "groupName", value: this.state.ucasAccountGroup.groupName})), 
                            React.createElement(FormItem, {label: "用户组描述"}, React.createElement(Input, {name: "descRibe", value: this.state.ucasAccountGroup.descRibe}))
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._saveOrUpdate}, "保存"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
                )
            )
        )
    }
});

/***/ }),

/***/ 13:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/24.
 */
var Button = UcsmyUI.Button;
var Checkbox = UcsmyUI.Checkbox;
var Grid = __webpack_require__(0);
var FormItem = UcsmyUI.Form.FormItem;
var Input = UcsmyUI.Input;
module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            gridUrl: '',
            saveUrl: '',
            title: '',
            sign: '',
            data: {},
            accgUuid:''
        }
    },
    init: function (sign, data, successFn) {// sign: bind or unbind
        var title, saveUrl,accgUuid;
        var gridUrl = "accountGroup/queryAccountInfo";;
        var queryData = {};
        if (sign == "bind") {
            title = "绑定用户";
            saveUrl = "accountGroup/manageAccount";
        } else {
            title = "解绑用户";
            queryData = {accgUuid : data.accgUuid};
            saveUrl = "accountGroup/manageAccount";
            accgUuid = data.accgUuid;
        }
        this.setState({
            gridUrl: gridUrl,
            saveUrl: saveUrl,
            title: title,
            data: data,
            sign: sign,
            accgUuid:accgUuid
        }, () => {
            this.refs.grid.load(queryData);
        });
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    _query: function() {
        this.refs.grid.load({
            ucasAccount: this.refs.ucasAccount.getValue(),
            emailAccount: this.refs.emailAccount.getValue(),
            mobileAccount: this.refs.mobileAccount.getValue(),
            accgUuid:this.state.accgUuid
        });

    },
    _onClick: function (event) {
        var me = this;
        var ids = [];
        var obj = document.getElementsByName("selectedId");
        for (k in obj) {
            if (obj[k].checked)
                ids.push(obj[k].value);
        }
        if (ids.length == 0) {
            UcsmyIndex.alert("异常消息", "请选择要绑定的用户");
            return;
        }
        var d;
        if (this.state.sign == "bind") {
            d = {"accgUuid": this.state.data.accgUuid, "accountIds": ids.join(",")};
        } else {
            d = {"accountIds": ids.join(",")};
        }
        UcsmyIndex.confirm("确定", "是否确定操作选中的用户？", function () {
            $.post(me.state.saveUrl, d, function (data) {
                if (data.retcode == "0") {
                    UcsmyIndex.alert("成功", data.retmsg);
                    me._return();
                } else {
                    UcsmyIndex.alert("失败", data.retmsg);
                }
            }, "json").error(function (xhr, errorText, errorType) {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    render: function () {
        return (
            React.createElement("div", null, 

                React.createElement("div", {className: "panel"}, 
                     React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                            React.createElement(FormItem, {label: "网金帐号"}, React.createElement(Input, {ref: "ucasAccount"})), 
                            React.createElement(FormItem, {label: "邮箱帐号"}, React.createElement(Input, {ref: "emailAccount"})), 
                            React.createElement(FormItem, {label: "手机帐号"}, React.createElement(Input, {ref: "mobileAccount"}))
                     )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._onClick}, this.state.title), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
                ), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {
                        url: this.state.gridUrl, ref: "grid", 
                        columns: [{
                            name: 'accUuid',
                            header: "",
                             width: 50,
                            content: function (item) {
                                return (
                                    React.createElement(Checkbox, {value: item.accUuid, name: "selectedId"})
                                );
                            }.bind(this)
                        }, {
                            name: 'ucasAccount', header: '用户网金账号', width: 150
                        }, {
                            name: 'realName', header: '用户名称', width: 150
                        },
                        {
                            name: 'emailAccount', header: '邮箱帐号', width: 150
                        }, {
                            name: 'mobileAccount', header: '手机帐号', width: 150
                        },{
                            name: 'mobilePhone', header: '用户手机号'
                        }]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        );
    }
});

/***/ }),

/***/ 14:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/24.
 */
var Button = UcsmyUI.Button;
var Checkbox = UcsmyUI.Checkbox;
var Grid = __webpack_require__(0);
var FormItem = UcsmyUI.Form.FormItem;
var Input = UcsmyUI.Input;
module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            gridUrl: '',
            saveUrl: '',
            title: '',
            sign: '',
            data: {},
            accgUuid:''
        }
    },
    init: function (sign, data, successFn) {// sign: bind or unbind
        var title, saveUrl,accgUuid;
        var gridUrl = "accountGroup/queryAccountInfo";;
        var queryData = {};
        if (sign == "bind") {
            title = "绑定用户";
            saveUrl = "accountGroup/manageAccount";
        } else {
            title = "解绑用户";
            queryData = {accgUuid : data.accgUuid};
            saveUrl = "accountGroup/manageAccount";
        }
        this.setState({
            gridUrl: gridUrl,
            saveUrl: saveUrl,
            title: title,
            data: data,
            sign: sign,
            accgUuid: data.accgUuid
        }, () => {
            this.refs.grid.load(queryData);
        });
    },
    _query: function() {
        this.refs.grid.load({
            ucasAccount: this.refs.ucasAccount.getValue(),
            emailAccount: this.refs.emailAccount.getValue(),
            mobileAccount: this.refs.mobileAccount.getValue(),
            realName: this.refs.realName.getValue(),
            accgUuid:this.state.accgUuid
        });

    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    _onClick: function (event) {
        var me = this;
        var ids = [];
        var obj = document.getElementsByName("selectedId");
        for (k in obj) {
            if (obj[k].checked)
                ids.push(obj[k].value);
        }
        if (ids.length == 0) {
            UcsmyIndex.alert("异常消息", "请选择要绑定的用户");
            return;
        }
        var d;
        if (this.state.sign == "bind") {
            d = {"accgUuid": this.state.data.accgUuid, "accountIds": ids.join(",")};
        } else {
            d = {"accountIds": ids.join(",")};
        }
        UcsmyIndex.confirm("确定", "是否确定操作选中的用户？", function () {
            $.post(me.state.saveUrl, d, function (data) {
                if (data.retcode == "0") {
                    UcsmyIndex.alert("成功", data.retmsg);
                    me._return();
                } else {
                    UcsmyIndex.alert("失败", data.retmsg);
                }
            }, "json").error(function (xhr, errorText, errorType) {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    render: function () {
        return (
            React.createElement("div", null, 
            React.createElement("div", {className: "panel"}, 
            React.createElement("div", {className: "panel-title"}, "查询条件"), 
            React.createElement("div", {className: "panel-content"}, 
                React.createElement(FormItem, {label: "用户名称"}, React.createElement(Input, {ref: "realName"})), 
            React.createElement(FormItem, {label: "网金帐号"}, React.createElement(Input, {ref: "ucasAccount"})), 
            React.createElement(FormItem, {label: "邮箱帐号"}, React.createElement(Input, {ref: "emailAccount"})), 
            React.createElement(FormItem, {label: "手机帐号"}, React.createElement(Input, {ref: "mobileAccount"}))
            )
            ), 
            React.createElement("div", {className: "btn-panel"}, 
            React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
        React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
        ), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {
                        url: this.state.gridUrl, ref: "grid", 
                        columns: [ {
                            name: 'ucasAccount', header: '用户网金账号', width: 150
                        },
                        {
                            name: 'emailAccount', header: '邮箱帐号', width: 150
                        }, {
                            name: 'mobileAccount', header: '手机帐号', width: 150
                        },{
                            name: 'realName', header: '用户名称', width: 150
                        }, {
                            name: 'mobilePhone', header: '用户手机号'
                        }]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        );
    }
});

/***/ }),

/***/ 3:
/***/ (function(module, exports) {

var Button = UcsmyUI.Button;

var PermissionButton = React.createClass({displayName: "PermissionButton",
    render : function() {
		if(!this.props.permissionName || myRoles[this.props.permissionName] == true) {
			return (
				React.createElement(Button, React.__spread({},  this.props), this.props.children)
			);
    	} else {
    		return (
				React.createElement("span", null)
			);
    	}
    }
});
module.exports = PermissionButton;

/***/ }),

/***/ 51:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var Form = __webpack_require__(12);
var GroupAccount = __webpack_require__(13);
var GroupAccountView = __webpack_require__(14);
var AccountClient = __webpack_require__(11);
var PermissionLink = __webpack_require__(1);
var PermissionButton = __webpack_require__(3);
myPanel = React.createClass({displayName: "myPanel",
    _reload: function () {
        this.refs.groupName.setValue('');
        this.refs.clientGroupName.setValue('');
        this.refs.grid.reload();
    },
    _add: function () {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('accountGroup/addAccountGroup', '添加账号组', {}, function() {
            	me._reload();
            });
        });
    },
    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init('accountGroup/editAccountGroup', '修改账号组', item, function() {
            	me._reload();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "你真的要删除该账号组吗？", function() {
        	$.post('accountGroup/deleteAccountGroup', {accgUuid: item.accgUuid}, function (result) {
                if (result && result.retcode && result.retcode == '0') {
                    UcsmyIndex.alert("成功", result.retmsg);
                    me._reload();
                } else {
                    UcsmyIndex.alert("失败", result.retmsg);
                }
            });
		});
    },
    _search: function () {
        this.refs.grid.load({
            groupName : this.refs.groupName.getValue(),
            clientGroupName: this.refs.clientGroupName.getValue()
        });
    },
    _bindAcount: function (item) {
        UcsmyIndex.openChildrenPage(GroupAccount, function(refPanel) {
            refPanel.init("bind", item);
        });
    },

    _unbindAcount: function (item) {
        UcsmyIndex.openChildrenPage(GroupAccount, function(refPanel) {
            refPanel.init("unbind", item);
        });
    },
    _unbindAcountView: function (item) {
        UcsmyIndex.openChildrenPage(GroupAccountView, function(refPanel) {
            refPanel.init("unbind", item);
        });
    },
    _bindClientGroup: function (item) {
        UcsmyIndex.openChildrenPage(AccountClient, function(refPanel) {
            refPanel.init("bind", item);
        });
    },

    _unbindClientGroup: function (item) {
        UcsmyIndex.openChildrenPage(AccountClient, function(refPanel) {
            refPanel.init("unbind", item);
        });
    },
    _unbindClientGroupView: function (item) {
        UcsmyIndex.openChildrenPage(AccountClient, function(refPanel) {
            refPanel.init("view", item);
        });
    },

    render: function() {
        var me = this;

        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "账号组管理"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "账号组名称"}, React.createElement(Input, {ref: "groupName"})), 
                        React.createElement(FormItem, {label: "授权应用组名称"}, React.createElement(Input, {ref: "clientGroupName"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._search}, "查询"), 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._add}, "添加")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: "accountGroup/query", ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                            {name: 'groupName', header: '帐号组名称', width: 200},
                            {name: 'descRibe', header: '帐号组描述', width: 300},
                            {name: 'accgUuid', header: '操作',  content: function (item) {

                                if (item.accgUuid==1 || item.accgUuid==2) {
                                    return (
                                        React.createElement("span", null, 
                                        React.createElement(PermissionLink, {permissionName: "update_account_group", href: "Javascript:void(0);", onClick: me._edit.bind(this, item)}, " 修改 "), 
                                     React.createElement(PermissionLink, {permissionName: "bind_account", href: "Javascript:void(0);", onClick: me._bindAcount.bind(this, item)}, " 绑定用户 "), 
                                     React.createElement(PermissionLink, {permissionName: "unbind_account", href: "Javascript:void(0);", onClick: me._unbindAcount.bind(this, item)}, " 解绑用户 "), 
                                     React.createElement(PermissionLink, {permissionName: "bind_client_group", href: "Javascript:void(0);", onClick: me._bindClientGroup.bind(this, item)}, " 绑定应用组 "), 
                                     React.createElement(PermissionLink, {permissionName: "unbind_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroup.bind(this, item)}, " 解绑应用组 "), 

                                     React.createElement(PermissionLink, {permissionName: "view_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroupView.bind(this, item)}, " 应用组查看 "), 
                                     React.createElement(PermissionLink, {permissionName: "view_account", href: "Javascript:void(0);", onClick: me._unbindAcountView.bind(this, item)}, " 用户查看 ")
                                    )
                                )
                                }else if(item.accgUuid == 0){
                                    return (
                                        React.createElement("span", null, 
                                        React.createElement(PermissionLink, {permissionName: "update_account_group", href: "Javascript:void(0);", onClick: me._edit.bind(this, item)}, " 修改 "), 
                                     React.createElement(PermissionLink, {permissionName: "bind_client_group", href: "Javascript:void(0);", onClick: me._bindClientGroup.bind(this, item)}, " 绑定应用组 "), 
                                     React.createElement(PermissionLink, {permissionName: "unbind_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroup.bind(this, item)}, " 解绑应用组 "), 
                                     React.createElement(PermissionLink, {permissionName: "view_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroupView.bind(this, item)}, " 应用组查看 "), 
                                     React.createElement(PermissionLink, {permissionName: "view_account", href: "Javascript:void(0);", onClick: me._unbindAcountView.bind(this, item)}, " 用户查看 ")

                                    )
                                )
                                }
                                else {
                                    return (
                                        React.createElement("span", null, 
                                        React.createElement(PermissionLink, {permissionName: "update_account_group", href: "Javascript:void(0);", onClick: me._edit.bind(this, item)}, " 修改 "), 
                                    React.createElement(PermissionLink, {permissionName: "delete_account_group", href: "Javascript:void(0);", onClick: me._delete.bind(this, item)}, " 删除 "), 

                                     React.createElement(PermissionLink, {permissionName: "bind_account", href: "Javascript:void(0);", onClick: me._bindAcount.bind(this, item)}, " 绑定用户 "), 
                                     React.createElement(PermissionLink, {permissionName: "unbind_account", href: "Javascript:void(0);", onClick: me._unbindAcount.bind(this, item)}, " 解绑用户 "), 
                                     React.createElement(PermissionLink, {permissionName: "bind_client_group", href: "Javascript:void(0);", onClick: me._bindClientGroup.bind(this, item)}, " 绑定应用组 "), 
                                     React.createElement(PermissionLink, {permissionName: "unbind_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroup.bind(this, item)}, " 解绑应用组 "), 
                                     React.createElement(PermissionLink, {permissionName: "view_client_group", href: "Javascript:void(0);", onClick: me._unbindClientGroupView.bind(this, item)}, " 应用组查看 "), 
                                     React.createElement(PermissionLink, {permissionName: "view_account", href: "Javascript:void(0);", onClick: me._unbindAcountView.bind(this, item)}, " 用户查看 ")
                                    )
                                )
                                }
                            }}
                        ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        );
    }
});

/***/ })

/******/ });