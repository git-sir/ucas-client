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
/******/ 	return __webpack_require__(__webpack_require__.s = 65);
/******/ })
/************************************************************************/
/******/ ({

/***/ 10:
/***/ (function(module, exports, __webpack_require__) {

var Navigation = __webpack_require__(50);

var LeftMenu = React.createClass({displayName: "LeftMenu",
    getDefaultProps: function(){
		return{
            data : [
                {
                    name: '需求管理', childShow: true, href: "javascript:;", icon: { left: 'iconfont menu-left-span menu-left-icon1', right: 'iconfont menu-right-span'},
                    children: [
                        {name: '金融机构', id: 'menu1', href: "javascript:;", active: true},
                        {name: '企业', id: 'menu2', href: "javascript:;"},
                        {name: '个人客户', id: 'menu3', href: "javascript:;"}
                    ]
                },
                {
                    name: '设置管理', href: "javascript:;", icon: { left: 'iconfont menu-left-span menu-left-icon2', right: 'iconfont menu-right-span'},
                    children: [
                        {name: '权限', id: 'menu4', href: "javascript:;"},
                        {name: '设置分类', id: 'menu5', href: "javascript:;"}
                    ]
                }
            ]
		}
    },
    onClick:function(e){
        var id = e.id;
        var subchild = document.getElementsByClassName('leftMenu')[0].getElementsByClassName('children');
        for(var i= 0;i<subchild.length;i++){
            var subLen = subchild[i].getElementsByTagName('a');
            for(var j = 0; j< subLen.length;j++){
                subLen[j].className = '';
            }
        }
        document.getElementById(id).className = 'active';
    },
    render:function(){
    	return(
			React.createElement("div", {className: "leftMenu"}, 
				React.createElement(Navigation, {data: this.props.data, className: "ucs-nav-top", onClick: this.onClick})
			)
		)
    }
})
module.exports = LeftMenu;

/***/ }),

/***/ 37:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;

var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var configFormData = {

	    "name": [
	        {type: "required", msg: "权限名称不能为空"},
	        {type : "maxlength", maxlength : 32, msg : "权限长度不能大于32"}
	    ],
	    "urlAction": [
	        {type: "required", msg: "URL不能为空"},
	        {type : "maxlength", maxlength : 256, msg : "资源URL长度不能大于256"}
	    ],
	    "sn": [
	        {type : "maxlength", maxlength : 36, msg : "判断标识长度不能大于36"}
	    ],
	    "description": [
		        {type : "maxlength", maxlength : 36, msg : "描述长度不能大于256"}
		    ]

	};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState:function(){
    	return {
			url: '',
			title: 'layerTitle',
			data:{}
		};
    },
    open: function(title, url, data, successFn) {
		this.setState({
			title: title,
			url: url,
			successFn: successFn,
			data:data
		});
	},


	_validate: function (callback) {
        var validateField = [
            "name", "urlAction", "sn","description"
        ];

		var fn = function (result) {
			if(result) {
				callback();
			}
		};

        this.refs.saveForm.validate(fn, validateField);

        return status;
    },
	
	
	_saveData: function() {
		var me = this;
		

		this._validate(function(){
            _addButtonDisabled('save');
			$.post(me.state.url,  $('#saveForm').serialize(), function(data) {
                _removeButtonDisabled('save');
				if(data.retcode == '0') {
					UcsmyIndex.closeChildrenPage();
					UcsmyIndex.alert("成功", data.retmsg);
					me.state.successFn();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(xhr, errorText, errorType){
                _removeButtonDisabled('save');
				UcsmyIndex.alert("失败", "网络异常");
			});
		});
	},
	_return: function(event) {
		UcsmyIndex.closeChildrenPage();
	},
    render:function(){
    	var me = this;
    	return(
			React.createElement("div", null, 
				React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
	                React.createElement("div", {className: "panel-content"}, 
	                
	                
                    React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
	                    React.createElement("input", {type: "hidden", name: "permissionId", value: this.state.data.permissionId}), 
	                    React.createElement("input", {type: "hidden", name: "moduleId", value: this.state.data.moduleId}), 
	                    React.createElement(FormItem, {label: "权限名称"}, React.createElement(Input, {name: "name", value: this.state.data.name})), 
	                    React.createElement(FormItem, {label: "描述"}, React.createElement(Input, {name: "description", value: this.state.data.description})), 
	                    React.createElement(FormItem, {label: "判断标识"}, React.createElement(Input, {name: "sn", value: this.state.data.sn})), 
	                    React.createElement(FormItem, {label: "资源URL"}, React.createElement(Input, {name: "urlAction", value: this.state.data.urlAction}))
                   )
	                
	                )
	            ), 
	            React.createElement("div", {className: "btn-panel"}, 
		            React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._saveData}, "保存"), 
		            React.createElement(Button, {buttonType: "cancel", onClick: this._return}, "取消")
		        )
	        )
		)
    }

});

/***/ }),

/***/ 50:
/***/ (function(module, exports, __webpack_require__) {

/*
黄德滨：这是组件源码，无需修改
 */
/*
 * 参数，带child开头的参数必须是要有子菜单才生效
 * （所有子导航的隐藏与显示都是通过添加和删除样式来控制）
 * className
 * data:[
 *    name:标题,
 *    href:链接地址,
 *    active:当前选中状态
 *    target：打开方式
 *    icon:默认不显示
 *    children：[子菜单，同样包括name,href,active,target
 *       //以下有子菜单才有效
 *       childShow:true/false 默认子菜单是否展开，默认为false
 *    ]
 *]
 * childShowType:click,mouseover,鼠标点击或经过显示
 * bodyClick:点击空白处允许菜单收起来，一般不设置
 * */
//obj为要绑定事件的元素，ev为要绑定的事件，fn为绑定事件的函数
function myAddEvent(obj, ev, fn) {
    if (obj.attachEvent) {
        obj.attachEvent("on" + ev, fn);
    }
    else {
        obj.addEventListener(ev, fn, false);
    }
}
function myRemoveEvent(obj, ev, fn) {
    if (obj.attachEvent) {
        obj.detachEvent("on" + ev, fn);
    }
    else {
        obj.removeEventListener(ev, fn, false);
    }
}
function findParentNode(elem, cls) {
    if (elem.nodeName.toUpperCase() === "BODY") {
        return false;
    } else if (elem.className.search(cls) > -1) {
        return elem;
    } else {
        return findParentNode(elem.parentNode, cls);
    }
}
var classnames = __webpack_require__(7);
var Navigation = React.createClass({displayName: "Navigation",
    getInitialState: function () {
        return {}
    },
    getDefaultProps: function () {
        return {childShowType: 'click', bodyClick: false}
    },
    _handleClick: function (key) {
        var ref = this.refs['keyul' + key];
        if (ref.className == 'children') {
            ref.className = 'children show'
        } else {
            ref.className = 'children'
        }
        this.props.bodyClick ?
            myAddEvent(document, 'click', this._handleBodyClick.bind(this, key)) : ""
    },
    _handleBodyClick: function (key, e) {
        var a = findParentNode(e.srcElement, 'ucs-menu-li-' + key);
        if (a === false) {
            this.refs['keyul' + key].className = 'children'
        }
    },
    _handleMouseOver: function (key) {
        this.refs['keyul' + key].className = 'children show';
        this.refs['key' + key].className = 'hover has-child ucs-menu-li-' + key;
    },
    _handleMouseLeave: function (key) {
        this.refs['keyul' + key].className = 'children';
        this.refs['key' + key].className = 'has-child ucs-menu-li-' + key;
    },
    componentWillUnmount: function () {
        this.state.bodyClick ?
            myRemoveEvent(document, 'click', this._handleBodyClick) : "";
    },
    componentWillMount: function () {
        this.index = 0;
    },
    MenuMap: function (props) {
        return (
            props && props.map(function (child, index) {
                var event = '';
                this.index++;

                if (child.children && this.props.childShowType === 'click') {
                    event = 'click';
                } else if (child.children && this.props.childShowType === 'mouseover') {
                    event = 'mouseover';
                }
                var rli = '';
                if(child.children){rli='ucs-menu-li-' + this.index}
                var li = {
                        className: classnames({'active': child.active}, {'has-child': child.children}, rli),
                        //onClick: event === 'click' ? this._handleClick.bind(this, this.index, child.name) : "",
                        //onMouseOver: event === 'mouseover' ? this._handleMouseOver.bind(this, this.index) : "",
                        //onMouseLeave: event === 'mouseover' ? this._handleMouseLeave.bind(this, this.index) : "",
                        //ref: 'key' + this.index
                    }
                    ;
                var div = {
                    onClick: event === 'click' ? this._handleClick.bind(this, this.index, child.name) : "",
                    onMouseOver: event === 'mouseover' ? this._handleMouseOver.bind(this, this.index) : "",
                    onMouseLeave: event === 'mouseover' ? this._handleMouseLeave.bind(this, this.index) : "",
                    ref: 'key' + this.index
                }
                //图标处理
                var icons = child.icon;
                if(icons){
                    var leftIconReturn = ( React.createElement("i", {className: icons.left}) );
                    var rightIconReturn = ( React.createElement("i", {className: icons.right}) );
                }else{
                    var leftIconReturn = rightIconReturn = '';
                }
                return (
                    React.createElement("li", React.__spread({},  li), 
                        React.createElement("div", React.__spread({className: "menu-title"},  div), 
                            leftIconReturn, 
                            child.href ?
                                React.createElement("a", {
                                    id: child.id, 
                                    href: child.href, 
                                    onClick: this.onClick.bind(this, child), 
                                    target: child.target, 
                                    className: classnames({'active': child.active})}, 
                                    child.name
                                ) :
                                React.createElement("span", {className: classnames({'active': child.active})}, child.name), 
                            
                            rightIconReturn
                        ), 
                        child.children ?
                            React.createElement("ul", {className: classnames('children',{'show':child.childShow}), 
                                ref: "keyul"+this.index}, 
                                this.MenuMap(child.children)
                            )
                            : ""
                    )
                );
            }.bind(this))
        )
    },
    onClick:function(child){
        this.props.onClick ? this.props.onClick(child) : '';
    },
    render: function () {
        return (
            React.createElement("div", {className: classnames('ucs-nav clearfix',this.props.className)}, 
                React.createElement("ul", null, 
                    this.MenuMap(this.props.data)
                )
            )
        )
    }
});
module.exports = Navigation;

/***/ }),

/***/ 65:
/***/ (function(module, exports, __webpack_require__) {

var Header = __webpack_require__(9);
var LeftMemu = __webpack_require__(10);
var Content = __webpack_require__(8);
var PermissionForm = __webpack_require__(37);
var Input = UcsmyUI.Input;
var Layer = UcsmyUI.Layer;
var Button = UcsmyUI.Button;
var TreeTable = UcsmyUI.TreeTable;
var Table = UcsmyUI.Table;
var FormItem = UcsmyUI.Form.FormItem;

myPanel = React.createClass({displayName: "myPanel",
    getInitialState: function () {
        return {
            treeData: {},
            permissionData: [],
            dataId: '',
            treeColumn: [
                {name: '', field: 'name', headerClass: 'ucs-treeTable-th', setContent: this.get}
            ]
        }
    },
    componentDidMount: function () {
        var me = this;
        $.post("module/list", function (data) {
            me.setState({treeData: data.data[0]});
        }, "json").error(function (xhr, errorText, errorType) {
            UcsmyIndex.alert("失败", "网络异常");
        });
    },
    get: function (item) {
        return React.createElement("a", {className: "treeItem", name: item.id, onClick: this._handleclick.bind(this, item)}, item.name)
    },
    _handleclick: function (item) {
        var me = this;
        var id = item.id;
        var subchild = getElementsByClassName('treeItem', 'a');
        for (var i = 0; i < subchild.length; i++) {
            subchild[i].className = (subchild[i].name == id ? "treeItem current" : "treeItem");
        }

        me.setState({dataId: item.id});
        me._loadPermission(item.id);
    },
    _loadPermission: function (moduleId) {
        var me = this;
        $.post("permission/list", {'id': moduleId}, function (data) {
            me.setState({permissionData: data.data});
        }, "json").error(function (xhr, errorText, errorType) {
            UcsmyIndex.alert("失败", "网络异常");
        });
    },
    _addPermission: function (e) {
        var me = this;
        if (UcsmyIndex.fn.isEmpty(this.state.dataId))
            return;

        var data = {moduleId: me.state.dataId};
        UcsmyIndex.openChildrenPage(PermissionForm, function (refPanel) {
            refPanel.open('添加权限', 'permission/add', data, function () {
                me._loadPermission(me.state.dataId);
            });
        });
    },
    _update: function (item) {
        var me = this;
        UcsmyIndex.openChildrenPage(PermissionForm, function (refPanel) {
            refPanel.open('修改权限', 'permission/update', item, function () {
                me._loadPermission(item.moduleId);
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "是否确定删除该权限？", function () {
            $.post("permission/delete", {"permissionId": item.permissionId}, function (data) {
                if (data.retcode == "0") {
                    UcsmyIndex.alert("消息", data.retmsg);
                    me._loadPermission(item.moduleId);
                } else {
                    UcsmyIndex.alert("异常消息", data.retmsg);
                }
            }, "json").error(function (xhr, errorText, errorType) {
                UcsmyIndex.alert("失败", "网络异常");
            });
        });
    },
    render: function () {
        var me = this;
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "权限管理"), 
                React.createElement("div", {className: "panel"}, 
                    React.createElement("div", {className: "panel-content"}, 
                        React.createElement("div", {className: "organizationTree"}, 
                            React.createElement(TreeTable, {data: this.state.treeData, column: this.state.treeColumn, id: "tabletest", 
                                       showLine: true, showIcon: false})
                        ), 
                        React.createElement("div", {className: "organizationTable"}, 
                            React.createElement("div", {className: "btn-panel"}, 
                                React.createElement(Button, {buttonType: UcsmyIndex.fn.isEmpty(this.state.dataId) ? "disabled" : "bidnow", 
                                        onClick: this._addPermission}, "添加权限")
                            ), 
                            React.createElement(Table, {id: "mytable", bordered: true, striped: true, hover: true, hasThead: true, 
                                   data: this.state.permissionData, 
                                   isTextOverflowHidden: true, 
                                   columns: [{
                                       name: 'name', header: '名称', width: 140
                                   }, {
                                       name: 'description', header: '描述', width: 150
                                   }, {
                                       name: 'sn', header: '判断标识', width: 200
                                   }, {
                                       name: 'urlAction', header: '资源URL', width: 300
                                   }, {
                                       name: 'cz', header: '操作', width:100,
                                       content: function (item) {
                                           return (
                                               React.createElement("span", null, 
                                                React.createElement("a", {href: "Javascript:void(0);", 
                                                   onClick: me._update.bind(this, item)}, "修改"), 
                                                   "    ", 
                                                   React.createElement("a", {href: "Javascript:void(0);", onClick: me._delete.bind(this, item)}, "删除")
                                            )
                                           )
                                       }
                                   }]})
                        ), 
                        React.createElement("div", {className: "clearfix"})
                    )
                )
            )
        )
    }
})


/***/ }),

/***/ 7:
/***/ (function(module, exports, __webpack_require__) {

var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!
  Copyright (c) 2016 Jed Watson.
  Licensed under the MIT License (MIT), see
  http://jedwatson.github.io/classnames
*/
/* global define */

(function () {
	'use strict';

	var hasOwn = {}.hasOwnProperty;

	function classNames () {
		var classes = [];

		for (var i = 0; i < arguments.length; i++) {
			var arg = arguments[i];
			if (!arg) continue;

			var argType = typeof arg;

			if (argType === 'string' || argType === 'number') {
				classes.push(arg);
			} else if (Array.isArray(arg)) {
				classes.push(classNames.apply(null, arg));
			} else if (argType === 'object') {
				for (var key in arg) {
					if (hasOwn.call(arg, key) && arg[key]) {
						classes.push(key);
					}
				}
			}
		}

		return classes.join(' ');
	}

	if (typeof module !== 'undefined' && module.exports) {
		module.exports = classNames;
	} else if (true) {
		// register as 'classnames', consistent with npm package name
		!(__WEBPACK_AMD_DEFINE_ARRAY__ = [], __WEBPACK_AMD_DEFINE_RESULT__ = function () {
			return classNames;
		}.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	} else {
		window.classNames = classNames;
	}
}());


/***/ }),

/***/ 8:
/***/ (function(module, exports) {


var Content = React.createClass({displayName: "Content",
    getInitialState: function(){
		return{
            className: this.props.className ? 'content '+this.props.className : 'content'
		}
    },
	componentDidMount:function () {
    },
	onchange:function (e) {
    },
    onClick: function (e) {

    },
    render:function(){
    	return(
			React.createElement("div", React.__spread({},  this.props, {className: this.state.className}), 
				this.props.children
			)
		)
    }
})
module.exports = Content;

/***/ }),

/***/ 9:
/***/ (function(module, exports) {

var Header = React.createClass({displayName: "Header",
    getDefaultProps: function(){
		return{
			username:'ucser-aaaa',
			userImg:''
		}
    },
	componentDidMount:function () {
    },
	onchange:function (e) {
    },
    onClick: function (e) {

    },
    render:function(){
    	return(
			React.createElement("div", {className: "header"}, 
				React.createElement("div", {className: "header-logo"}, 
					React.createElement("div", {className: "ucserImg"}, 
						React.createElement("img", {src: "../../images/logo.png", alt: ""})
					), 
					React.createElement("span", {className: "header-name"}, "网金决策方案管理系统")
				), 
				React.createElement("div", {className: "header-mes"}, 
					React.createElement("ul", null, 
						React.createElement("li", null, React.createElement("img", {className: "userImg", src: this.props.userImg}), this.props.username, "，你好！ "), 
						React.createElement("li", null, React.createElement("a", {className: "btn-loginout", href: "javascript:;", onClick: this.props.onClick}), " ")
					)
				)
			)
		)
    }
})
module.exports = Header;

/***/ })

/******/ });