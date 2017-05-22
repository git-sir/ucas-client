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
/******/ 	return __webpack_require__(__webpack_require__.s = 68);
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

/***/ 44:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;


var scheduleFormData = {

    "taskCode": [
        {type: "required", msg: "任务码不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数名称长度不能超过100"}
    ],
    "taskName": [
        {type: "required", msg: "任务名称不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数值长度不能超过100"}
    ],
    "taskConf": [
        {type: "required", msg: "定时配置不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数描述长度不能超过100"}
    ],
    "taskClass": [
        {type: "required", msg: "执行地址不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数描述长度不能超过100"}
    ],
    "taskServerIp": [
        {type: "required", msg: "指定IP不能为空"},
        {type : "maxlength", maxlength : 100, msg : "参数描述长度不能超过100"}
    ],
    "remark": [
        {type: "required", msg: "备注不能为空"},
        {type : "maxlength", maxlength : 250, msg : "参数描述长度不能超过250"}
    ]
};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function () {
        return {
            url: '',
            title: '',
            callback: function(){},
            schedule: {}
        }
    },
    _validate: function (callback) {
        var status = false;
        var validateField = [
            "taskCode", "taskName", "taskConf", "taskClass", "taskServerIp", "remark"
        ];

        var fn = function (result) {
            if(result) {
                callback();
            }
        }

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

    init: function (url, title, callback, data) {
        var rootThis = this;

        this.setState({
            url: url,
            title: title,
            callback: callback,
            schedule: data.schedule ? data.schedule : {}
        });

    },

    _return: function(event) {
        UcsmyIndex.closeChildrenPage();
    },

    render: function () {
        return (
            React.createElement("div", null, 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: scheduleFormData}, 
                            React.createElement("input", {type: "hidden", name: "id", value: this.state.schedule.id}), 
                            React.createElement(FormItem, {label: "任务码", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.taskCode, name: "taskCode"})), 
                            React.createElement(FormItem, {label: "任务名称", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.taskName, name: "taskName"})), 
                            React.createElement(FormItem, {label: "定时配置", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.taskConf, name: "taskConf"})), 
                            React.createElement(FormItem, {label: "执行地址", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.taskClass, name: "taskClass"})), 
                            React.createElement(FormItem, {label: "指定IP", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.taskServerIp, name: "taskServerIp"})), 
                            React.createElement(FormItem, {label: "备注", className: "col-xs-5"}, React.createElement(Input, {value: this.state.schedule.remark, name: "remark"}))
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

/***/ 68:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Grid = __webpack_require__(0);
var Form = __webpack_require__(44);
var FormItem = UcsmyUI.Form.FormItem;



myPanel = React.createClass({displayName: "myPanel",


    getInitialState: function () {
        return {
            
        }
    },

    _add: function (item) {
        var rootThis = this;
        var id = item.id
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init(
                'schedule/add',
                '添加定时任务',
                function () {
                    rootThis.refs.grid.load();
                },
                {
                    data: {}
                }
            );
        });
    },

    _edit: function (item) {
        var rootThis = this;
        UcsmyIndex.openChildrenPage(Form, function (ref) {
            ref.init(
                'schedule/update',
                '修改定时任务',
                function () {
                    rootThis.refs.grid.load();
                },
                {
                    schedule: item
                }
            );
        });
    },

    _delete: function (item) {

        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否确定删除该定时任务？", function() {
            $.post(
                'schedule/delete',
                {id: item.id},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
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

    _search: function () {
        this.refs.grid.load({
            taskName: this.refs.taskName.getValue()
        });
    },

    _start: function (item) {

        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否启用定时任务？", function() {
            $.post(
                'schedule/start',
                {id: item.id},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
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

    _stop: function (item) {

        var rootThis = this;

        UcsmyIndex.confirm("确定", "是否停用定时任务？", function() {
            $.post(
                'schedule/stop',
                {id: item.id},
                function (result) {
                    if(result && result.retcode == "0") {
                        UcsmyIndex.alert("提示", result.retmsg);
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
    
    render: function () {
        var rootThis = this;

        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "定时管理"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-title"}, "查询条件"), 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement(FormItem, {label: "任务名称"}, React.createElement(Input, {ref: "taskName"}))
                    )
                ), 
                React.createElement("div", {className: "btn-panel"}, 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._search}, "查询"), 
                    React.createElement(Button, {buttonType: "bidnow", onClick: this._add}, "添加")
                ), 

                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(Grid, {url: "schedule/list", ref: "grid", 
                          isTextOverflowHidden: true, 
                          columns: [
                              {name: 'taskCode', header: '任务码', width: 80},
                              {name: 'taskName', header: '任务名称', width: 80},
                              {name: 'taskConf', header: '定时配置', width: 100},
                              {name: 'taskClass', header: '执行地址', width: 200},
                              {name: 'taskServerIp', header: '指定IP', width: 100},
                              {name: 'status', header: '状态', width: 100, content: function (item) {
                                  var statusText = '已停用';
                                  if(item.status == 1) {
                                      statusText = '已启用';
                                  }
                                  return (
                                      React.createElement("span", null, 
                                          statusText
                                      )
                                  )
                              }},
                              {name: 'remark', header: '备注', width: 100},
                              {name: 'id', header: '操作', width: 200, content: function (item) {
                                  return (
                                      React.createElement("span", null, 
                                        React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._edit.bind(this, item)}, "修改"), 
                                          "    ", 
                                          React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._delete.bind(this, item)}, "删除"), 
                                          "    ", 
                                          React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._start.bind(this, item)}, "启用"), 
                                          "    ", 
                                          React.createElement("a", {href: "Javascript:void(0);", onClick: rootThis._stop.bind(this, item)}, "停用")
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