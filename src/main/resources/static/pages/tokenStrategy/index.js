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
/******/ 	return __webpack_require__(__webpack_require__.s = 71);
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

/***/ 4:
/***/ (function(module, exports, __webpack_require__) {

var Form = __webpack_require__(2);

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function(){
		return {
			client:{},
            showReturn: true,
            showTokenStrategy: true,
            tokenStrategy: {},
            hasTokenStrategy: "0"
		}
	},
	init: function(data, showReturn, showTokenStrategy) {
		// console.log("clientName="+data.clientName);
		//var me = this;
		////更新CheckBox组件,必须通过调用组件setChecked方法才有效
		//if(data.grantType !== undefined && data.grantType !== null){
		//	data.grantType.split(",").map(function (grantTypeName) {
		//		me.refs[grantTypeName].setChecked(true);
		//	})
		//}
		/* 是否展示返回按钮 */
		var showRet = true;
		if (showReturn != null) {
            showRet = showReturn;
		}
		/* 是否展示自定义Token策略 */
		var showTokenST = true;
		if (showTokenStrategy != null) {
			showTokenST = showTokenStrategy
		}
		this.setState({
			client: data,
            showReturn: showRet,
            showTokenStrategy: showTokenST
		}, () => {
            this._loadTokenStrategy(data.clientId);
        });
	},
	_return: function(event) {
		UcsmyIndex.closeChildrenPage();
	},
    _loadTokenStrategy: function (clientId) {
        if (clientId == null || clientId == '') {
            return;
        }
        var me = this;
        $.post("tokenStrategy/queryTokenStrategyByClientId", {clientId: clientId}, function(data) {
            if(data.retcode != 0) {
                return;
            }
            var tokenStrategy = {};
            var hasTokenStrategy = "0";
            if (data.data != null && data.data.length > 0) {
                tokenStrategy = data.data[0];
                hasTokenStrategy = "1";
            }
            me.setState({
                tokenStrategy: tokenStrategy,
                hasTokenStrategy: hasTokenStrategy
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
	render: function(){
		var returnBtn = [];
		if (this.state.showReturn != null && this.state.showReturn) {
            returnBtn.push(
				React.createElement("div", {className: "btn-panel"}, 
					React.createElement(Button, {buttonType: "bidnow", onClick: this._return}, "返回")
				)
			)
		}
		var tokenStrategy = [];
		if (this.state.showTokenStrategy != null && this.state.showTokenStrategy && this.state.hasTokenStrategy == "1") {
            tokenStrategy.push(
				React.createElement("div", {className: "panel"}, 
					React.createElement("div", {className: "panel-title fc-red"}, "自定义Token策略"), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "最大使用次数："), 
						React.createElement("span", null, this.state.tokenStrategy.maxTimes)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label", style: {'width': '120px'}}, "策略ID："), 
						React.createElement("span", null, this.state.tokenStrategy.uuid)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "Token时效："), 
						React.createElement("span", null, this.state.tokenStrategy.expiryDate)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label", style: {'width': '120px'}}, "刷新Token时效："), 
						React.createElement("span", null, this.state.tokenStrategy.refreshExpiryDate)
					)
				)
            );
		}
		return (
			React.createElement("div", null, 
				React.createElement("div", {className: "panel"}, 
					React.createElement("div", {className: "panel-title fc-red"}, "应用信息"), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用简称："), 
						React.createElement("span", null, this.state.client.clientName)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用ID："), 
						React.createElement("span", null, this.state.client.clientId)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用密钥："), 
						React.createElement("span", null, this.state.client.clientSecret)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "授权类型："), 
						React.createElement("span", null, this.state.client.grantType)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用组："), 
						React.createElement("span", null, this.state.client.ucasClientGroup?this.state.client.ucasClientGroup.groupName:'')
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "重定向URL："), 
						React.createElement("span", null, this.state.client.clientUrl)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用状态："), 
						React.createElement("span", null, this.state.client.status == '0' ? '可用' : '停用')
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用描述："), 
						React.createElement("span", null, this.state.client.descRibe)
					)
				), 
				tokenStrategy, 
				returnBtn
			)
	    );
	 }
});

/***/ }),

/***/ 46:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/5/11.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = UcsmyUI.Form.FormItem;
var ClientInfo = __webpack_require__(4);

var formData = {

    "maxTimes": [
        {type: "required", msg: "最大使用次数不能为空"},
        {type: "digits", msg: "最大使用次数只能为数字"},
        {type : "maxlength", maxlength : 11, msg : "参数值长度不能超过11"}
    ],
    "expiryDate": [
        {type: "digits", msg: "Token时效只能为数字"},
        {type: "required", msg: "Token时效不能为空"},
        {type : "maxlength", maxlength : 11, msg : "参数值长度不能超过11"}
    ],
    "refreshExpiryDate": [
        {type: "digits", msg: "刷新Token时效只能为数字"},
        {type: "required", msg: "刷新Token时效不能为空"},
        {type : "maxlength", maxlength : 11, msg : "参数值长度不能超过11"}
    ]

};
module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            data: {},
            url: "tokenStrategy/update",
            successFn: function() {}
        }
    },

    init: function (data, successFn) {
        this.refs.clientInfo.init(data.ucasClientInfo, false, false);
        this.setState({
            data: data,
            successFn: successFn
        })
    },

    _validate: function (callback) {
        var status = false;
        var validateField = [
            "maxTimes", "expiryDate", "refreshExpiryDate"
        ];
        var fn = function (result) {
            console.log(result);
            if(result) {
                callback();
            }
        };

        this.refs.saveForm.validate(fn, validateField);

        return status;
    },

    _return: function(event) {
        UcsmyIndex.closeChildrenPage();
    },

    _save: function(event) {
        var rootThis = this;
        this._validate(function () {
            _addButtonDisabled('save');
            $.post(
                rootThis.state.url,
                $('#form').serialize(),
                function (result) {
                    _removeButtonDisabled('save');
                    if (result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        rootThis.state.successFn();
                        rootThis._return();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }, "json").error(function(xhr, errorText, errorType){
                _removeButtonDisabled('save');
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },

    render: function () {
        var rootThis = this;
        return(
            React.createElement("div", null, 
                React.createElement(ClientInfo, {ref: "clientInfo"}), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, "修改Token策略"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: formData}, 
                            React.createElement("input", {type: "hidden", name: "uuid", value: rootThis.state.data.uuid}), 
                            React.createElement("input", {type: "hidden", name: "clientId", value: rootThis.state.data.clientId}), 
                            React.createElement(FormItem, {label: "最大使用次数"}, 
                                React.createElement(Input, {name: "maxTimes", value: rootThis.state.data.maxTimes})
                            ), 
                            React.createElement(FormItem, {label: "Token时效"}, 
                                React.createElement(Input, {name: "expiryDate", value: rootThis.state.data.expiryDate, placeholder: "单位（秒）"})
                            ), 
                            React.createElement(FormItem, {label: "刷新Token时效"}, 
                                React.createElement(Input, {name: "refreshExpiryDate", value: rootThis.state.data.refreshExpiryDate, placeholder: "单位（秒）"})
                            )
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: rootThis._save}, "保存"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: rootThis._return}, "取消")
                )
            )
        )
    }
});

/***/ }),

/***/ 71:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/5/11.
 */
var Input = UcsmyUI.Input;
var FormItem = UcsmyUI.Form.FormItem;
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);
var PermissionLink = __webpack_require__(1);
var ClientInfo = __webpack_require__(4);
var Form = __webpack_require__(46);

myPanel = React.createClass({displayName: "myPanel",

    _search: function () {
        this.refs.grid.load({
            clientId: this.refs.clientId.getValue(),
            clientName: this.refs.clientName.getValue()
        });
    },

    _clientDetail: function (item) {
        UcsmyIndex.openChildrenPage(ClientInfo, function(refPanel) {
            refPanel.init(item.ucasClientInfo);
        });
    },

    _edit: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init(item, function(){
                me.refs.clientId.setValue('');
                me.refs.clientName.setValue('');
                me.refs.grid.load();
            });
        });
    },

    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "是否确定删除该Token策略吗？", function() {
            $.post("tokenStrategy/delete", {uuid: item.uuid}, function(data) {
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

    render: function () {
        var rootThis = this;
        return(
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "Token策略管理"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "应用id"}, React.createElement(Input, {ref: "clientId"})), 
                        React.createElement(FormItem, {label: "应用简称"}, React.createElement(Input, {ref: "clientName"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: rootThis._search}, "查询")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: "tokenStrategy/queryList", ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                              {name: 'clientId', header: '所属应用id', width: 270},
                              {name: 'clientName', header: '应用简称', width: 150, content: function (item) {
                                  return(
                                      React.createElement("span", null, 
                                          item.ucasClientInfo == null ? '' : item.ucasClientInfo.clientName
                                      )
                                  )
                              }},
                              {name: 'maxTimes', header: '最大使用次数', width: 100},
                              {name: 'expiryDate', header: 'TOKEN有效期', width: 100},
                              {name: 'refreshExpiryDate', header: 'RefreshToken有效期', width: 200},
                              {name: 'cz', header: '操作', content: function (item) {
                                  return (
                                      React.createElement("div", null, 
                                          React.createElement(PermissionLink, {permissionName: "token_strategy_query", href: "Javascript:void(0);", onClick: rootThis._clientDetail.bind(this, item)}, "  查看应用详情  "), 
                                          React.createElement(PermissionLink, {permissionName: "token_strategy_edit", href: "Javascript:void(0);", onClick: rootThis._edit.bind(this, item)}, "  修改  "), 
                                          React.createElement(PermissionLink, {permissionName: "token_strategy_delete", href: "Javascript:void(0);", onClick: rootThis._delete.bind(this, item)}, "  删除  ")
                                      )
                                  )
                              }}
                          ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        )
    }
});

/***/ })

/******/ });