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
/******/ 	return __webpack_require__(__webpack_require__.s = 76);
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

/***/ 2:
/***/ (function(module, exports) {

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
        $.merge(rules,data.rules);

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

/***/ 49:
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
		},
        {type : "maxlength", maxlength : 32, msg : "密码长度不能超过32"}
	],
	"newPassword": [
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
			"password","newPassword"
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
			$.post("user/updateUserPassword",
				$('#saveForm').serialize(),
				function (result) {
                    _removeButtonDisabled('save');
					if (result && result.retcode && result.retcode == "0") {
						UcsmyIndex.alert("提示", result.retmsg);
						UcsmyIndex.closeChildrenPage();
						me.state.callback();
					} else {
						UcsmyIndex.alert("提示",result.retmsg);
					}
				}).error(function(){
                _removeButtonDisabled('save');
				UcsmyIndex.alert("失败", "网络异常");
			});
		});
	},
	init: function (data) {
		this.setState({
			title: '密码修改',
			ucasAccount: data
		});
	},
	_return: function () {
		UcsmyIndex.closeChildrenPage();
	},
	render: function () {
		return (
			React.createElement("div", null, 
			React.createElement("div", {className: "panel"}, 
			React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
		React.createElement("div", {className: "panel-content"}, 
			React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
			React.createElement("input", {type: "hidden", name: "userId", value: this.state.ucasAccount.userId}), 
			React.createElement(FormItem, {label: "用户名称"}, React.createElement(Input, {name: "account", disabled: "true", value: this.state.ucasAccount.name})), 
		React.createElement(FormItem, {label: "新密码"}, React.createElement(Input, {type: "password", name: "password"})), 
		React.createElement(FormItem, {label: "确认密码"}, React.createElement(Input, {type: "password", name: "newPassword"}))
			)
			)
			), 
			React.createElement("div", {className: "btn-panel"}, 
			React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._saveOrUpdate}, "保存"), 
		React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
		)
		)
		)
	}
});

/***/ }),

/***/ 50:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;
var Form = __webpack_require__(2);

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function() {
        return {
            url: '',
            title: 'title',
            successFn: function(){},
            isUpdate: false,
            role: []
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("role/queryAllRoleList", {}, function(data) {
            if(data.success != true) {
                return;
            }
            var array = [];
            data.roleList.map(function(data) {
                array.push({
                    option: data.name,
                    value: data.roleId
                });
            });
            me.setState({
                role: array
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    init: function(title, url, data, successFn, isUpdate) {
        this.setState({
            title: title,
            url: url,
            successFn: successFn,
            isUpdate: isUpdate == true
        });
        data.username = data.name;
        this.refs.saveForm.setValues(data);
    },
    _return: function(event) {
        UcsmyIndex.closeChildrenPage();
    },
    _save: function(event) {

        var me = this;
        _addButtonDisabled('save');
        this.refs.saveForm.submit({
            url: this.state.url,
            isValid: true,
            success: function(data) {
                _removeButtonDisabled('save');
                if(data.retcode == 0) {
                    UcsmyIndex.alert("成功","保存成功");
                    me.state.successFn();
                    me._return();
                } else {
                    UcsmyIndex.alert("失败", data.retmsg);
                }
            },
            error: function(type, xhr, errorText, errorType) {
                _removeButtonDisabled('save');
                if(type != "0") {
                    UcsmyIndex.alert("失败", "网络异常");
                }
            }
        });
    },
    render: function() {
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
                    React.createElement("div", {className: "panel-content"}, 

                        React.createElement(Form, {config: [{
                            panelType: Input,
                            hidden: true,
                            name: "userId"
                        }, {
                            itemText: "登录名",
                            itemClassName: "col-xs-5",
                            panelType: Input,
                            name: "account",
                            valueMap: "userAccount.account",
                            v_required: true,
                            v_minlength: 2,
                            v_maxlength: 10,
                            disabled: this.state.isUpdate
                        }, {
                            itemText: "用户姓名",
                            itemClassName: "col-xs-5",
                            panelType: Input,
                            name: "username",
                            v_required: true,
                            v_minlength: 2,
                            v_maxlength: 4
                        }, {
                            itemText: "固定电话",
                            itemClassName: "col-xs-5",
                            panelType: Input,
                            name: "telephone",
                            v_telephone: true,
                            v_maxlength: 20
                        }, {
                            itemText: "手机",
                            itemClassName: "col-xs-5",
                            panelType: Input,
                            name: "mobilephone",
                            v_required: true,
                            v_mobile: true
                        }, {
                            itemText: "邮箱",
                            itemClassName: "col-xs-5",
                            panelType: Input,
                            name: "email",
                            v_required: true,
                            v_mail: true,
                            v_maxlength: 32
                        }, {
                            itemText: "性别",
                            itemClassName: "col-xs-5",
                            panelType: RadioGroup,
                            name: "gender",
                            v_required: true,
                            childrenPanel: [React.createElement(Radio, {value: "1"}, React.createElement("span", {style: {"padding-left": "10px"}}), "男", React.createElement("span", {style: {"padding-left": "40px"}})),
                                React.createElement(Radio, {value: "2"}, React.createElement("span", {style: {"padding-left": "10px"}}), "女")]
                        }, {
                            itemText: "角色",
                            itemClassName: "col-xs-5",
                            panelType: SelectDropDown,
                            name: "role.roleId",
                            option: this.state.role && this.state.role.length > 0 ? this.state.role : undefined,
                            defaultText: "请选择",
                            defaultValue: "",
                            className: "setwidth",
                            v_required: true
                        }], ref: "saveForm"})
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._save}, "保存"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
                )
            )
        );
    }
});

/***/ }),

/***/ 76:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var UserForm = __webpack_require__(50);
var PasswordForm = __webpack_require__(49);
var PermissionLink = __webpack_require__(1);
var PermissionButton = __webpack_require__(3);
var Form = __webpack_require__(2);

myPanel = React.createClass({displayName: "myPanel",
	getInitialState:function () {
        return{
        }
    },
    _query: function() {
    	this.refs.grid.load(this.refs.queryForm.getValues());
    },
    _add: function() {
    	var me = this;
    	UcsmyIndex.openChildrenPage(UserForm, function(refPanel) {
    		refPanel.init('新增用户', 'user/add', {gender: "1"}, function(){
    			me.refs.grid.load();
    		});
    	});
    },
    _update: function(column) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(UserForm, function(refPanel) {
    		refPanel.init('修改用户', 'user/update', column, function(){
    			me.refs.grid.load();
    		}, true);
    	});
    },
    _updatePassword: function(column) {
    	UcsmyIndex.openChildrenPage(PasswordForm, function(refPanel) {
    		refPanel.init(column);
    	});
    },
    _delete: function(column) {
    	var me = this;
		UcsmyIndex.confirm("确定", "你真的要删除该用户数据吗？", function() {
			$.post("user/delete", {userId: column.userId}, function(data) {
				if(data.retcode == 0) {
					UcsmyIndex.alert("成功", data.retmsg);
					me.refs.grid.reload();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(){
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
	                    React.createElement(Form, {ref: "queryForm", config: [{
	                    	itemText: "用户姓名",
	                    	panelType: Input,
	                    	name: "name"
	                    }]})
	                )
	            ), 
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(PermissionButton, {permissionName: "user_add", buttonType: "bidnow", onClick: this._add}, "新增")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {
		            	retDataProperty: "data.resultList", 
	            		retTotalProperty: "data.totalCount", 
	            		retCurrentProperty: "data.pageNo", 
		            	url: "user/query", ref: "grid", 
		                columns: [ 
         				    {
          						name: 'account', header: '登录名', content:function(column){
          				    		 return React.createElement("span", null, column.userAccount.account);
          				    	}
          					}, {
          						name: 'name', header: '用户姓名'
          					}, {
          						name: 'telephone', header: '固定电话'
          					}, {
          						name: 'mobilephone', header: '手机'
          					}, {
          						name: 'email', header: '邮箱'
          					}, {
          						name: 'gender', header: '性别', content:function(column){
          				    		 return (React.createElement("span", null, 
          				    		 	column.gender == '1' ? '男' : '女'
          				    		 ))
          						}
          					}, {
          						name: 'roleName', header: '角色', content:function(column){
         				    		 return (React.createElement("span", null, 
        				    		 	column.role == null ? '' : column.role.name
        				    		 ))
        						}
          					}, {
          						name: 'cz',
          						header: '操作',
          						permissionName: 'user_update,user_delete',
          						content:function(column){
          							return (React.createElement("span", null, 
      				    		 		React.createElement(PermissionLink, {permissionName: "user_update", href: "Javascript:void(0);", onClick: me._update.bind(this, column)}, "修改   "), 
      				    		 		React.createElement(PermissionLink, {permissionName: "user_update_password", href: "Javascript:void(0);", onClick: me._updatePassword.bind(this, column)}, "密码修改   "), 
      				    		 		React.createElement(PermissionLink, {permissionName: "user_delete", href: "Javascript:void(0);", onClick: me._delete.bind(this, column)}, "删除")
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