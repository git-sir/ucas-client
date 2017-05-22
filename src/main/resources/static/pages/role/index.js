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
/******/ 	return __webpack_require__(__webpack_require__.s = 67);
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

/***/ 41:
/***/ (function(module, exports) {

var Button = UcsmyUI.Button;
var TreeTable = UcsmyUI.TreeTable; 
var Checkbox =UcsmyUI.Checkbox;

var BindPermissionPanel = React.createClass({displayName: "BindPermissionPanel",
	getInitialState:function(){//react组件的初始化状态
		return {
			column:[
			        {name: '功能菜单', field: 'name', headerClass: 'id_col', className: 'id_col'},
			        {name: '操作权限', field: 'name', headerClass: 'value_col', className: 'value_col',setContent:this.operatorValue}
			       ],
			treeData:"",        
			roleId : "",
			name:""
		};
	},
	componentDidMount:function(){
		
	},
	
    moduleName: function (item) {
    	var astr =[];
    	astr.push(React.createElement("a", {href: "javascript:;"}, item.value))
    	return astr;
    },   
    operatorValue: function (item) {
    	var astr =[];
    	if(item.permissionList){
    		item.permissionList.map(function (permission) {
    			astr.push(React.createElement(Checkbox, {text: permission.permissionName, value: item.id +"_" + permission.permissionId, checked: permission.cheched, name: "rolePermissionID"}))
    		})
    	}else {
    		astr.push('')
    	}
    	return astr;
    },
    
    /**
     * 只能有一个根节点的json树对象
     * 如果有多个根节点或没有数据会报错
     */
	load:function(id,name){
		var that = this;
		
		$.post("rolePermission/queryRolePermission", {"role_id":id}, function(data) {
			if(!data.success){
				UcsmyIndex.alert("失败", data.msg);
			}else{
				that.setState({
	    	 		roleId:id,
	    	 		name:name,
	    	 		treeData:data.rolePermissions[0]
	    	 	});
			}
 		}, "json").error(function(xhr, errorText, errorType){
 			UcsmyIndex.alert("失败", "网络异常");
 	    });
	},
	
	_backClick:function(){
		UcsmyIndex.closeChildrenPage();
	},
	_addPermission:function(){
		var that = this;
		var ids = new Array();
		var obj = document.getElementsByName("rolePermissionID");
	    for(k in obj){
	        if(obj[k].checked)
	        	ids.push(obj[k].value);
	    }
	    UcsmyIndex.confirm("确定", "你真的要为角色分配权限吗？", function() {
            _addButtonDisabled('save');
	    	$.post("rolePermission/addRolePermission", {"role_id": that.state.roleId,"permissions_id": ids.join(","),"name": that.state.name}, function(data) {
                _removeButtonDisabled('save');
	    		if(data.success){
					UcsmyIndex.alert("消息", data.msg);
					that.load(data.role_id,data.name);
				}else{
					UcsmyIndex.alert("异常消息", data.msg);
				}
	 		}, "json").error(function(xhr, errorText, errorType){
                _removeButtonDisabled('save');
	 			UcsmyIndex.alert("失败", "网络异常");
	 	    });
		});
	    
	    
	},
	render:function(){
		var that = this;
		return (
				React.createElement("div", {className: this.props.className}, 
		            React.createElement("div", {className: "form-group mar-r15"}, 
						React.createElement("h2", {className: "content-title"}, "角色：", this.state.name), 
						React.createElement("div", {className: "btn-panel"}, 
							React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._addPermission}, "添加权限"), 
							React.createElement(Button, {buttonType: "cancel", onClick: this._backClick}, "返回")
						)
                    ), 
		            React.createElement("div", null, 
	                	React.createElement(TreeTable, {data: that.state.treeData, column: that.state.column, id: "tabletest"})
		            )
				)
		);
	}
});
module.exports = BindPermissionPanel;

/***/ }),

/***/ 42:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var roleFormData = {

    "name": [
        {type: "required", msg: "角色名不能为空"},
        {type : "maxlength", maxlength : 36, msg : "参数名称长度不能超过36"}
    ],
    "description": [
        {type: "required", msg: "描述不能为空"},
        {type : "maxlength", maxlength : 255, msg : "参数值长度不能超过255"}
    ]

};

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function(){
		return {
			url: '',
			title: 'title',
			successFn: function() {},
            role: {}
		}
	},
	_validate: function (callback) {
        var status = false;
        var validateField = [
            "name", "description"
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
            role: data
		});
		//this.refs.saveForm.setValues(data);
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
                        React.createElement(Form, {id: "form", ref: "saveForm", formData: roleFormData}, 
                            React.createElement("input", {type: "hidden", name: "roleId", value: this.state.role.roleId}), 
                            React.createElement(FormItem, {label: "角色名"}, React.createElement(Input, {name: "name", value: this.state.role.name})), 
                            React.createElement(FormItem, {label: "描述"}, React.createElement(Input, {name: "description", value: this.state.role.description}))
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

/***/ 43:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Checkbox = UcsmyUI.Checkbox;
var Grid = __webpack_require__(0);

module.exports = React.createClass({displayName: "module.exports",
	getInitialState: function() {
        return{
        	gridUrl: '',
			saveUrl: '',
			title: '',
			sign: '',
			data: {}
        }
    },
    init: function(sign, data, successFn) {// sign: bind or unbind
    	var title, gridUrl, saveUrl;
    	if(sign == "bind") {
    		title = "绑定用户";
    		gridUrl = "role/queryUnbindUserList?roleId=" + data.roleId;
    		saveUrl = "role/bindUser";
    	} else {
    		title = "解绑用户";
    		gridUrl = "role/queryBindUserList?roleId=" + data.roleId;
    		saveUrl = "role/unbindUser";
    	}
    	this.setState({
    		gridUrl: gridUrl,
    		saveUrl: saveUrl,
    		title: title,
    		data: data,
    		sign: sign
    	});
    },
	componentDidUpdate: function() {
		this.refs.grid.load();
	},
    _return: function(event) {
		UcsmyIndex.closeChildrenPage();
	},
	_onClick: function(event) {
		var me = this;
		var ids = [];
		var obj = document.getElementsByName("userRoleId");
		for(var i = 0; i < obj.length; i++) {
			if(obj[i].checked)
				ids.push(obj[i].value);
		}
		if (ids.length == 0){
			UcsmyIndex.alert("异常消息", "请选择要绑定的用户");
			return;
		}
		var d;
		if(this.state.sign == "bind") {
			d = {"roleId": this.state.data.roleId, "userIds": ids.join(",")};
    	} else {
    		d = {"ids": ids.join(",")};
    	}
		UcsmyIndex.confirm("确定", "是否确定操作选中的用户？", function(){
            _addButtonDisabled('save');
			$.post(me.state.saveUrl, d, function(data){
                _removeButtonDisabled('save');
				if(data.retcode == "0"){
					UcsmyIndex.alert("成功", data.retmsg);
					me._return();
				} else{
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
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._onClick}, this.state.title), 
	                React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "返回")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {
		            	url: this.state.gridUrl, ref: "grid", 
		                columns: [ {
							 name:'roleId',
							 header: "",
							 content: function(column) {
								 return (
									 React.createElement(Checkbox, {value: this.state.sign == "bind" ? column.userId : column.id, name: "userRoleId", eventId: this.state.eventId})
								 );
							 }.bind(this)
						}, {
      						name: 'userAccount', header: '用户账号'
      					}, {
      						name: 'userName', header: '姓名'
      					}]}
		            ), 
		            React.createElement("div", {className: "clearfix"})
		        )
			)
		);
	}
});

/***/ }),

/***/ 67:
/***/ (function(module, exports, __webpack_require__) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Grid = __webpack_require__(0);
var Form = __webpack_require__(42);
var RoleUser = __webpack_require__(43);
var BindPermissionPanel = __webpack_require__(41);
var PermissionLink = __webpack_require__(1);

myPanel = React.createClass({displayName: "myPanel",
    _query: function() {
    	this.refs.grid.load({
    		name: this.refs.name.getValue()
    	});
    },
    _add: function() {
    	var me = this;
    	UcsmyIndex.openChildrenPage(Form, function(refPanel) {
    		refPanel.init('新增角色', 'role/addRole', {}, function(){
    			me.refs.grid.load();
    		});
    	});
    },
    _updateClick: function(column) {
    	var me = this;
    	UcsmyIndex.openChildrenPage(Form, function(refPanel) {
    		refPanel.init('修改角色', 'role/updateRole', column, function(){
    			me.refs.grid.load();
    		});
    	});
	},
	_deleteClick: function(column) {
		var me = this;
		UcsmyIndex.confirm("确定", "是否确定删除该角色？", function() {
			$.post("role/deleteRole", {"roleId":column.roleId}, function(data) {
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
				React.createElement("h2", {className: "content-title"}, "角色管理"), 
	            React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title"}, "查询条件"), 
	                React.createElement("div", {className: "panel-content"}, 
	                    React.createElement(FormItem, {label: "用户名"}, React.createElement(Input, {ref: "name"}))
	                )
	            ), 
	            React.createElement("div", {className: "btn-panel"}, 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._query}, "查询"), 
	                React.createElement(Button, {buttonType: "bidnow", onClick: this._add}, "新增")
	            ), 
				React.createElement("div", {className: "table-panel"}, 
		            React.createElement(Grid, {url: "role/queryRoleList", ref: "grid", isTextOverflowHidden: true, 
		                columns: [ {
          						name: 'name', header: '角色名'
          					}, {
          						name: 'description', header: '说明',width:500
          					}, {
          						name: 'oper', header: '操作',
          						content: function(column) {
          							return (
      									React.createElement("div", null, 
      										React.createElement(PermissionLink, {permissionName: "role_update", href: "javascript:void(0);", onClick: me._updateClick.bind(this,column)}, "  修改  "), 
	    	                    		  	React.createElement(PermissionLink, {permissionName: "role_delete", href: "javascript:void(0);", onClick: me._deleteClick.bind(this,column)}, "  删除  "), 
	    	                    		  	React.createElement(PermissionLink, {permissionName: "role_bind_user", href: "javascript:void(0);", onClick: me._bindClick.bind(this,column)}, "  绑定用户  "), 
	    	                    		  	React.createElement(PermissionLink, {permissionName: "role_unbind_user", href: "javascript:void(0);", onClick: me._unbindClick.bind(this,column)}, "  解绑用户  "), 
	    	                    		  	React.createElement(PermissionLink, {permissionName: "role_bind_permission", href: "javascript:void(0);", onClick: me._bindPermClick.bind(this,column)}, "  绑定权限  ")
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