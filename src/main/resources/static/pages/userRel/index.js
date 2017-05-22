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
/******/ 	return __webpack_require__(__webpack_require__.s = 72);
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

/***/ 47:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/25.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);
var FormItem = UcsmyUI.Form.FormItem;
var PermissionLink = __webpack_require__(1);
var ResourceLayer = __webpack_require__(73);

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            accUuid: '',
            url: '',
            realName: '',
            callback: function(){}
        }
    },

    init: function (callback, data) {
        var url = 'userRel/clientList';
        var queryData = {
            accUuid : data.accUuid,
            clientName: this.refs.clientName.getValue(),
            openId: this.refs.openId.getValue()
        };
        this.setState({
            accUuid: data.accUuid,
            callback: callback,
            realName: data.realName,
            url: url
        }, () => {
            this.refs.grid.load(queryData);
        });
    },

    _search: function () {
        this.refs.grid.load({
            accUuid: this.state.accUuid,
            clientName: this.refs.clientName.getValue(),
            openId: this.refs.openId.getValue()
        });
    },

    _clientResources: function (item) {
        this.refs.resourceLayer._open('userRel/resList', item);
    },

    _deleteClientRel: function (item) {
        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否确定取消应用授权？", function() {
            $.post(
                'userRel/deleteUserRel',
                {openId: item.openId},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
                        rootThis.refs.clientName.setValue('');
                        rootThis.refs.openId.setValue('');
                        rootThis.refs.grid.load({accUuid : rootThis.state.accUuid, clientName: rootThis.refs.clientName.getValue()});
                    } else {
                        UcsmyIndex.alert("提示", result.retmsg);
                    }
                }
                , "json").error(function () {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },

    _return: function(event) {
        UcsmyIndex.closeChildrenPage();
    },

    render: function () {
        var rootThis = this;
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "账号授权应用列表"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "ucs-form-group"}, 
                        React.createElement("span", {className: "label"}, "用户名称："), 
                        React.createElement("span", null, rootThis.state.realName)
                    ), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "应用名称"}, React.createElement(Input, {ref: "clientName"})), 
                        React.createElement(FormItem, {label: "openId"}, React.createElement(Input, {ref: "openId"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._search}, "查询"), 
                    React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: rootThis.state.url, ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                              {name: 'openId', header: 'openId', width: 270},
                              {name: 'clientName', header: '应用名称', width: 150},
                              {name: 'descRibe', header: '应用描述', width: 150},
                              {
                                  name: 'operator', header: '操作', content: function (item) {
                                  return (
                                      React.createElement("div", null, 
                                          React.createElement(PermissionLink, {permissionName: "user_rel_client", href: "Javascript:void(0);", onClick: rootThis._clientResources.bind(this, item)}, "  查看授权接口  "), 
                                          React.createElement(PermissionLink, {permissionName: "user_rel_remove_client", href: "Javascript:void(0);", onClick: rootThis._deleteClientRel.bind(this, item)}, "  取消应用授权  ")
                                      )
                                  )
                              }
                              }
                          ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                ), 
                React.createElement(ResourceLayer, {ref: "resourceLayer"})
            )
        )
    }
});

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

/***/ 72:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/25.
 */
var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);
var FormItem = UcsmyUI.Form.FormItem;
var UserClient = __webpack_require__(47);
var PermissionLink = __webpack_require__(1);
var SelectDropDown = UcsmyUI.SelectDropDown;
var UserDetailForm = __webpack_require__(5);

var option = [
    {option: '正常', value: '0'},
    {option: '冻结', value: '1'}
];
myPanel = React.createClass({displayName: "myPanel",

    getInitialState:function () {
        return{
            urgentFlag:''
        }
    },

    componentDidMount: function() {
        var me = this;
        $.post("accountGroup/getAll", {}, function(data) {
            if(data.retmsg != 'success') {
                return;
            }
            me.setState({
                urgentFlag: data.data
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },

    _search: function () {
        this.refs.grid.load({
            ucasAccount: this.refs.ucasAccount.getValue(),
            emailAccount: this.refs.emailAccount.getValue(),
            mobileAccount: this.refs.mobileAccount.getValue(),
            realName: this.refs.realName.getValue(),
            status: this.refs.sta.getValue(),
            accgUuid: this.refs.accgUuid.getValue()
        });
    },

    _manageClient: function (column) {
        var me = this;
        UcsmyIndex.openChildrenPage(UserClient, function(refPanel) {
            refPanel.init(
                function () {
                    me._refreshData();
                }, column);
        });
    },

    _refreshData: function () {
        console.log('refresh');
        this.refs.grid.load({
            ucasAccount: this.refs.ucasAccount.getValue()
        });
    },

    _detail:function(column) //基本信息
    {
        var me = this;
        UcsmyIndex.openChildrenPage(UserDetailForm, function(refPanel) {
            refPanel.init('account/upInfo','修改基本信息',  column, function(){
                //me.refs.grid.load();
                me.refs.grid.reload();
            });
        });
    },

    render: function () {
        var rootThis = this;
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "账号授权管理"), 
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
                            React.createElement(SelectDropDown, {ref: "accgUuid", defaultText: "请选择", defaultValue: "", option: rootThis.state.urgentFlag})
                        )
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._search}, "查询")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: "account/queryUsers", ref: "grid", 
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
                              },
                              {
                                  name: 'mobileAccount', header: '手机帐号',width: 120
                              },
                              {
                                  name: 'orgName', header: '组织名称',width: 60
                              },
                              {
                                  name: 'groupName', header: '用户组',width: 100
                              },
                              {
                                  name: 'modifyDate', header: '最近修改时间',width: 150
                              },
                              {
                                  name: 'status', header: '状态',width: 30, content:function(column){
                                      return (React.createElement("span", null, 
        				    		 	column.status == '0' ? '正常' : '冻结'
        				    		 ))
                                  }
                              },
                              {
                                  name: 'accUuid', header: '操作', content: function (item) {
                                  return (
                                      React.createElement("div", null, 
                                          React.createElement(PermissionLink, {permissionName: "account_detail", href: "Javascript:void(0);", onClick: rootThis._detail.bind(this, item)}, "  账号详情  "), 
                                          React.createElement(PermissionLink, {permissionName: "user_rel_client", href: "Javascript:void(0);", onClick: rootThis._manageClient.bind(this, item)}, "  查看授权应用  ")
                                      )
                                  )
                              }
                              }
                          ]}
                    ), 
                    React.createElement("div", {className: "clearfix"})
                )
            )
        )
    }
});

/***/ }),

/***/ 73:
/***/ (function(module, exports, __webpack_require__) {

/**
 * Created by chenqilin on 2017/4/25.
 */
var Grid = __webpack_require__(0);
var Layer = UcsmyUI.Layer;

module.exports = React.createClass({displayName: "module.exports",

    getInitialState: function () {
        return {
            url: '',
            pageSize: 4
        }
    },

    _open: function(url, data) {
        var queryData = {
            openId: data.openId
        };
        this.refs.layer.layerOpen();
        this.setState({
            url: url,
        }, () => {
            this.refs.grid.load(queryData);
        });
    },

    render: function () {
        var rootThis = this;
        return (
            React.createElement("div", null, 
                React.createElement(Layer, {ref: "layer", title: "授权接口", width: "770", height: "550"}, 
                    React.createElement("div", {className: "table-panel"}, 
                        React.createElement(Grid, {url: rootThis.state.url, ref: "grid", 
                              pageSize: rootThis.state.pageSize, 
                              columns: [
                                  {name: 'no', header: '编号', width: 50},
                                  {name: 'resUri', header: '资源URI', width: 200},
                                  {name: 'descRibe', header: '资源描述', width: 150},
                                  {name: 'status', header: '资源状态', width: 50, content: function (item) {
                                  var statusText = '正常';
                                  if (item.status == '1') {
                                      statusText = '失效';
                                  }
                                  return (
                                      React.createElement("span", null, 
                                          statusText
                                      )
                                  )
                                  }}
                              ]}
                        ), 
                        React.createElement("div", {className: "clearfix"})
                    )
                )
            )
        )
    }
});

/***/ })

/******/ });