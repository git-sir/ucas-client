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
/******/ 	return __webpack_require__(__webpack_require__.s = 56);
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

/***/ 22:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/28.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);


var formData = {
    "groupName": [
        {type: "required", msg: "资源组名称不能为空"},
        {type: "maxlength", maxlength: 50, msg: "资源组名称长度不能超过50"},
        {type: "fn", validator: function(value){
            if(""!=value) {

                var m =/^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;

                return m.test(value);
            }else return true;
        }, msg: '只能为大小写字母、数字、下划线'
        }
    ],
    "descRibe": [
        {type: "required", msg: "资源组描述不能为空"},
        {type: "maxlength", maxlength: 200, msg: "资源组描述长度不能超过200"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            client: {},
            gridUrl: null,
            url: "resourceGroup/add"
        }
    },

    init: function (item, url) {
        var queryData = {
            clientId: item.clientId
        }
        this.setState({
            client: item,
            gridUrl: url
        }, () => {
            this.refs.grid.load(queryData);
        });
    },

    _validate: function (callback) {
        var validateField = [
            "groupName", "descRibe"
        ];

        var fn = function (result) {
            if (result) {
                callback();
            }
        };

        this.refs.saveForm.validate(fn, validateField);
    },

    _saveOrUpdate: function () {
        var rootThis = this;
        this._validate(function () {
            _addButtonDisabled('save');
            $.post(
                rootThis.state.url,
                $('#form').serialize(),
                function (result) {
                    _removeButtonDisabled('save');
                    if (result.retcode == "0") {
                        rootThis.refs.groupName.setValue('');
                        rootThis.refs.descRibe.setValue('');
                        rootThis.refs.grid.load({clientId: rootThis.state.client.clientId});
                        UcsmyIndex.alert("提示", result.retmsg);
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
                , "json").error(function () {
                    _removeButtonDisabled('save');
                    UcsmyIndex.alert("失败", "网络异常");
                }
            );
        });
    },

    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },

    render: function () {
        var rootThis = this;
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, "添加资源组"), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "应用ID："), 
                        React.createElement("span", null, rootThis.state.client.clientId)
                    ), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "应用简称："), 
                        React.createElement("span", null, rootThis.state.client.clientName)
                    ), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "应用组："), 
                        React.createElement("span", null, rootThis.state.client.ucasClientGroup?rootThis.state.client.ucasClientGroup.groupName:'')
                    ), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: formData}, 
                            React.createElement("input", {type: "hidden", name: "clientId", value: rootThis.state.client.clientId}), 
                            React.createElement(FormItem, {label: "资源组名称", className: "col-xs-5"}, React.createElement(Input, {ref: "groupName", name: "groupName"})), 
                            React.createElement(FormItem, {label: "资源组描述", className: "col-xs-5"}, React.createElement(Input, {ref: "descRibe", name: "descRibe"}))
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._saveOrUpdate}, "保存"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
                ), 
                React.createElement("h3", {className: "content-title"}, "应用已有资源组"), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: rootThis.state.gridUrl, ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                              {name: 'groupName', header: '资源组名称', width: 300},
                              {name: 'descRibe', header: '资源组描述', width: 400}
                          ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        )
    }
});

/***/ }),

/***/ 23:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Checkbox =UcsmyUI.Checkbox;
var Radio = UcsmyUI.Radio;
var RadioGroup = Radio.RadioGroup;

var clientFormData = {

    "clientName": [
        {type: "required", msg: "应用简称不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数名称长度不能超过100"},
        {type: "fn", validator: function(value){
            if(""!=value) {

                var m =/^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;

                return m.test(value);
            }else return true;
        }, msg: '只能为大小写字母、数字、下划线'
        }
    ],
    "descRibe": [
        {type: "required", msg: "应用描述不能为空"},
        {type : "maxlength", maxlength : 200, msg : "参数值长度不能超过200"}
    ],
    "clientUrl": [
        {type : "maxlength", maxlength : 200, msg : "参数值长度不能超过200"}
    ],
    "maxTimes": [
        {type: "digits", msg: "最大使用次数只能为数字"},
        {type : "maxlength", maxlength : 9, msg : "参数值长度不能超过9"}
    ],
    "expiryDate": [
        {type: "digits", msg: "Token时效只能为数字"},
        {type: "required", msg: "Token时效不能为空"},
        {type : "maxlength", maxlength : 9, msg : "参数值长度不能超过9"}
    ],
    "refreshExpiryDate": [
        {type: "digits", msg: "刷新Token时效只能为数字"},
        {type: "required", msg: "刷新Token时效不能为空"},
        {type : "maxlength", maxlength : 9, msg : "参数值长度不能超过9"}
    ]

};

module.exports = React.createClass({displayName: "module.exports",
    getDefaultProps: function(){
        return {
            //GRANT_TYPE: ['authorization_code','password','client_credentials','proxy_authorization_code']
        }
    },
    getInitialState: function(){
        return {
            url: '',
            title: 'title',
            successFn: function() {},
            client: {},
            clientGroup: [],
            cligUuid: '',
            grantType: '',
            tokenStrategy: {},
            hasTokenStrategy: "0",
            GRANT_TYPE:[]
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("client_info/queryAllClientGroup", {}, function(data) {
            if(data.retcode != 0) {
                return;
            }
            var array = [];
            data.data.map(function(data) {
                array.push({
                    option: data.groupName,
                    value: data.cligUuid
                });
            });
            me.setState({
                clientGroup: array
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
        $.post("client_info/queryAllGrantType", {}, function(data) {
            if(data.retcode != 0) {
                return;
            }
            console.log("加载到所有授权类型");
            console.log(data.data);
            //页面加载授权类型对应的CheckBox组件
            me.setState({
                GRANT_TYPE: data.data
            });
            //更新CheckBox组件,必须通过调用组件setChecked方法才有效
            if(me.state.client.grantType !== undefined && me.state.client.grantType !== null){
                me.state.client.grantType.split(",").map(function (grantTypeName) {
                    if(me.refs[grantTypeName] !== undefined){
                        me.refs[grantTypeName].setChecked(true);
                    }
                })
            }
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "clientName", "descRibe", "clientUrl"
        ];
        if (this.refs.hasTokenStrategy.getValue() == "1") {
            validateField = [
                "clientName", "descRibe", "clientUrl", "maxTimes", "expiryDate", "refreshExpiryDate"
            ];
        }
        var fn = function (result) {
            console.log(result);
            if(result) {
                callback();
            }
        };

        this.refs.saveForm.validate(fn, validateField);

        return status;
    },
    init: function(title, url, data, successFn) {
        this.setState({
            title: title,
            url: url,
            successFn: successFn,
            client: data,
            cligUuid: data.cligUuid
        }, () => {
            this._loadTokenStrategy(data.clientId);
        });
    },
    _return: function(event) {
        UcsmyIndex.closeChildrenPage();
    },
    _save: function(event) {
        var me = this;
        this._validate(function(){
            _addButtonDisabled('save');
            var selectedGroupId = me.refs.clientGroupDropDown.getValue();
            me.state.cligUuid = selectedGroupId;
            var array = [];
            var authorization_code_selected = false;
            //me.props.GRANT_TYPE.map(function(data,index){
            me.state.GRANT_TYPE.map(function(data,index){
                if(me.refs[data].getChecked()){
                    array.push(data);
                    if(data == 'authorization_code'){   //判断授权类型是否有选择'authorization_code'
                        authorization_code_selected = true;
                    }
                }
            });
            if(array.length == 0){
                UcsmyIndex.alert("提示", "请选择授权类型");
                _removeButtonDisabled('save');
                return;
            }
            if(authorization_code_selected){
                var clientUrlValue = me.refs.clientUrl.getValue();
                if(clientUrlValue == undefined || clientUrlValue == ''){
                    UcsmyIndex.alert("提示", "选择authorization_code授权类型必须填写重定向URL");
                    _removeButtonDisabled('save');
                    return;
                }
            }
            //每次都先清空一下me.state.grantType,以免操作失败时保留着旧数据,造成数据重复添加
            me.state.grantType = '';
            array.map(function(data,index){
                me.state.grantType += data;
                if(index < array.length-1){
                    me.state.grantType += ',';
                }
            });

            me.setState({
                grantType: me.state.grantType
            });
            $.post(me.state.url,
                $('#form').serialize(),
                function(data) {
                    _removeButtonDisabled('save');
                    if(data.retcode == 0) {
                        UcsmyIndex.alert("成功", data.retmsg);
                        me.state.successFn();
                        me._return();
                    } else {
                        UcsmyIndex.alert("失败", data.retmsg);
                    }
                }, "json").error(function(xhr, errorText, errorType){
                    _removeButtonDisabled('save');
                    UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    _customTokenStrategy: function (item) {
        if (item == '1') {
            $('#customTokenStrategy').attr('style', 'display');
        } else {
            $('#customTokenStrategy').attr('style', 'display:none');
        }
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
            me._customTokenStrategy(hasTokenStrategy);
            me.setState({
                tokenStrategy: tokenStrategy,
                hasTokenStrategy: hasTokenStrategy
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    render: function() {
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: clientFormData}, 
                            React.createElement("input", {type: "hidden", name: "clientId", value: this.state.client.clientId}), 
                            React.createElement("input", {type: "hidden", name: "cligUuid", value: this.state.cligUuid}), 
                            React.createElement("input", {type: "hidden", name: "grantType", value: this.state.grantType}), 
                            React.createElement(FormItem, {label: "应用简称"}, 
                                React.createElement(Input, {name: "clientName", value: this.state.client.clientName})
                            ), 
                            React.createElement(FormItem, {label: "应用描述"}, 
                                React.createElement(Input, {name: "descRibe", value: this.state.client.descRibe})
                            ), 
                            React.createElement(FormItem, {label: "重定向URL"}, 
                                React.createElement(Input, {name: "clientUrl", value: this.state.client.clientUrl, ref: "clientUrl"})
                            ), 
                            React.createElement(FormItem, {label: "应用组"}, 
                                React.createElement(SelectDropDown, {
                                    option: this.state.clientGroup, 
                                    //defaultText="请选择"
                                    ref: "clientGroupDropDown", 
                                    value: this.state.client.cligUuid}
                                )
                            ), 
                            this.state.client.clientSecret?(React.createElement(FormItem, {label: "应用密钥"}, 
                                React.createElement(Input, {name: "clientSecret", value: this.state.client.clientSecret})
                            )):'', 
                            React.createElement(FormItem, {label: "授权类型", className: "shouquan-type"}, 
                                
                                    this.state.GRANT_TYPE ?
                                    this.state.GRANT_TYPE.map(function(data,index){
                                        return React.createElement(Checkbox, {text: data, value: data, ref: data})
                                    }):''
                                
                            ), 
                            React.createElement("div", {className: "ucs-form-group"}, 
                                React.createElement("span", null, "自定义Token策略："), 
                                React.createElement("span", {style: {'margin-left': '10px'}}, 
                                React.createElement(RadioGroup, {name: "hasTokenStrategy", ref: "hasTokenStrategy", value: this.state.hasTokenStrategy == null ? "0" : this.state.hasTokenStrategy, onChange: this._customTokenStrategy}, 
                                    React.createElement(Radio, {value: "1"}, React.createElement("span", {style: {"padding-left": "5px"}}, "是")), 
                                    React.createElement(Radio, {value: "0"}, React.createElement("span", {style: {"padding-left": "5px"}}, "否"))
                                )
                                )
                            ), 
                            React.createElement("div", {id: "customTokenStrategy", style: {'display': 'none'}}, 
                                React.createElement("input", {type: "hidden", name: "uuid", value: this.state.tokenStrategy.uuid}), 
                                React.createElement(FormItem, {label: "最大使用次数"}, 
                                    React.createElement(Input, {name: "maxTimes", value: this.state.tokenStrategy.maxTimes})
                                ), 
                                React.createElement(FormItem, {label: "Token时效"}, 
                                    React.createElement(Input, {name: "expiryDate", value: this.state.tokenStrategy.expiryDate == null ? '7200' : this.state.tokenStrategy.expiryDate, placeholder: "单位秒，默认7200秒"})
                                ), 
                                React.createElement(FormItem, {label: "刷新Token时效"}, 
                                    React.createElement(Input, {name: "refreshExpiryDate", value: this.state.tokenStrategy.refreshExpiryDate == null ? '2592000' : this.state.tokenStrategy.refreshExpiryDate, placeholder: "单位秒，默认2592000秒"})
                                )
                            )
                        )
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

/***/ 24:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Checkbox = UcsmyUI.Checkbox;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Grid = __webpack_require__(0);
var Layer=UcsmyUI.Layer;
var Form = UcsmyUI.Form;

var configFormData = {

	"maxTimes": [
		{type:"maxlength", maxlength:9, msg : "使用次数长度不能超过9"},
		{type: "fn", validator: function(value){
			if(""!=value) {

				var m =/^[1-9]*[1-9][0-9]*$/;

				return m.test(value);
			}else return true;
		}, msg: '使用次数只能为正整数'
		}
	],
	"expiryTime": [
		{type:"maxlength", maxlength:9, msg : "有效时间长度不能超过9"},
		{type: "fn", validator: function(value){
			if(""!=value) {
				var m =/^[1-9]*[1-9][0-9]*$/;

				return m.test(value);
			}else return true;
		}, msg: '有效时间只能为正整数'
		}
	]

};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function() {
        return{
        	gridUrl: '',
			saveUrl: '',
			title: '',
			sign: '',
			data: {},
			resGroupDisabled: true,
			resGroupArray: [],
            resGroupObj: [],
            clientArray: []
        }
    },
    componentDidMount: function() {
        var me = this;
        $.post("resourceGroup/queryAllResourceGroup", {}, function(data) {        	
            if(data.retcode != 0) {
                return;
            }            
//            var map = {};
            var array = [];
            data.data.map(function(returnData) {            	
//            	map[returnData.resGroupUuid] = returnData.groupName;
            	array.push({
                    option: returnData.groupName,
                    value: returnData.resGroupUuid
                });
            });            
            me.setState({
//            	resGroup: map,
            	resGroupArray: array,
            	resGroupObj: data.data
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
        
        $.post("client_info/query", {pageNum:1,pageSize:99999}, function(data) {        	
            if(data.retcode != 0) {
                return;
            }            
            
            var array = [];
            data.data.resultList.map(function(returnData) {
            	array.push({
                    option: returnData.clientName,
                    value: returnData.clientId
                });
            });            
            me.setState({
            	clientArray: array
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
    init: function(sign, data, successFn) {// sign: bind or unbind
    	var title, gridUrl, saveUrl;
    	if(sign == "bind") {
    		title = "授权资源组";
    		gridUrl = "clientResgrRel/queryUnbindResgrRel?filteringClientId=" + data.clientId;
    		saveUrl = "clientResgrRel/addResgrRel";
    	} else {
    		title = "取消授权资源组";
    		gridUrl = "clientResgrRel/queryResgrRel?clientId=" + data.clientId;
    		saveUrl = "clientResgrRel/deleteResgrRel";
    	}
    	this.setState({
    		gridUrl: gridUrl,
    		saveUrl: saveUrl,
    		title: title,
    		data: data,
    		sign: sign
    	},()=>{
    		this.refs.grid.load();
    	});
    },
//	componentDidUpdate: function() {
//		this.refs.grid.load();
//	},
	_query: function(event) {		
    	this.refs.grid.load({
    		resGroupUuid: this.refs.resGroupUuid.getValue(),
    		searchClientId: this.refs.clientId.getValue()
    	});
    },
    _changeClient: function(event) {    	
    	var array = [];
    	
    	$.each(this.state.resGroupObj,function(){    		
    		if(event == "" || this.clientId == event){
    			array.push({
    				option: this.groupName,
                    value: this.resGroupUuid
    			});
    		}	
    	});
    	this.setState({
    		resGroupArray: array,
    		resGroupDisabled: array.length == 0 ? true : false
    	});
    },
    _return: function(event) {
		UcsmyIndex.closeChildrenPage();
	},
	_onClick: function(event) {
		var me = this;
		var ids = [];
		var obj = document.getElementsByName("resGroupUuid");
		for(var i = 0; i < obj.length; i++) {
			if(obj[i].checked)
				ids.push(obj[i].value);
		}
		if (ids.length == 0){
			UcsmyIndex.alert("异常消息", "请选择要操作的资源组");
			return;
		}


		if(me.state.sign == "bind") {
			this.refs.layer4.layerOpen();
		}else
		{
			me._bindResour('unBindResour');
		}

	},
	_close: function()
	{
		this.refs.layer4.layerClose();
	},
	_validate: function (callback) {
		var status = false;
		var validateField = [
			"maxTimes", "expiryTime"
		];
		var fn = function (result) {
			if(result) {
				callback();
			}
		};

		this.refs.saveForm.validate(fn, validateField);

		return status;
	},
	_bindResour:function(btnId)
	{

         var me = this;
		var ids = [];
		var obj = document.getElementsByName("resGroupUuid");
		for(var i = 0; i < obj.length; i++) {
			if(obj[i].checked)
				ids.push(obj[i].value);
		}


		this._validate(function(){
			_addButtonDisabled(btnId);
			var maxTimes = me.refs.maxTimes.state.value;
			var expiryTime = me.refs.expiryTime.state.value;
			var d;
			if(me.state.sign == "bind") {
				d = {"clientId": me.state.data.clientId, "resGroupUuid": ids.join(",")};
				if(maxTimes!=null && maxTimes!='')
					d["maxTimes"]=maxTimes;
				if(expiryTime!=null && expiryTime!='')
					d["expiryTime"]=expiryTime;
			} else {
				d = {"uuid": ids.join(",")};
			}

			$.post(me.state.saveUrl, d, function(data){
				_removeButtonDisabled(btnId);
				if(data.retcode == "0"){
					UcsmyIndex.alert("成功", data.retmsg);
					me._return();
				} else{
					UcsmyIndex.alert("失败", data.retmsg);
				}
				me._close();
			}, "json").error(function(xhr, errorText, errorType){
				_removeButtonDisabled(btnId);
				UcsmyIndex.alert("失败", "网络异常");
				me._close();
			});
		});


{/*		UcsmyIndex.confirm("确定", "是否确定操作选中的资源组？", function(){
			 $.post(me.state.saveUrl, d, function(data){
			 if(data.retcode == "0"){
			      UcsmyIndex.alert("成功", data.retmsg);
			      me._return();
			 } else{
			     UcsmyIndex.alert("失败", data.retmsg);
			 }
			 }, "json").error(function(xhr, errorText, errorType){
			     UcsmyIndex.alert("失败", "网络异常");
			 });
		 });*/}
	},
	render: function() {
		return (					
			React.createElement("div", null, 
				React.createElement("div", {className: "panel-content"}, 
			React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
               React.createElement(Layer, {ref: "layer4", title: "设置属性", width: "450"}, 
                  React.createElement("div", {className: "ucs-form-group"}, 
			        React.createElement(FormItem, {label: "最大调用次数"}, React.createElement(Input, {name: "maxTimes", ref: "maxTimes", placeholder: "默认为空"}), React.createElement("i", {className: "unit"}, "次"))
			     ), 
			    React.createElement("div", {className: "ucs-form-group"}, 
			       React.createElement(FormItem, {label: "有效时间"}, React.createElement(Input, {name: "expiryTime", ref: "expiryTime", placeholder: "默认36000"}), React.createElement("i", {className: "unit"}, "秒"))
			    ), 
				React.createElement("div", {className: "ucs-layer-footer"}, 
			         React.createElement(Button, {id: "bindResour", buttonType: "bidnow", onClick: this._bindResour.bind(this,'bindResour')}, "确定"), 
		             React.createElement(Button, {buttonType: "bidnow", onClick: this._close}, "取消")
			    )
			   )
             )
					), 
	            React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title"}, "查询条件"), 
	                React.createElement("div", {className: "panel-content"}, 
		                React.createElement(FormItem, {label: "应用简称"}, 
		                	React.createElement(SelectDropDown, {ref: "clientId", defaultText: "全部", defaultValue: "", option: this.state.clientArray, onChange: this._changeClient, showNum: "10"})
		                ), 
	                    React.createElement(FormItem, {label: "资源组"}, 
	                    	React.createElement(SelectDropDown, {ref: "resGroupUuid", defaultText: "全部", defaultValue: "", option: this.state.resGroupArray, disabled: this.state.resGroupDisabled, showNum: "10"})
	                    )
	                )
	            ), 
	            
	            React.createElement("div", {className: "panel"}, 
		            React.createElement("div", {className: "panel-content"}, 
			            React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", null, this.state.sign == "bind" ? "被授权应用ID：" : "被取消授权应用ID："), 
							React.createElement("span", null, this.state.data.clientId)
						), 
			            React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", null, this.state.sign == "bind" ? "被授权应用简称：" : "被取消授权应用简称："), 
							React.createElement("span", null, this.state.data.clientName)
						)
		            )
	            ), 
	            
	            React.createElement("div", {className: "btn-panel"}, 
	            React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(Button, {id: "unBindResour", buttonType: "bidnow", onClick: this._onClick}, this.state.title), 
	                React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {
		            	url: this.state.gridUrl, ref: "grid", 
						isTextOverflowHidden: true, 
		                columns: [ {
							 name:'clientId',
							 header: "",
							 content: function(column) {
								 return (
									 React.createElement(Checkbox, {value: this.state.sign == "bind" ? column.resGroupUuid : column.uuid, name: "resGroupUuid", eventId: this.state.eventId})
								 );
							 }.bind(this)
						}, {
      						name: 'clientName', header: '应用简称',width: 300
      					},{
      						name: 'groupName', header: '资源组',width: 300
      					}, {
      						name: 'descRibe', header: '资源组描述',width: 300
      					}
		               ]}
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

/***/ 4:
/***/ (function(module, exports, __webpack_require__) {

var Form = __webpack_require__(2);

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Tooltip=UcsmyUI.Tooltip;
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
				React.createElement("div", {className: "panel panel-custom"}, 
					React.createElement("div", {className: "panel-title fc-red"}, "应用信息"), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用简称："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.clientName}, 
								this.state.client.clientName
							)
						)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用ID："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.clientId}, 
								this.state.client.clientId
							)
						)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用密钥："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.clientSecret}, 
								this.state.client.clientSecret
							)
						)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "授权类型："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.grantType}, 
								this.state.client.grantType
							)
					  )
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用组："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.ucasClientGroup?this.state.client.ucasClientGroup.groupName:''}, 
								this.state.client.ucasClientGroup?this.state.client.ucasClientGroup.groupName:''
							)
						)
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "重定向URL："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.clientUrl}, 
								this.state.client.clientUrl
							)
						)
					), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用状态："), 
						React.createElement("span", null, this.state.client.status == '0' ? '可用' : '停用')
					), 
					React.createElement("div", {className: "ucs-form-group"}, 
						React.createElement("span", {className: "label"}, "应用描述："), 
						React.createElement("div", {className: "custom-tooltip"}, 
							React.createElement(Tooltip, {title: this.state.client.descRibe}, 
								this.state.client.descRibe
							)
						)
					)
				), 
				tokenStrategy, 
				returnBtn
			)
	    );
	 }
});

/***/ }),

/***/ 56:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var ClientInfoForm = __webpack_require__(23);
var ClientResgrRelForm = __webpack_require__(24);
var ViewClientInfo = __webpack_require__(4);
var AddResGroup = __webpack_require__(22);
var PermissionLink = __webpack_require__(1);
var PermissionButton = __webpack_require__(3);
var SelectDropDown = UcsmyUI.SelectDropDown;
myPanel = React.createClass({displayName: "myPanel",
	getDefaultProps: function(){
		return {
			STATUS_OPTION: [
				{option: '可用', value: '0'},
				{option: '停用', value: '1'}
			]
		}
	},
	getInitialState:function () {
        return{
			GRANT_TYPE_OPTION: []
        }
    },
	componentDidMount: function() {
		var me = this;
		//加载到所有授权类型
		$.post("client_info/queryAllGrantType", {}, function(data) {
			if(data.retcode != 0) {
				return;
			}
			//console.log("加载到所有授权类型");
			//console.log(data.data);
			var jsonArr = [];
			data.data.map(function(data,index){
				jsonArr.push({option : data, value: data});
			});
			//页面加载授权类型对应的CheckBox组件
			me.setState({
				GRANT_TYPE_OPTION: jsonArr
			});
		}, "json").error(function(xhr, errorText, errorType){
		});
	},
    _query: function() {
		this.refs.grid.load({
			clientName: this.refs.clientName.getValue(),
			clientId: this.refs.clientId.getValue(),
			clientGroupName: this.refs.clientGroupName.getValue(),
			status: this.refs.status.getValue(),
			grantType: this.refs.grantType.getValue()
		});
    },
    _add: function() {
    	var me = this;
    	UcsmyIndex.openChildrenPage(ClientInfoForm, function(refPanel) {
    		refPanel.init('新增应用', 'client_info/add', {}, function(){
    			me.refs.grid.load();
    		});
    	});
    },
    _update: function(column) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(ClientInfoForm, function(refPanel) {
    		refPanel.init('修改应用', 'client_info/update', column, function(){
    			me.refs.grid.load();
    		}, true);
    	});
    },
    _bindResgr: function(column) {
    	UcsmyIndex.openChildrenPage(ClientResgrRelForm, function(refPanel) {
    		refPanel.init("bind", column);
    	});
    },
    _unbindResgr: function(column) {
    	UcsmyIndex.openChildrenPage(ClientResgrRelForm, function(refPanel) {
    		refPanel.init("unbind", column);
    	});
    },
    _delete: function(column) {
    	var me = this;
		UcsmyIndex.confirm("确定", "你真的要删除该应用数据吗？", function() {
			$.post("client_info/delete", {clientId: column.clientId}, function(data) {
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
	_view: function(column) {
		UcsmyIndex.openChildrenPage(ViewClientInfo, function(refPanel) {
			refPanel.init(column);
		});
	},

    _addResgr: function (column) {
        UcsmyIndex.openChildrenPage(AddResGroup, function(refPanel) {
            refPanel.init(column, "resourceGroup/queryResourceGroupByClient");
        });
    },
	formatDate: function(date,fmt) {
		var o = {
			"M+": date.getMonth() + 1, //月份
			"d+": date.getDate(), //日
			"h+": date.getHours(), //小时
			"m+": date.getMinutes(), //分
			"s+": date.getSeconds(), //秒
			"q+": Math.floor((date.getMonth() + 3) / 3), //季度
			"S": date.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	},
	render: function() {
		var me = this;
		return (
			React.createElement("div", null, 
				React.createElement("h2", {className: "content-title"}, "应用管理"), 
	            React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title"}, "查询条件"), 
					React.createElement("div", {className: "panel-content"}, 
						React.createElement(FormItem, {label: "应用简称"}, React.createElement(Input, {ref: "clientName"})), 
						React.createElement(FormItem, {label: "应用ID"}, React.createElement(Input, {ref: "clientId"})), 
						React.createElement(FormItem, {label: "应用组名称"}, React.createElement(Input, {ref: "clientGroupName"})), 
						React.createElement(FormItem, {label: "状态", className: "col-xs-5"}, 
							React.createElement(SelectDropDown, {
								ref: "status", 
								option: me.props.STATUS_OPTION}
							)
						), 
						React.createElement(FormItem, {label: "授权类型", className: "col-xs-5"}, 
							React.createElement(SelectDropDown, {
								ref: "grantType", 
								defaultText: "请选择", defaultValue: "", 
								option: me.state.GRANT_TYPE_OPTION, searchPlaceholder: "请选择"}
							)
						)
					)
	            ), 
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(PermissionButton, {permissionName: "client_info_add", buttonType: "bidnow", onClick: this._add}, "新增")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {
		            	retDataProperty: "data.resultList", 
	            		retTotalProperty: "data.totalCount", 
	            		retCurrentProperty: "data.pageNo", 
		            	url: "client_info/query", ref: "grid", 
						isTextOverflowHidden: true, 
		                columns: [ 
         				    {
          						name: 'clientName', header: '应用简称',
          						width: 100
          					}, /*{
          						name: 'descRibe', header: '应用描述',
          						width: 200
          					}, {
          						name: 'clientId', header: '应用ID',
          						width: 260
          					}, {
          						name: 'clientSecret', header: '应用密钥',
          						width: 150
          					},*/ {
          						name: 'grantType', header: '授权类型',
          						width: 200
          					}, {
          						name: 'ucasClientGroup', header: '应用组名称', width: 100, content:function(column){
         				    		 return (React.createElement("span", null, 
        				    		 	column.ucasClientGroup == null ? '' : column.ucasClientGroup.groupName
        				    		 ))
        						}
          					}, {
          						name: 'status', header: '状态', width: 50, content:function(column){
          				    		 return (React.createElement("span", null, 
          				    		 	column.status == '0' ? '可用' : '停用'
          				    		 ))
          						}
          					}, {
          						name: 'modifyDate', header: '最近修改时间', width: 160, content:function(column){
          				    		 return (React.createElement("span", null, 
          				    		 	me.formatDate(new Date(column.modifyDate),"yyyy-MM-dd hh:mm:ss")
          				    		 ))
          						}
          					}, {
          						name: 'cz',
          						header: '操作',
          						permissionName: 'client_info_view,client_info_update,client_info_delete,client_info_bindResgr,client_info_unbindResgr',
          						content:function(column){
          							if(column.status == 0){
										return (React.createElement("span", null, 
											React.createElement(PermissionLink, {permissionName: "client_info_view", href: "Javascript:void(0);", onClick: me._view.bind(this, column)}, "查看   "), 
											React.createElement(PermissionLink, {permissionName: "client_info_update", href: "Javascript:void(0);", onClick: me._update.bind(this, column)}, "修改   "), 
											React.createElement(PermissionLink, {permissionName: "client_info_delete", href: "Javascript:void(0);", onClick: me._delete.bind(this, column)}, "删除   "), 
                                            React.createElement(PermissionLink, {permissionName: "client_info_addResgr", href: "Javascript:void(0);", onClick: me._addResgr.bind(this, column)}, "添加资源组   "), 
											React.createElement(PermissionLink, {permissionName: "client_info_bindResgr", href: "Javascript:void(0);", onClick: me._bindResgr.bind(this, column)}, "授权资源组   "), 
											React.createElement(PermissionLink, {permissionName: "client_info_unbindResgr", href: "Javascript:void(0);", onClick: me._unbindResgr.bind(this, column)}, "取消授权资源组")
										))
          							}else{
          								return (React.createElement("span", null, "无"))
          							}
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