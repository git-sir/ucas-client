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
/******/ 	return __webpack_require__(__webpack_require__.s = 52);
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

/***/ 15:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var configFormData = {

	"emailAccount": [
		{type: "required", msg: "邮箱帐号不能为空"},
		{type: "mail", msg: "邮箱地址不对"}
	]

};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function () {
		return {
			title: '',
			callback: function(){},
			ucasAccount: {}
		}
	},
	_validate: function (callback) {
		var status = false;
		var validateField = [
			"emailAccount"
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
			_addButtonDisabled('save');
			$.post("account/upEmail",
				$('#saveForm').serialize(),
				function (result) {
					if (result && result.retcode && result.retcode == "0") {
						UcsmyIndex.alert("提示", result.retmsg);
						UcsmyIndex.closeChildrenPage();
						me.state.callback();
					} else {
						UcsmyIndex.alert("提示",result.retmsg);
					}
					_removeButtonDisabled('save');
				}).error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
				_removeButtonDisabled('save');
			});
		});
	},
	init: function (data,callback) {
		var me = this;
		this.setState({
			title: '邮箱账号修改',
			ucasAccount: data,
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
			React.createElement("input", {type: "hidden", name: "ucasAccount", value: this.state.ucasAccount.ucasAccount}), 
		React.createElement(FormItem, {label: "旧邮箱帐号"}, React.createElement(Input, {disabled: "true", value: this.state.ucasAccount.emailAccount})), 
		React.createElement(FormItem, {label: "新邮箱帐号"}, React.createElement(Input, {name: "emailAccount"}))
		)
		)
		), 
		React.createElement("div", {className: "btn-panel"}, 
			React.createElement(Button, {buttonType: "bidnow", id: "save", onClick: this._saveOrUpdate}, "保存"), 
		React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
		)
		)
		)
	}
});

/***/ }),

/***/ 16:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var p;
var configFormData = {

	"password": [
		{type: "required", msg: "密码不能为空"},
		{type: "fn", validator: function(value){
			  p=value;
			  return true;
		}, msg: ''
		}
	],
	"repassword": [
		{type: "required", msg: "确认密码不能为空"},
		{type: "fn", validator: function(value){
			if(p!=value) {

				return false;
			}else return true;
		}, msg: '确认密码不一致'
		}
	]

};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function () {
		return {
			title: '',
			callback: function(){},
			ucasAccount: {}
		}
	},
	_validate: function (callback) {
		var status = false;
		var validateField = [
			"password","repassword"
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
			_addButtonDisabled('save');
			$.post("account/upPassword",
				$('#saveForm').serialize(),
				function (result) {
					if (result && result.retcode && result.retcode == "0") {
						UcsmyIndex.alert("提示", result.retmsg);
						UcsmyIndex.closeChildrenPage();
						me.state.callback();
					} else {
						UcsmyIndex.alert("提示",result.retmsg);
					}
					_removeButtonDisabled('save');
				}).error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
				_removeButtonDisabled('save');
			});
		});
	},
	init: function (data) {
		var me = this;
		this.setState({
			title: '密码修改',
			ucasAccount: data
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
			React.createElement("input", {type: "hidden", name: "ucasAccount", value: this.state.ucasAccount.ucasAccount}), 
		React.createElement(FormItem, {label: "新密码"}, React.createElement(Input, {type: "password", name: "password"})), 
		React.createElement(FormItem, {label: "确认密码"}, React.createElement(Input, {type: "password", name: "repassword"}))
			)
			)
			), 
			React.createElement("div", {className: "btn-panel"}, 
			React.createElement(Button, {buttonType: "bidnow", id: "save", onClick: this._saveOrUpdate}, "保存"), 
		React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
		)
		)
		)
	}
});

/***/ }),

/***/ 17:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var configFormData = {

	"mobileAccount": [
		{type: "required", msg: "手机帐号号不能为空"},
		{type: "mobile", msg: "手机号码不对"}
	]

};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function () {
		return {
			title: '',
			callback: function(){},
			ucasAccount: {}
		}
	},
	_validate: function (callback) {
		var status = false;
		var validateField = [
			"mobileAccount"
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
			_addButtonDisabled('save');
			$.post("account/upPhone",
				$('#saveForm').serialize(),
				function (result) {
					if (result && result.retcode && result.retcode == "0") {
						UcsmyIndex.alert("提示", result.retmsg);
						UcsmyIndex.closeChildrenPage();
						me.state.callback();
					} else {
						UcsmyIndex.alert("提示",result.retmsg);
					}
					_removeButtonDisabled('save');
				}).error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
				   _removeButtonDisabled('save');
			});
		});
	},
	init: function (data,callback) {
		var me = this;
		this.setState({
			title: '手机帐号修改',
			ucasAccount: data,
			callback: callback
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
			React.createElement("input", {type: "hidden", name: "ucasAccount", value: this.state.ucasAccount.ucasAccount}), 
		React.createElement(FormItem, {label: "旧手机帐号"}, React.createElement(Input, {disabled: "true", value: this.state.ucasAccount.mobileAccount})), 
		React.createElement(FormItem, {label: "新手机帐号"}, React.createElement(Input, {name: "mobileAccount"}))
		)
		)
		), 
		React.createElement("div", {className: "btn-panel"}, 
			React.createElement(Button, {buttonType: "bidnow", id: "save", onClick: this._saveOrUpdate}, "保存"), 
		React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
		)
		)
		)
	}
});

/***/ }),

/***/ 18:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;
var configFormData = {

    "realName": [
        {type: "required", msg: "用户名称不能为空"},
        {type : "minlength", maxlength : 2, msg : "用户名称长度不能小于2"},
        {type : "maxlength", maxlength : 50, msg : "用户名称长度不能超过50"}
    ],
    "mobilePhone": [
        {type: "mobile", msg: "手机格式不对"}
    ],
    "email": [
        {type: "mail", msg: "邮箱格式不对"}
    ],
    "ucasAccount": [
        {type: "fn", validator: function(value){
            if(""!=value) {
                var m =/^\d{8}$/;

                return m.test(value);
            }else return true;
        }, msg: '网金帐号为8位数字'
        }
    ],
    "accgUuid": [
        {type: "required", msg: "用户组必须选择"},
        {type: "fn", validator: function(value){
            console.log(value)
            if("-1"==value) {
                return false;
            }else return true;
        }, msg: '用户组必须选择'
        }
    ],
    "orgName": [
        {type: "required", msg: "组织名称必须填写"},
        {type : "maxlength", maxlength : 8, msg : "组织名称长度为100"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            ucasAccountGroup: {},
            sta: '',
            urgentFlag:'',
            data:{},
            clientGroup: {}
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("accountGroup/getAll", {}, function(data) {
            if(data.retmsg != 'success') {
                return;
            }

            me.setState({
                data: data.data,
                urgentFlag:data.data
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "realName",
            "mobilePhone","email","accgUuid","ucasAccount","orgName"
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
            _addButtonDisabled('save');
            $.post(me.state.url,
                $('#saveForm').serialize(),
                function (result) {
                    if (result && result.retcode=='0') {
                        UcsmyIndex.alert("提示", result.retmsg);
                        UcsmyIndex.closeChildrenPage();
                        me.state.callback();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                    _removeButtonDisabled('save');
                }).error(function(xhr, errorText, errorType){
                UcsmyIndex.alert("失败", "网络异常");
                _removeButtonDisabled('save');
            });
        });
    },
    init: function (url, title, data, callback) {
        var me = this;

        $.post("account/getUser",
            {'ucasAccount':data.ucasAccount},
            function (result) {
                if (result && result.retcode && result.retcode == "0") {
                  var sta = "正常"
                    if(result.data.status==null || result.data.status=='' || result.data.status=='0')
                        sta='正常';
                    else if(result.data.status='1')
                        sta='冻结';
                    else
                        sta='删除';
                    me.setState({
                        title: title,
                        url: url,
                        ucasAccountGroup: result.data,
                        sta: sta,
                        callback: callback
                    });

                } else {
                    UcsmyIndex.alert("提示", result.retmsg);
                }
            }).error(function(xhr, errorText, errorType){
            UcsmyIndex.alert("失败", "网络异常");
        });
        // this.refs.saveForm.setValues(data);
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        var that = this;
        return (
            React.createElement("div", null, 
            React.createElement("div", {className: "panel"}, 
            React.createElement("div", {className: "panel-title fc-red"}, "帐号管理"), 
        React.createElement("div", {className: "panel-content"}, 
            React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
            React.createElement("input", {type: "hidden", name: "ucasAccount", value: this.state.ucasAccountGroup.ucasAccount}), 
        React.createElement(FormItem, {label: "网金帐号"}, React.createElement(Input, {disabled: "false", name: "ucasAccount", value: this.state.ucasAccountGroup.ucasAccount})), 
        React.createElement(FormItem, {label: "用户名称"}, React.createElement(Input, {name: "realName", value: this.state.ucasAccountGroup.realName})), 
        React.createElement(FormItem, {label: "邮箱帐号"}, React.createElement(Input, {disabled: "false", name: "emailAccount", value: this.state.ucasAccountGroup.emailAccount})), 
        React.createElement(FormItem, {label: "手机帐号"}, React.createElement(Input, {disabled: "false", name: "mobileAccount", value: this.state.ucasAccountGroup.mobileAccount})), 
        React.createElement(FormItem, {label: "联系手机"}, React.createElement(Input, {name: "mobilePhone", value: this.state.ucasAccountGroup.mobilePhone})), 
        React.createElement(FormItem, {label: "联系邮箱"}, React.createElement(Input, {name: "email", value: this.state.ucasAccountGroup.email})), 
        React.createElement(FormItem, {label: "头像地址"}, React.createElement(Input, {name: "headImgUrl", value: this.state.ucasAccountGroup.headImgUrl})), 
        React.createElement(FormItem, {label: "组织名称"}, React.createElement(Input, {name: "orgName", value: this.state.ucasAccountGroup.orgName})), 
        React.createElement(FormItem, {label: "状态"}, React.createElement(Input, {name: "sta", disabled: "false", value: this.state.sta})), 
        React.createElement(FormItem, {label: "性别", className: "col-xs-5"}, 
            React.createElement(RadioGroup, {name: "sex", value: this.state.ucasAccountGroup.sex == null ? "1" : this.state.ucasAccountGroup.sex}, 
        React.createElement(Radio, {value: "1"}, React.createElement("span", {style: {"padding-left": "20px"}}, "男")), 
        React.createElement(Radio, {value: "2"}, React.createElement("span", {style: {"padding-left": "20px"}}, "女"))
        )
        ), 
        React.createElement(FormItem, {label: "用户组"}, 
            React.createElement(SelectDropDown, {name: "accgUuid", option: that.state.urgentFlag, value: that.state.ucasAccountGroup.userAccg, className: "setwidth", ref: "urgentFlag"})
         )
        )
        )
        ), 
        React.createElement("div", {className: "btn-panel"}, 
            React.createElement(Button, {buttonType: "bidnow", id: "save", onClick: this._saveOrUpdate}, "保存"), 
        React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
        )
        )
        )
    }
});

/***/ }),

/***/ 19:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;
var configFormData = {

    "realName": [
        {type: "required", msg: "用户名称不能为空"},
        {type : "minlength", maxlength : 2, msg : "用户名称长度不能小于2"},
        {type : "maxlength", maxlength : 50, msg : "用户名称长度不能超过50"}
    ],
    "emailAccount": [
        {type: "mail", msg: "邮箱地址不对"}
    ],
    "mobileAccount": [
        {type: "mobile", msg: "手机格式不对"}
    ],
    "password": [
        {type: "required", msg: "密码不能为空"},
        {type : "maxlength", maxlength : 25, msg : "密码长度不能超过50"}
    ],
    "mobilePhone": [
        {type: "mobile", msg: "手机格式不对"}
    ],
    "email": [
        {type: "mail", msg: "手机格式不对"}
    ],
    "ucasAccount": [
        {type: "fn", validator: function(value){
            if(""!=value) {
            var m =/^\d{8}$/;

                return m.test(value);
            }else return true;
        }, msg: '网金帐号为8位数字'
        }
    ],
    "accgUuid": [
        {type: "required", msg: "用户组必须选择"},
        {type: "fn", validator: function(value){
            console.log(value)
            if("-1"==value) {
                return false;
            }else return true;
        }, msg: '用户组必须选择'
        }
    ],
    "orgName": [
        {type: "required", msg: "组织名称必须填写"},
        {type : "maxlength", maxlength : 8, msg : "组织名称长度为100"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            ucasAccountGroup: {},
            urgentFlag:'',
            data:{},
            clientGroup: {}
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("accountGroup/getAll", {}, function(data) {
            if(data.retmsg != 'success') {
                return;
            }

            me.setState({
                data: data.data,
                urgentFlag:data.data
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "realName", "emailAccount","mobileAccount","password",
            "mobilePhone","email","accgUuid","ucasAccount","orgName"
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
            _addButtonDisabled('save');
            $.post(me.state.url,
                $('#saveForm').serialize(),
                function (result) {
                    if (result && result.retcode=='0') {
                        UcsmyIndex.alert("提示", result.retmsg);
                        UcsmyIndex.closeChildrenPage();
                        me.state.callback();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                    _removeButtonDisabled('save');
                }).error(function(xhr, errorText, errorType){
                UcsmyIndex.alert("失败", "网络异常");
                   _removeButtonDisabled('save');
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
        var that = this;
        return (
            React.createElement("div", null, 
            React.createElement("div", {className: "panel"}, 
            React.createElement("div", {className: "panel-title fc-red"}, "帐号管理"), 
        React.createElement("div", {className: "panel-content"}, 
            React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
            React.createElement("input", {type: "hidden", name: "accUuid", value: this.state.ucasAccountGroup.accUuid}), 
        React.createElement(FormItem, {label: "用户名称"}, React.createElement(Input, {name: "realName", value: this.state.ucasAccountGroup.realName})), 
        React.createElement(FormItem, {label: "邮箱帐号"}, React.createElement(Input, {name: "emailAccount", value: this.state.ucasAccountGroup.emailAccount})), 
        React.createElement(FormItem, {label: "手机帐号"}, React.createElement(Input, {name: "mobileAccount", value: this.state.ucasAccountGroup.mobilephone})), 
        React.createElement(FormItem, {label: "用户密码"}, React.createElement(Input, {name: "password", value: this.state.ucasAccountGroup.password})), 
        React.createElement(FormItem, {label: "联系手机"}, React.createElement(Input, {name: "mobilePhone", value: this.state.ucasAccountGroup.mobilephone})), 
        React.createElement(FormItem, {label: "联系邮箱"}, React.createElement(Input, {name: "email", value: this.state.ucasAccountGroup.emailAccount})), 
        React.createElement(FormItem, {label: "网金帐号"}, React.createElement(Input, {name: "ucasAccount", value: this.state.ucasAccountGroup.ucasAccount})), 
        React.createElement(FormItem, {label: "头像地址"}, React.createElement(Input, {name: "headImgUrl", value: this.state.ucasAccountGroup.headImgUrl})), 
        React.createElement(FormItem, {label: "组织名称"}, React.createElement(Input, {name: "orgName", value: this.state.ucasAccountGroup.orgName})), 
        React.createElement(FormItem, {label: "性别", className: "col-xs-5"}, 
            React.createElement(RadioGroup, {name: "sex", value: this.state.clientGroup.isSso == null ? "1" : this.state.clientGroup.isSso}, 
        React.createElement(Radio, {value: "1"}, React.createElement("span", {style: {"padding-left": "20px"}}, "男")), 
        React.createElement(Radio, {value: "2"}, React.createElement("span", {style: {"padding-left": "20px"}}, "女"))
        )
        ), 
        React.createElement(FormItem, {label: "用户组"}, 
            React.createElement(SelectDropDown, {name: "accgUuid", option: that.state.urgentFlag, value: that.state.data, className: "setwidth", ref: "urgentFlag"})
         )
        )
        )
        ), 
        React.createElement("div", {className: "btn-panel"}, 
            React.createElement(Button, {buttonType: "bidnow", id: "save", onClick: this._saveOrUpdate}, "保存"), 
        React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
        )
        )
        )
    }
});

/***/ }),

/***/ 2:
/***/ (function(module, exports) {

//var FormItem = UcsmyUI.FormItem;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var MyForm = React.createClass({displayName: "MyForm",
    getInitialState:function () {
        return {
            values: this.props.data ? this.props.data : {}
        }
    },
    setValues: function(data) {
        var me = this;
        this.setState({
            values: data
        });
    },
    getValues: function() {
        var value = {};
        var me = this;
        this.props.config.map(function(data) {
            if(!data.name)
                return;
            var v = me._getWidget(me.refs[data.ref]);
            if(data.name == 'username') {
                value['name'] = v;
            } else {
                value[data.name] = v;
            }

        });
        return value;
    },
    isValid: function(retFn, validateField) {
        var me = this;
        var fn = function (b) {
            retFn ? retFn(b) : "";
        };
        if(validateField)
            this.refs.form.validate(fn, validateField);
        else
            this.refs.form.validate(fn);
    },
    submit: function(option) {
        var me = this;
        if(option.isValid === true) {
            this.isValid(function(b) {
                b === true ? me._submit(option) : option.error("0");
            }, option.validateField);
        } else {
            me._submit(option);
        }
    },
    _submit: function(option) {
        $.post(option.url, this.getValues(), function(data) {
            option.success(data);
        }, "json").error(function(xhr, errorText, errorType){
            option.error ? option.error("1", xhr, errorText, errorType) : "";
        });
    },
    _get: function(data, p) {
        if(!p)
            return "";
        p.split(".").map(function(d) {
            if(data) {
                data = data[d]
            }
        });
        return data ? data : "";
    },
    _getWidget: function(ref) {
        if(ref.getValue && typeof(ref.getValue) == "function") {
            return ref.getValue();
        }
        return ref.state.value;
    },
    _setWidget: function(ref, value) {
        if(ref.setValue && typeof(ref.setValue) == "function") {
            return ref.setValue(value);
        }
        ref.setState({
            value: value
        });
    },
    _getRules: function(data) {// 获取校验rules
        // 生成验证规则，v_required/v_digits/v_mail/v_maxlength/v_minlength;
        // rules（数组）:ucsmyui原生校验，如{type:"rule",rule: "正则表达式",msg: "msg"}
        var rules = [];
        if(data.v_required === true)
            rules.push({type: "required", msg: data.itemText + "不能为空"});
        if(data.v_digits === true)
            rules.push({type: "digits", msg: data.itemText + "只能为数字"});
        if(data.v_mail === true)
            rules.push({type: "mail", msg: "请输入正确的邮箱地址"});
        if(data.v_mobile === true)
            rules.push({type: "mobile", msg: "请输入正确的手机号码"});
        if(data.v_telephone === true)
            rules.push({type: "rule", rule: "/^(0\\d{2,3}[-]{0,1})?[1-9]{1}\\d{6,7}/", msg: "请输入正确的固定电话"});
        if(data.v_maxlength !== undefined)
            rules.push({type: "maxlength", maxlength: data.v_maxlength, msg: data.itemText + "最大长度是：" + data.v_maxlength});
        if(data.v_minlength !== undefined)
            rules.push({type: "rule", rule: "/[\\s\\S]{" + data.v_minlength + ",}/", msg: data.itemText + "最小长度是：" + data.v_minlength});
        if(data.rules && data.rules.length > 0)
            rules.push(...data.rules);

        return rules;
    },
    render: function() {
        var me = this, array = [], i = 0, formData = {};
        this.props.config.map(function(data) {
            var ref = "panel_" + i;
            data.ref = ref;
            data.value = me._get(me.state.values, (data.valueMap ? data.valueMap : data.name));
            if(data.hidden == true) {
                array.push(
                    React.createElement("div", {style: {"display": "none"}}, 
                        React.createElement(data.panelType, React.__spread({},  data), data.childrenPanel ? data.childrenPanel : "")
                    )
                );
            } else {
                array.push(
                    React.createElement(FormItem, {label: data.itemText, className: data.itemClassName}, 
                        React.createElement(data.panelType, React.__spread({},  data), data.childrenPanel ? data.childrenPanel : "")
                    )
                );
            }

            var rules = me._getRules(data);
            if(rules.length > 0)
                formData[data.name] = rules;

            i++;
        });
        return (
            React.createElement("div", null, 
                React.createElement(Form, {ref: "form", formData: formData, 
                      validateType: this.props.validateType ? this.props.validateType : "1"}, 
                    array
                )
            )
        );
    }
});

module.exports = MyForm;

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

/***/ 5:
/***/ (function(module, exports) {


var Button = UcsmyUI.Button;


module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            ucasAccountGroup: {},
            urgentFlag:'',
            data:{},
            clientGroup: {}
        }
    },

    init: function (url, title, data, callback) {
        var me = this;

        $.post("account/getUser",
            {'ucasAccount':data.ucasAccount},
            function (result) {
                if (result && result.retcode && result.retcode == "0") {
                    me.setState({
                        title: title,
                        url: url,
                        ucasAccountGroup: result.data,
                        callback: callback
                    });

                } else {
                    UcsmyIndex.alert("提示", result.retmsg);
                }
            }).error(function(xhr, errorText, errorType){
            UcsmyIndex.alert("失败", "网络异常");
        });
        // this.refs.saveForm.setValues(data);
    },
    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        var that = this;
        return (
            React.createElement("div", null, 
            React.createElement("div", {className: "panel"}, 
            React.createElement("div", {className: "panel-title fc-red"}, "帐号信息"), 
         React.createElement("div", {className: "panel-content"}, 

            React.createElement("div", {className: "ucs-form-group"}, 
               React.createElement("span", {className: "label"}, "网金帐号："), 
               React.createElement("span", null, this.state.ucasAccountGroup.ucasAccount)
           ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "用户名称："), 
        React.createElement("span", null, this.state.ucasAccountGroup.realName)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "邮箱帐号："), 
        React.createElement("span", null, this.state.ucasAccountGroup.emailAccount)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "手机帐号："), 
        React.createElement("span", null, this.state.ucasAccountGroup.mobileAccount)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "联系手机："), 
        React.createElement("span", null, this.state.ucasAccountGroup.mobilePhone)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "联系邮箱："), 
        React.createElement("span", null, this.state.ucasAccountGroup.email)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "头像地址："), 
        React.createElement("span", null, this.state.ucasAccountGroup.headImgUrl)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "组织名称："), 
        React.createElement("span", null, this.state.ucasAccountGroup.orgName)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "状态："), 
        React.createElement("span", null, this.state.ucasAccountGroup.status=='0' ? '正常':'冻结')
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "组织名称："), 
        React.createElement("span", null, this.state.ucasAccountGroup.orgName)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "性别："), 
        React.createElement("span", null, this.state.ucasAccountGroup.sex=='1' ? '男' : '女')
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "用户组："), 
        React.createElement("span", null, this.state.ucasAccountGroup.groupName)
        ), 
        React.createElement("div", {className: "ucs-form-group"}, 
            React.createElement("span", {className: "label"}, "指纹标识："), 
        React.createElement("span", null, this.state.ucasAccountGroup.fingerprint=='1' ? '有':'无')
        )
        )
        ), 
        React.createElement("div", {className: "btn-panel"}, 

        React.createElement(Button, {buttonType: "bidnow", onClick: this._return}, "返回")
        )
        )
        )
    }
});

/***/ }),

/***/ 52:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var UserForm = __webpack_require__(19);
var UserDetailForm = __webpack_require__(5);
var PasswordForm = __webpack_require__(16);
var EmailForm = __webpack_require__(15);
var PhoneForm = __webpack_require__(17);
var PermissionLink = __webpack_require__(1);
var PermissionButton = __webpack_require__(3);
var Form = __webpack_require__(2);
var UpdateuserDetailForm = __webpack_require__(18);
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;
var SelectDropDown = UcsmyUI.SelectDropDown;

var option = [
	{option: '正常', value: '0'},
	{option: '冻结', value: '1'}
];

myPanel = React.createClass({displayName: "myPanel",
	getInitialState:function () {
        return{
			urgentFlag:'',
			data:{},
        }
    },
	componentDidMount: function() {
		var me = this;
		$.post("accountGroup/getAll", {}, function(data) {
			if(data.retmsg != 'success') {
				return;
			}

			me.setState({
				data: data.data,
				urgentFlag:data.data
			});
		}, "json").error(function(xhr, errorText, errorType){
		});
	},
    _query: function() {
		{/*this.refs.grid.load(this.refs.queryForm.getValues());*/}

		this.refs.grid.load({
			ucasAccount: this.refs.ucasAccount.getValue(),
			emailAccount: this.refs.emailAccount.getValue(),
			mobileAccount: this.refs.mobileAccount.getValue(),
			realName: this.refs.realName.getValue(),
			status: this.refs.sta.getValue(),
			accgUuid: this.refs.accgUuid.getValue()
		});

    },
    _add: function() {
    	var me = this;
    	UcsmyIndex.openChildrenPage(UserForm, function(refPanel) {
    		refPanel.init('account/addUser','新增帐号',  {gender: "1"}, function(){
    			me.refs.grid.load();
				//me._reload();
    		});
    	});

    },
	_freeze:function(column,event)
	{
		var me = this;
		UcsmyIndex.confirm("确定", "你真的要冻结该用户吗？", function() {
			$.post("account/freeze", {ucasAccount: column.ucasAccount}, function(data) {
				if(data.retcode == 0) {
					UcsmyIndex.alert("成功", data.retmsg);
					me.refs.grid.reload();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
			});
		});
	},
	_unfreeze:function(column,event)
	{
		var me = this;
		UcsmyIndex.confirm("确定", "你真的要解冻该用户吗？", function() {
			$.post("account/unfreeze", {ucasAccount: column.ucasAccount}, function(data) {
				if(data.retcode == 0) {
					UcsmyIndex.alert("成功", data.retmsg);
					me.refs.grid.reload();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
			});
		});
	},
	_mod_email:function(column,event)
	{
		var me = this;
		UcsmyIndex.openChildrenPage(EmailForm, function(refPanel) {
			refPanel.init(column,function(){
				me.refs.grid.load();
			});
		});
	},
	_mod_mobile:function(column,event)
	{
		var me = this;
		UcsmyIndex.openChildrenPage(PhoneForm, function(refPanel) {
			refPanel.init(column,function(){
				me.refs.grid.load();
			});
		});
	},
    _update: function(column, event) {
		var me = this;
		UcsmyIndex.openChildrenPage(UpdateuserDetailForm, function(refPanel) {
			refPanel.init('account/upInfo','修改基本信息',  column, function(){
				//me.refs.grid.load();
				me.refs.grid.reload();
			});
		});
    },
    _updatePassword: function(column, event) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(PasswordForm, function(refPanel) {
    		refPanel.init(column);
    	});
    },
	_detail:function(column, event) //基本信息
	{
		var me = this;
		UcsmyIndex.openChildrenPage(UserDetailForm, function(refPanel) {
			refPanel.init('account/upInfo','修改基本信息',  column, function(){
				//me.refs.grid.load();
				me.refs.grid.reload();
			});
		});
	},
    _delete: function(column, event) {
    	var me = this;
		UcsmyIndex.confirm("确定", "你真的要删除该用户吗？", function() {
			$.post("account/delete", {ucasAccount: column.ucasAccount}, function(data) {
				if(data.retcode == 0) {
					UcsmyIndex.alert("成功", data.retmsg);
					me.refs.grid.reload();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
		    });
		});
    },
	render: function() {
		var me = this;
		return (
			React.createElement("div", null, 
			     React.createElement("h2", {className: "content-title"}, "用户管理"), 
			    React.createElement("div", {className: "panel"}, 
			            React.createElement("div", {className: "panel-title"}, "查询条件"), 
						React.createElement("div", {className: "panel-content"}, 
						     React.createElement(FormItem, {label: "网金帐号"}, React.createElement(Input, {ref: "ucasAccount"})), 
			                 React.createElement(FormItem, {label: "邮箱帐号"}, React.createElement(Input, {ref: "emailAccount"})), 
			                 React.createElement(FormItem, {label: "手机帐号"}, React.createElement(Input, {ref: "mobileAccount"})), 
			                 React.createElement(FormItem, {label: "用户名称"}, React.createElement(Input, {ref: "realName"})), 
						    React.createElement(FormItem, {label: "状态"}, 
							  React.createElement(SelectDropDown, {ref: "sta", defaultText: "请选择", defaultValue: "", option: option, searchPlaceholder: "请选择"})
							), 
							React.createElement(FormItem, {label: "用户组"}, 
								React.createElement(SelectDropDown, {ref: "accgUuid", defaultText: "请选择", defaultValue: "", option: me.state.urgentFlag})

							)
					    )
		        ), 
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(PermissionButton, {permissionName: "user_add", buttonType: "bidnow", onClick: this._add}, "新增")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {
		            	url: "account/queryUsers", ref: "grid", 
		                isTextOverflowHidden: true, 
		                columns: [ 
         				    {
          						name: 'realName', header: '用户名称',width: 80
          					},
							{
							name: 'ucasAccount', header: '网金帐号',width: 80
							},
		                   {
          						name: 'emailAccount', header: '邮箱帐号',width: 100
          					}, {
          						name: 'mobileAccount', header: '手机帐号',width: 120
          					}, {
          						name: 'orgName', header: '组织名称',width: 60
          					}
		                  , {
			                    name: 'groupName', header: '用户组',width: 100
		                    }, {
			                    name: 'modifyDate', header: '最近修改时间',width: 150
		                      }
		                     , {
          						name: 'status', header: '状态',width: 30, content:function(column){
         				    		 return (React.createElement("span", null, 
        				    		 	column.status == '0' ? '正常' : '冻结'
        				    		 ))
        						}
          					}, {
          						name: 'cz',
          						header: '操作',
          						permissionName: 'account_update,account_delete,account_freeze,account_unfreeze,account_mod_email,account_mod_mobile,account_update_password,account_detail',
          						content:function(column){
          							return (React.createElement("span", null, 
				                        React.createElement(PermissionLink, {permissionName: "account_update", href: "Javascript:void(0);", onClick: me._update.bind(this, column)}, "修改   "), 
      				    		 		React.createElement(PermissionLink, {permissionName: "account_delete", href: "Javascript:void(0);", onClick: me._delete.bind(this, column)}, "删除   "), 
										React.createElement(PermissionLink, {permissionName: "account_freeze", href: "Javascript:void(0);", onClick: me._freeze.bind(this, column)}, "冻结   "), 
				                        React.createElement(PermissionLink, {permissionName: "account_unfreeze", href: "Javascript:void(0);", onClick: me._unfreeze.bind(this, column)}, "解冻   "), 
      				    		 		React.createElement(PermissionLink, {permissionName: "account_mod_email", href: "Javascript:void(0);", onClick: me._mod_email.bind(this, column)}, "修改邮箱帐号     ", React.createElement("br", null)), 
				                       React.createElement(PermissionLink, {permissionName: "account_mod_mobile", href: "Javascript:void(0);", onClick: me._mod_mobile.bind(this, column)}, "修改手机帐号   "), 
				                       React.createElement(PermissionLink, {permissionName: "account_update_password", href: "Javascript:void(0);", onClick: me._updatePassword.bind(this, column)}, "密码修改   "), 
				                       React.createElement(PermissionLink, {permissionName: "account_detail", href: "Javascript:void(0);", onClick: me._detail.bind(this, column)}, "详情   ")

				))
          				    	 }
          					}
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