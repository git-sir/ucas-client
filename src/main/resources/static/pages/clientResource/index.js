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
/******/ 	return __webpack_require__(__webpack_require__.s = 54);
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

/***/ 20:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;

var resourceFormData = {
    "resGroupUuid": [
        {type: "required", msg: "资源组不能为空"}
    ],
    "descRibe": [
        {type: "required", msg: "资源描述不能为空"}
    ],
    "resUri": [
        {type: "required", msg: "资源URI不能为空"}
    ]
};

var commonDropDown = [
	{value: "0",option: "否"},
	{value: "1",option: "是"}
];

//var statusDropDown = [
//	{value: "0",option: "正常"},
//	{value: "1",option: "停用"},
//	{value: "2",option: "删除"}
//];

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function(){
		return {
			url: '',
			title: 'title',
			successFn: function() {},
			resGroupDisabled: true,
            clientResource: {},
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
            var array = [];
            data.data.map(function(returnData) {
                array.push({
                    option: returnData.groupName,
                    value: returnData.resGroupUuid
                });
            });
            me.setState({
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
	_validate: function (callback) {
        var status = false;
        var validateField = [
            "resGroupUuid",
            "descRibe",
            "resUri"
        ];
		var fn = function (result) {
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
            clientResource: data
		});
		//this.refs.saveForm.setValues(data);
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
	_save: function(event) {

		var me = this;
		this._validate(function(){
            _addButtonDisabled('save');
			$.post(me.state.url,
				$('#form').serialize(),
				function(data) {
				_removeButtonDisabled('save');
				if(data.retcode == "0") {
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
	render: function() {
		return (
			React.createElement("div", null, 
				React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
	                React.createElement("div", {className: "panel-content"}, 
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: resourceFormData}, 
                            React.createElement("input", {type: "hidden", name: "resUuid", value: this.state.clientResource.resUuid}), 
                            React.createElement("input", {type: "hidden", name: "status", value: this.state.clientResource.status}), 
                            React.createElement(FormItem, {label: "应用简称"}, 
			                	React.createElement(SelectDropDown, {ref: "clientId", defaultText: "全部", defaultValue: "", value: this.state.clientResource.clientId, option: this.state.clientArray, onChange: this._changeClient})
			                ), 
                            React.createElement(FormItem, {label: "资源组"}, 
                            	React.createElement(SelectDropDown, {name: "resGroupUuid", defaultText: "全部", defaultValue: "", value: this.state.clientResource.resGroupUuid, option: this.state.resGroupArray, disabled: this.state.resGroupDisabled})
                            ), 
                            React.createElement(FormItem, {label: "资源描述"}, React.createElement(Input, {name: "descRibe", value: this.state.clientResource.descRibe})), 
                            React.createElement(FormItem, {label: "资源URI"}, React.createElement(Input, {name: "resUri", value: this.state.clientResource.resUri})), 
                            React.createElement(FormItem, {label: "涉及用户隐私"}, 
                            	React.createElement(SelectDropDown, {name: "isPrivacy", value: this.state.clientResource.isPrivacy, option: commonDropDown})
                            ), 
                            React.createElement(FormItem, {label: "资源组必须授权"}, 
                            	React.createElement(SelectDropDown, {name: "isDefault", value: this.state.clientResource.isDefault, option: commonDropDown})
                            )
                            /*<FormItem label="资源状态">
                            	<SelectDropDown name="status" value={this.state.clientResource.status} option={statusDropDown} />                            
                            </FormItem>*/
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

/***/ 21:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;

var commonMap = {"0":"否","1":"是"};

//var statusMap = {"0":"正常","1":"停用"};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function () {
		return {
			url: '',
			title: 'title',
			successFn: function () { },
			clientResource: {}
		}
	},
	init: function (title, url, data, successFn) {
		this.setState({
			title: title,
			url: url,
			successFn: successFn,
			clientResource: data
		});
		//this.refs.saveForm.setValues(data);
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
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label"}, "应用简称："), 
							React.createElement("span", null, this.state.clientResource.clientName)
						), 
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label"}, "资源组："), 
							React.createElement("span", null, this.state.clientResource.groupName)
						), 
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label"}, "资源描述："), 
							React.createElement("span", null, this.state.clientResource.descRibe)
						), 
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label"}, "资源URI："), 
							React.createElement("span", null, this.state.clientResource.resUri)
						), 
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label"}, "涉及用户隐私："), 
							React.createElement("span", null, commonMap[this.state.clientResource.isPrivacy])
						), 
						React.createElement("div", {className: "ucs-form-group"}, 
							React.createElement("span", {className: "label", style: {width:"120px"}}, "资源组必须授权："), 
							React.createElement("span", null, commonMap[this.state.clientResource.isDefault])
						)
						/*<div className="ucs-form-group">
							<span className="label">资源状态：</span>
							<span>{statusMap[this.state.clientResource.status]}</span>
						</div>*/
					)
				), 
				React.createElement("div", {className: "btn-panel"}, 
					React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
				)
			)
		);
	}
});

/***/ }),

/***/ 54:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Grid = __webpack_require__(0);
var Form = __webpack_require__(20);
var ShowView = __webpack_require__(21);
var PermissionLink = __webpack_require__(1);

//var statusArray = [
//	{value:"0",option:"正常"},
//	{value:"1",option:"停用"}
//];

myPanel = React.createClass({displayName: "myPanel",
	getInitialState: function() {
        return {            
//            resGroup: {},
        	resGroupDisabled: true,
            resGroupArray: [],
            resGroupObj: [],
            clientArray: [],
            clientRepo: []
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
            	clientArray: array,
            	clientRepo: array
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
	/*_changeGroup: function(event) {		
    	this.refs.grid.load({
    		status: this.refs.status.getValue(),
    		resGroupUuid: event
    	});
    },
    _changeStatus: function(event) {		
    	this.refs.grid.load({
    		status: event,
    		resGroupUuid: this.refs.resGroupUuid.getValue()
    	});
    },*/
    _query: function(event) {		
    	this.refs.grid.load({
//    		status: this.refs.status.getValue(),
    		resGroupUuid: this.refs.resGroupUuid.getValue(),
    		clientId: this.refs.clientId.getValue()
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
    _searchChangeClient: function(event) {    	
    	var array = [];
    	var value = event.target.value
    	
    	if(value == ""){
    		array = this.state.clientRepo;
    	}else{	
	    	$.each(this.state.clientRepo,function(){
	    		if(this.option.indexOf(value) != -1){
	    			array.push(this);
	    		}	
	    	});
    	}
//    	console.log(value);
    	console.log(array);
    	this.setState({
    		clientArray: array
    	});
    },
    _add: function() {
    	var me = this;
    	UcsmyIndex.openChildrenPage(Form, function(refPanel) {
    		refPanel.init('新增资源', 'clientResource/addResource', {}, function(){
    			me.refs.grid.load();
    		});
    	});
    },
    _updateClick: function(column) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(Form, function(refPanel) {
    		refPanel.init('修改资源', 'clientResource/editResource', column, function(){
    			me.refs.grid.load();
    		});
    	});
	},
	_showClick: function(column) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(ShowView, function(refPanel) {
    		refPanel.init('查看资源', 'clientResource/showResource', column, function(){
    			me.refs.grid.load();
    		});
    	});
	},
	_deleteClick: function(column) {
		var me = this;
		UcsmyIndex.confirm("确定", "是否确定删除该资源？", function() {
			$.post("clientResource/deleteResource", {"resUuid":column.resUuid}, function(data) {
				if(data.retcode == "0") {
					UcsmyIndex.alert("成功", data.retmsg);
					me.refs.grid.load();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
		    });
		});
	},
	_unbindClick:function(column){		
		var me = this;
    	UcsmyIndex.openChildrenPage(RoleUser, function(refPanel) {
    		refPanel.init("unbind", column);
    	});	
	},
	_bindClick: function(column) {
		var me = this;
    	UcsmyIndex.openChildrenPage(RoleUser, function(refPanel) {
    		refPanel.init("bind", column);
    	});		
	},
	_bindPermClick: function(column){
		var me = this;
    	UcsmyIndex.openChildrenPage(BindPermissionPanel, function(refPanel) {
    		refPanel.load(column.roleId,column.name);
    	});
		//this.props._bindPermission(column.roleId,column.name);
	},
	render:function() {
		var me = this;
		return (				
			React.createElement("div", null, 
				React.createElement("h2", {className: "content-title"}, "应用资源管理"), 
	            React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title"}, "查询条件"), 
	                React.createElement("div", {className: "panel-content"}, 
		                React.createElement(FormItem, {label: "应用简称"}, 
		                	React.createElement(SelectDropDown, {type: "search", ref: "clientId", defaultText: "全部", defaultValue: "", option: this.state.clientArray, onChange: this._changeClient, searchChange: this._searchChangeClient})
		                ), 
	                    React.createElement(FormItem, {label: "资源组"}, 
	                    	React.createElement(SelectDropDown, {ref: "resGroupUuid", defaultText: "全部", defaultValue: "", option: this.state.resGroupArray, disabled: this.state.resGroupDisabled})
	                    )
	                    /*<FormItem label="资源状态">
	                    	<SelectDropDown ref="status" defaultText="全部" defaultValue="" option={statusArray} />
	                    </FormItem>*/	
	                )
	            ), 
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._add}, "新增")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {url: "clientResource/queryResource", ref: "grid", isTextOverflowHidden: true, 
		                columns: [ {
          						name:'groupName', header: '资源组'
          					}, {
          						name: 'descRibe', header: '资源描述'
          					}, {
          						name: 'resUri', header: '资源URI'
          					}/*, {
          						name: 'isPrivacy', header: '涉及用户隐私', content: function (item) {
                                    var statusText = '否';
                                    if (item.isPrivacy == 1) {
                                        statusText = '是';
                                    }
                                    return (
                                        <span>
                                            {statusText}
                                        </span>
                                    )
          						}
          					}, {
          						name: 'isDefault', header: '资源组必须授权', content: function (item) {
                                    var statusText = '否';
                                    if (item.isDefault == 1) {
                                        statusText = '是';
                                    }
                                    return (
                                        <span>
                                            {statusText}
                                        </span>
                                    )
          						}
          					}, {
          						name: 'status', header: '资源状态', content: function (item) {
                                    var statusText = '正常';
                                    if (item.status == 1) {
                                        statusText = '停用';
                                    }else if (item.status == 2) {
                                        statusText = '已删除';
                                    }
                                    return (
                                        <span>
                                            {statusText}
                                        </span>
                                    )
          						}
          					}*/, {
          						name: 'modifyDate', header: '修改时间', content: function (item) {
                                    var dateText = new Date(item.modifyDate).format("yyyy-MM-dd hh:mm:ss");                                    
                                    return (
                                        React.createElement("span", null, 
                                            dateText
                                        )
                                    )
          						}
          					}, {
          						name: 'oper', header: '操作',
          						content: function(column) {
          							return (
      									React.createElement("div", null, 
      										React.createElement(PermissionLink, {permissionName: "resource_show", href: "javascript:void(0);", onClick: me._showClick.bind(this,column)}, "  查看详情  "), 
      										React.createElement(PermissionLink, {permissionName: "resource_update", href: "javascript:void(0);", onClick: me._updateClick.bind(this,column)}, "  修改  "), 
	    	                    		  	React.createElement(PermissionLink, {permissionName: "resource_delete", href: "javascript:void(0);", onClick: me._deleteClick.bind(this,column)}, "  删除  ")	    	                    		  	
    	                    		  	)
      								)
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