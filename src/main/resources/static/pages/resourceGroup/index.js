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
/******/ 	return __webpack_require__(__webpack_require__.s = 66);
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

/***/ 38:
/***/ (function(module, exports) {

/**
 * Created by chenqilin on 2017/5/4.
 */
var Button = UcsmyUI.Button;

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            group: {}
        }
    },

    init: function(data) {
        this.setState({
            group: data
        });
    },

    _return: function() {
        UcsmyIndex.closeChildrenPage();
    },

    render: function(){
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, "资源组信息"), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "应用简称："), 
                        React.createElement("span", null, this.state.group.clientName)
                    ), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "所属应用id："), 
                        React.createElement("span", null, this.state.group.clientId)
                    ), 
                    React.createElement("br", null), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "资源组名称："), 
                        React.createElement("span", null, this.state.group.groupName)
                    ), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "资源组UUID："), 
                        React.createElement("span", null, this.state.group.resGroupUuid)
                    ), 
                    React.createElement("br", null), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "资源组描述："), 
                        React.createElement("span", null, this.state.group.descRibe)
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

/***/ 39:
/***/ (function(module, exports) {

/**
 * Created by chenqilin on 2017/5/2.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = UcsmyUI.Form.FormItem;


var formData = {
    "groupName": [
        {type: "required", msg: "资源组名称不能为空"},
        {type: "maxlength", maxlength: 50, msg: "资源组名称长度不能超过50"}
    ],
    "descRibe": [
        {type: "required", msg: "资源组描述不能为空"},
        {type: "maxlength", maxlength: 200, msg: "资源组描述长度不能超过200"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            data: {},
            url: "resourceGroup/update"
        }
    },

    init: function (item, callback) {
        this.setState({
            data: item.resGroup ? item.resGroup : {},
            callback: callback
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
                        UcsmyIndex.alert("提示", result.retmsg);
                        UcsmyIndex.closeChildrenPage();
                        rootThis.state.callback();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }, "json").error(function(xhr, errorText, errorType){
                _removeButtonDisabled('save');
                UcsmyIndex.alert("失败", "网络异常");
            });
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
                    React.createElement("div", {className: "panel-title fc-red"}, "修改资源组"), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "应用ID："), 
                        React.createElement("span", null, rootThis.state.data.clientId)
                    ), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: formData}, 
                            React.createElement("input", {type: "hidden", name: "resGroupUuid", value: rootThis.state.data.resGroupUuid}), 
                            React.createElement("input", {type: "hidden", name: "clientId", value: rootThis.state.data.clientId}), 
                            React.createElement(FormItem, {label: "资源组名称", className: "col-xs-5"}, React.createElement(Input, {name: "groupName", value: rootThis.state.data.groupName})), 
                            React.createElement(FormItem, {label: "资源组描述", className: "col-xs-5"}, React.createElement(Input, {name: "descRibe", value: rootThis.state.data.descRibe}))
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._saveOrUpdate}, "保存"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
                )
            )
        )
    }
});

/***/ }),

/***/ 40:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/5/3.
 */
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            gridUrl: null,
            resGroupUuid: '',
            groupName: ''
        }
    },

    init: function (data) {
        var query = {
            resGroupUuid: data.resGroupUuid
        };
        this.setState({
            gridUrl: 'clientResource/queryResource',
            resGroupUuid: data.resGroupUuid,
            groupName: data.groupName,
        }, () => {
            this.refs.grid.load(query);
        });
    },

    _query: function() {
        var rootThis = this;
        this.refs.grid.load({
            resGroupUuid: rootThis.state.resGroupUuid
        });
    },

    _return: function (event) {
        UcsmyIndex.closeChildrenPage();
    },

    render: function () {
        var me = this;
        return (
            React.createElement("div", null, 
                React.createElement("div", null, 
                    React.createElement("h2", {className: "content-title"}, "查看资源"), 
                    React.createElement("div", {className: "panel"}, 
                        React.createElement("div", {className: "panel-title"}, "查询条件"), 
                        React.createElement("div", {className: "ucs-form-group"}, 
                            React.createElement("span", {className: "label"}, "资源组ID："), 
                            React.createElement("span", null, me.state.resGroupUuid)
                        ), 
                        React.createElement("div", {className: "ucs-form-group"}, 
                            React.createElement("span", {className: "label"}, "资源组名称："), 
                            React.createElement("span", null, me.state.groupName)
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._return}, "返回")
                ), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {
                        url: this.state.gridUrl, ref: "grid", 
                        isTextOverflowHidden: true, 
                        columns: [
                            {name: 'resUri', header: '资源URI', width: 250},
                            {name: 'descRibe', header: '资源描述', width: 300},
                            {name: 'status', header: '状态', width: 50, content: function (item) {
                                var statusText = '正常';
                                if (item.status == '1') {
                                    statusText = '失效';
                                }
                                return (
                                    React.createElement("span", null, 
                                          statusText
                                      )
                                )
                            }},
                            {name: 'modifyDate', header: '修改时间', content: function (item) {
                                var date = new Date(item.modifyDate).format("yyyy/MM/dd hh:mm:ss");
                                return (
                                    React.createElement("div", {className: "alignLeftWrap"}, 
                                        date
                                    )
                                )
                            }}
                        ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        );
    }
});

/***/ }),

/***/ 66:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/28.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);
var FormItem = UcsmyUI.Form.FormItem;
var Form = __webpack_require__(39);
var PermissionLink = __webpack_require__(1);
var GroupResource = __webpack_require__(40);
var GroupDetail = __webpack_require__(38);

myPanel = React.createClass({displayName: "myPanel",

    _search: function () {
        this.refs.grid.load({
            groupName: this.refs.groupName.getValue(),
            clientId: this.refs.clientId.getValue(),
            clientName: this.refs.clientName.getValue()
        });
    },

    _edit: function (item) {
        var rootThis = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init(
                {resGroup: item},
                function () {
                    rootThis.refs.clientId.setValue('');
                    rootThis.refs.groupName.setValue('');
                    rootThis.refs.clientName.setValue('');
                    rootThis.refs.grid.load();
                }
            );
        });
    },

    _delete: function (item) {
        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否确定删除该资源组？", function() {
            $.post(
                'resourceGroup/delete',
                {resGroupUuid: item.resGroupUuid},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        rootThis.refs.clientId.setValue('');
                        rootThis.refs.groupName.setValue('');
                        rootThis.refs.clientName.setValue('');
                        rootThis.refs.grid.load();
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
                , "json").error(function () {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },

    _queryResource: function (column) {
        UcsmyIndex.openChildrenPage(GroupResource, function(refPanel) {
            refPanel.init(column);
        });
    },

    _queryGroupDetail: function(column) {
        UcsmyIndex.openChildrenPage(GroupDetail, function(refPanel) {
            refPanel.init(column);
        });
    },

    render: function () {
        var rootThis = this;
        return(
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "资源组管理"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "应用id"}, React.createElement(Input, {ref: "clientId"})), 
                        React.createElement(FormItem, {label: "应用简称"}, React.createElement(Input, {ref: "clientName"})), 
                        React.createElement(FormItem, {label: "资源组名称"}, React.createElement(Input, {ref: "groupName"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: rootThis._search}, "查询")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: "resourceGroup/queryResourceGroupByClient", ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                              {name: 'clientId', header: '所属应用id', width: 270},
                              {name: 'clientName', header: '应用简称', width: 150},
                              {name: 'groupName', header: '资源组名称', width: 150},
                              {name: 'descRibe', header: '资源组描述', width: 200},
                              {name: 'resGroupUuid', header: '操作', content: function (item) {
                                  return (
                                      React.createElement("div", null, 
                                          React.createElement(PermissionLink, {permissionName: "resource_group_query", href: "Javascript:void(0);", onClick: rootThis._queryGroupDetail.bind(this, item)}, "  查看详情  "), 
                                          React.createElement(PermissionLink, {permissionName: "resource_group_edit", href: "Javascript:void(0);", onClick: rootThis._edit.bind(this, item)}, "  修改  "), 
                                          React.createElement(PermissionLink, {permissionName: "resource_group_delete", href: "Javascript:void(0);", onClick: rootThis._delete.bind(this, item)}, "  删除  "), 
                                          React.createElement(PermissionLink, {permissionName: "resource_group_query", href: "Javascript:void(0);", onClick: rootThis._queryResource.bind(this, item)}, "  查看资源  ")
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