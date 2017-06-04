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
/******/ 	return __webpack_require__(__webpack_require__.s = 63);
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

/***/ 36:
/***/ (function(module, exports, __webpack_require__) {

var Button = UcsmyUI.Button;
var TreeTable = UcsmyUI.TreeTable;
var Checkbox = UcsmyUI.Checkbox;
var Form = __webpack_require__(65);
var Bind = __webpack_require__(64);

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            treeData: {}
        }
    },

    _edit: function (item) {
        var rootThis = this;
        $.post(
            'organization/getOrganization',
            {id: item.id},
            function (result) {
                if(result && result.retcode == "0") {
                    UcsmyIndex.openChildrenPage(Form, function (ref) {
                        ref.init(
                            'organization/editOrganization',
                            '修改组织',
                            function () {
                                rootThis.load();
                            },
                            {
                                organization: result.data
                            }
                        );
                    });
                } else {
                    if(result) {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
            }
        , "json").error(function () {
            UcsmyIndex.alert("失败", "网络异常");
        });
    },


    _add: function (item) {
        var rootThis = this;
        var id = item.id
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init(
                'organization/addOrganization',
                '添加组织',
                function () {
                    rootThis.load();
                },
                {
                    organization: {parentId: id}
                }
            );
        });
    },

    _delete: function (item) {
        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否确定删除该组织？", function() {
            $.post(
                'organization/deleteOrganization',
                {id: item.id},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        rootThis.load();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
                , "json").error(function () {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },

    componentDidMount: function () {
        this.load();
    },

    load: function () {
        var rootThis = this;
        $.post(
            'organization/queryOrganization',
            {},
            function (result) {
                if(result && result.retcode == "0") {
                    rootThis.setState({
                        treeData: result.data.length == 1 ? result.data[0] : result.data
                    });
                } else {
                    if (result.retmsg) {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
            }
        , "json").error(function () {
            UcsmyIndex.alert("提示", "网络异常");
        });
    },

    _bind: function (item) {
        var id = item.id;
        var name = item.name;
        UcsmyIndex.openChildrenPage(Bind, function (ref) {
            ref.init(
                '用户绑定',
                'organization/bindOrganization',
                'organization/queryUserWithoutOrganization?id=' + id,
                {
                    id: id,
                    organizationName: name
                }
            );
        });
    },

    _unbind: function (item) {
        var id = item.id;
        var name = item.name;
        UcsmyIndex.openChildrenPage(Bind, function (ref) {
            ref.init(
                '用户解绑',
                'organization/unbindOrganization',
                'organization/queryUserWithOrganization?id=' + id,
                {
                    id: id,
                    organizationName: name
                }
            );
        });

    },

    render: function () {
        var rootThis = this;
        return (
            React.createElement("div", null, 
                React.createElement(TreeTable, {data: this.state.treeData, id: "treetableid", 
                    column: [
                    {name: '名称', field: 'name', headerClass: 'header-blank', width: 200} ,
                    {name: '描述', field: 'description', headerClass: 'header-blank'},
                    {name: '优先级', field: 'priority', headerClass: 'header-blank',width: 100},
                    {name: '操作', headerClass: 'header-blank',width: 300, setContent:function (item) {
                        return (
                                React.createElement("span", null, 
                                    React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._edit.bind(this,item)}, "修改"), 
                                    "    ", 
                                    React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._add.bind(this,item)}, "新增"), 
                                    "    ", 
                                    React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._delete.bind(this,item)}, "删除"), 
                                    "    ", 
                                    React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._bind.bind(this,item)}, "用户绑定"), 
                                    "    ", 
                                    React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._unbind.bind(this,item)}, "用户解绑")
                                )
                            )
                    }}
                ]})
            )
        )
    }

});

/***/ }),

/***/ 63:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var OrganizationTreeTable = __webpack_require__(36)

myPanel = React.createClass({displayName: "myPanel",
    getInitialState: function () {
        return {

        }
    },

    componentDidMount: function () {

    },

    render: function () {

        return (

            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "组织管理"), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(OrganizationTreeTable, null)
                )
            )
        )
    }

});

/***/ }),

/***/ 64:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Checkbox = UcsmyUI.Checkbox;
var Grid = __webpack_require__(0);

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            title: '',
            operationUrl: '',
            userOrganizationUrl: '',
            id: '',
            organizationName: ''
        }

    },

    init: function (title, operationUrl, userOrganizationUrl, data) {
        this.setState({
            title: title,
            operationUrl: operationUrl,
            userOrganizationUrl: userOrganizationUrl,
            id: data.id,
            organizationName: data.organizationName
        });

    },

    _return: function () {
        UcsmyIndex.closeChildrenPage();
    },

    _bind: function () {
        var me = this;
        var idArray = [];
        var obj = document.getElementsByName("userId");
        for(var key = 0; key < obj.length; key++) {
            if(obj[key].checked)
                idArray.push(obj[key].value);
        }
        var ids = idArray.join(',');

        var rootThis = this;
        _addButtonDisabled('save');
        $.post(
            me.state.operationUrl,
            {
                userId: ids,
                organizationId: this.state.id
            },
            function (result) {
                _removeButtonDisabled('save');
                UcsmyIndex.alert("提示", result.retmsg);
                if(result && result.retcode == '0') {
                    me.refs.account.setValue('');
                    me.refs.name.setValue('');
                    rootThis.refs.grid.load();
                }
            }
            , "json").error(function(){
            _removeButtonDisabled('save');
            UcsmyIndex.alert("失败", "网络异常");
        });


    },

    _checkAll: function () {
        UEventHub.emit('checkAllEvent', this.refs.checkAllBox.getChecked());
    },

    _query: function() {
        this.refs.grid.load({
            'account': this.refs.account.getValue(),
            'name': this.refs.name.getValue()
        });
    },

    callback: function () {
        this.refs.checkAllBox.setChecked(false);

    },

    render: function () {
        if(this.state.userOrganizationUrl == '') {
            return (
                React.createElement("div", null)
            )
        }
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, this.state.title, " - ", this.state.organizationName), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "用户账号"}, 
                            React.createElement(Input, {ref: "account"})
                        ), 
                        React.createElement(FormItem, {label: "用户姓名"}, 
                            React.createElement(Input, {ref: "name"})
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
                    React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._bind}, this.state.title), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
                ), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement("form", {id: "organizationForm"}, 
                        React.createElement(Grid, {
                            url: this.state.userOrganizationUrl, ref: "grid", 
                            columns: [
                                {
                                    header: React.createElement(Checkbox, {ref: "checkAllBox", onChange: this._checkAll}),
                                    content:function(column){
                                    return (
                                        React.createElement(Checkbox, {value: column.userId, name: "userId"})
                                    );
                                    }.bind(this)
                                },
                                {
                                    name: 'account', header: '用户账号'
                                },
                                {
                                    name: 'name', header: '姓名'
                                }
                            ], 
                            callback: this.callback}
                        )
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        )
    }
});

/***/ }),

/***/ 65:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var organizationFormData = {

    "name": [
        {type: "required", msg: "组织名称不能为空"},
        {type : "maxlength", maxlength : 64, msg : "组织名称长度不能超过64"}
    ],
    "description": [
        {type: "required", msg: "组织描述不能为空"},
        {type : "maxlength", maxlength : 256, msg : "组织描述长度不能超过256"}
    ],
    "priority": [
        {type: "required", msg: "组织优先级不能为空"},
        {type: "fn", validator: function(value){
            var m = /^\d+$/;
            return m.test(value);
        }, msg: '优先级必须为大于等于0的整数'
        },
        {type : "maxlength", maxlength : 9, msg : "优先级长度不能大于9"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            organization: {},
            parentId: ''
        }
    },
    _validate: function (callback) {
        var validateField = [
            "name", "description", "priority"
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

        var rootThis = this;
        this._validate(function(){
            _addButtonDisabled('save');
            $.post(
                rootThis.state.url,
                $('#form').serialize(),
                function (result) {
                    _removeButtonDisabled('save');
                    if (result && result.retcode && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        UcsmyIndex.closeChildrenPage();
                        rootThis.state.callback();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }, "json").error(function(){
                _removeButtonDisabled('save');
                UcsmyIndex.alert("失败", "网络异常");
            });

        });
    },

    init: function (url, title, callback, data) {

        this.setState({
            url: url,
            title: title,
            callback: callback,
            organization: data.organization ? data.organization : {},
            parentId: data.parentId
        });

    },

    _return: function() {
        UcsmyIndex.closeChildrenPage();
    },

    render: function () {
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: organizationFormData}, 
                            React.createElement("input", {type: "hidden", name: "id", value: this.state.organization.id}), 
                            React.createElement("input", {type: "hidden", name: "parentId", value: this.state.organization.parentId}), 
                            React.createElement(FormItem, {label: "名称", className: "col-xs-5"}, React.createElement(Input, {value: this.state.organization.name, name: "name"})), 
                            React.createElement(FormItem, {label: "描述", className: "col-xs-5"}, React.createElement(Input, {value: this.state.organization.description, name: "description"})), 
                            React.createElement(FormItem, {label: "优先级", className: "col-xs-5"}, React.createElement(Input, {value: this.state.organization.priority, name: "priority"}))
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

/***/ })

/******/ });