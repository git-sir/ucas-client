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
/******/ 	return __webpack_require__(__webpack_require__.s = 59);
/******/ })
/************************************************************************/
/******/ ({

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

/***/ 29:
/***/ (function(module, exports) {


var ChildrenComponent = React.createClass({displayName: "ChildrenComponent",
	open: function(panel, fn) {
		
	},
	close: function() {
		
	},
	componentDidMount: function() {
		this.props.successFn(this.refs.panel);
	},
	render: function(){
		return (
			React.createElement("div", null, 
				React.createElement(this.props.panel, {ref: "panel"})
			)   
	    );
	 }
});

module.exports = ChildrenComponent;

/***/ }),

/***/ 30:
/***/ (function(module, exports) {

// UcsmyIndex参数在html上定义

// 动态加载组件
var timestamp = Date.parse(new Date());
UcsmyIndex.loadComponent = function(url, successFn, errorFn) {
	$.ajax({
		url: url + "?_=" + timestamp,
		dataType: "script",
		cache: true,
		success: function() {
			if (successFn) {
				successFn(myPanel);
			}
		},
		error: function() {
			if (errorFn) {
				errorFn();
			}
		}
	});
}

//增加csrf到默认header
var meta = document.getElementsByTagName('meta');
var csrf = meta['_csrf'].getAttribute("content");
var csrfHeader = meta['_csrf_header'].getAttribute("content");
//var tokenType = meta['token_type'].getAttribute("content");
/** 重写jqery ajax post get方法 */
var _ajax = $.ajax;
$.ajax = function(opt) {
	// 备份opt中error和success方法
	var fn = {
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success: function(data, textStatus) {
		}
	}
	if (opt.error) {
		fn.error = opt.error;
	}
	if (opt.success) {
		fn.success = opt.success;
	}
	
	if(!opt.headers) {
		opt.headers = {};
	}
	opt.headers[csrfHeader] = csrf;
	opt.headers['token_type']="LOCAL";
	// 扩展增强处理
	var _opt = $.extend(opt, {
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			// 错误方法增强处理
			_error(XMLHttpRequest, function(){
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			});
		},
		success: function(data, textStatus) {
			// 成功回调方法增强处理
			fn.success(data, textStatus);
		}
	});
	return _ajax(_opt);
};

var _post = $.post;
$.post = function(a, c, d, e) {
	var me = this;
	this._error;
	this._ppp = {
		error: function(fn){
			me._ppp._error = fn;
			return me._ppp;
		},
		_error: function() {}
	};
	_post(a, c, d, e).error(function(xhr, errorText, errorType) {
		_error(xhr, function(){
			me._ppp._error(xhr, errorText, errorType);
		});
	});
	return this._ppp;
};

var _get = $.get;
$.get = function(a, c, d, e) {
	var me = this;
	this._error;
	this._ppp = {
		error: function(fn){
			me._ppp._error = fn;
			return me._ppp;
		},
		_error: function() {}
	};
	_get(a, c, d, e).error(function(xhr, errorText, errorType) {
		_error(xhr, function(){
			me._ppp._error(xhr, errorText, errorType);
		});
	});
	return this._ppp;
};

_error = function(XMLHttpRequest, bfn) {
	if(XMLHttpRequest.status == 401) {
		UcsmyIndex.alert("失败", "权限不足");
	} else if(XMLHttpRequest.status == 403) {
		UcsmyIndex.confirm("登录异常", "登录失效，是否重新登录？", function() {
			window.location.reload();
		});
	} else if(XMLHttpRequest.status == 444) {
		UcsmyIndex.confirm("登录异常", "登录信息失效，是否刷新或重新登录？", function() {
			window.location.reload();
		});
	} else {
		bfn();
	}
}

$(window).resize(function () {
	resizeContent();
});

UcsmyIndex.fn = {};
UcsmyIndex.fn.isEmpty = function(valueStr) {
	if(valueStr == null || valueStr == undefined || valueStr == "") {
		return true;
	}
	return false;
}

// 在content上增加class
UcsmyIndex.addContentClassName = function(className) {
	$(".content").addClass(className);
};
// 重置content的class
UcsmyIndex.resetContentClassName = function() {
	var content = $(".content");
	content.removeClass();
	content.addClass("content");
}


/***/ }),

/***/ 31:
/***/ (function(module, exports) {

var Home = React.createClass({displayName: "Home",
	componentDidMount: function() {
		UcsmyIndex.addContentClassName("welcome");
	},
	render: function(){
		return (
			React.createElement("div", {className: "welcome-center"}, 
				React.createElement("img", {src: "images/wangjin-icon-big.png"}), 
				React.createElement("p", null, "网金统一认证管理系统", React.createElement("br", null), "欢迎您！")
			)
        );
	 }
});
module.exports = Home;

/***/ }),

/***/ 32:
/***/ (function(module, exports, __webpack_require__) {

/*
var Tree = UcsmyUI.Tree;
var Left = React.createClass({
    getDefaultProps: function(){
		return{

		}
    },
    getInitialState:function(){//react组件的初始化状态
		return ({
			data: myModules
		});
	},
	componentDidMount:function () {
    },
	onchange:function (e) {
    },
    onClick: function (data, e) {
    	if(data.url) {
    		this.props.onClick(data.url, data.id);
    		return;
    	}

    	// 点击打开
    	var parent = $(e.target).parent().parent();
    	var arrow = $(parent.find(".ucs-tree-arrow")[0]);
    	var children = $(parent.find(".ucs-tree-children")[0]);
    	if(arrow.hasClass("ucs-tree-arrow-collapsed")) {
    		arrow.removeClass("ucs-tree-arrow-collapsed");
        	children.removeClass("ucs-tree-children-collapsed");
    	} else {
    		arrow.addClass("ucs-tree-arrow-collapsed");
        	children.addClass("ucs-tree-children-collapsed");
    	}
    },
    select: function(id) {
    	var subchild = getElementsByClassName('ucs-tree-children', 'div');
		for(var i= 0; i < subchild.length; i++){
			for(var j = 0; j < subchild[i].getElementsByTagName('a').length; j++){
                subchild[i].getElementsByTagName('a')[j].className = '';
			}
		}
		var a = document.getElementById(id);
		if(a)
			a.className = 'current';
    },
    render:function(){
    	return(
			<div className="leftMenu">
				<Tree data={this.state.data} onClick = {this.onClick}/>
			</div>
		)
    }
})
module.exports = Left;
*/
var Navigation = __webpack_require__(60);
var Left = React.createClass({displayName: "Left",
    getDefaultProps: function(){
		return{
			
		}
    },
    getInitialState:function(){//react组件的初始化状态
		return ({
			data: myModules
		});
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
        
        if(e.url) {
    		this.props.onClick(e.url, e.id);
    		return;
    	}
    },
    select: function(id) {
    	
    },
    render:function(){
    	return(
			React.createElement("div", {className: "leftMenu"}, 
				React.createElement(Navigation, {data: this.state.data, className: "ucs-nav-top", onClick: this.onClick})
			)
		)
    }
})
module.exports = Left;

/***/ }),

/***/ 33:
/***/ (function(module, exports) {

var Header = React.createClass({displayName: "Header",
    getDefaultProps: function(){
        return{
            username: 'ucser-aaaa',
            userImg: '',
            InterNum: '',
            roleName: '',
            loginTime: ''
        }
    },
    componentDidMount:function () {
    },
    onchange:function (e) {
    },
    onClick: function (e) {
        this.props.onUserInfo();
    },
    render:function(){
        return(
			React.createElement("div", {className: "header"}, 
				React.createElement("div", {className: "header-logo"}, 
					React.createElement("div", {className: "ucserImg"}, 
						React.createElement("img", {src: "images/logo.png", alt: ""})
					), 
					React.createElement("span", {className: "header-name"}, "统一认证管理系统")
				), 
				React.createElement("div", {className: "header-mes"}, 
					React.createElement("ul", null, 
						React.createElement("li", {onClick: this.onClick, style: {"cursor": "pointer"}}, 
							React.createElement("img", {className: "userImg", src: "images/user_img.png"}), myUser.name
						), 
						React.createElement("li", null, "机构：", myUser.orgName), 
						React.createElement("li", null, "角色：", myUser.roleName), 
						React.createElement("li", null, "登录日期：", myUser.loginTime), 
						React.createElement("li", null, React.createElement("a", {className: "btn-loginout", href: "outSys"}, " "), " ")
					)
				)
			)
        )
    }
})
module.exports = Header;

/***/ }),

/***/ 34:
/***/ (function(module, exports, __webpack_require__) {

var Form = __webpack_require__(2);

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
module.exports = React.createClass({displayName: "module.exports",
	_update: function(event) {
		var me = this;
        _addButtonDisabled('save');
		$.post("user/updateUserPassword", this.refs.passwordForm.getValues(), function(data) {
            _removeButtonDisabled('save');
			if(data.retcode == 0) {
				//UcsmyIndex.alert("成功", data.msg);
				UcsmyIndex.alert("更新成功", data.msg);
			} else {
				UcsmyIndex.alert("失败", data.msg);
			}
		}, "json").error(function(xhr, errorText, errorType) {
            _removeButtonDisabled('save');
			UcsmyIndex.alert("失败", "网络异常");
	    });
	},
	render: function(){
		return (
			React.createElement("div", null, 
				React.createElement("div", {className: "panel"}, 
					React.createElement("div", {className: "panel-title fc-red"}, "个人信息"), 
					React.createElement("div", {className: "panel-content"}, 
						React.createElement(FormItem, {label: "登录帐号：", className: "col-xs-11"}, 
			            	React.createElement("span", null, myUser.userName)
			            ), 
			            React.createElement(FormItem, {label: "用户姓名：", className: "col-xs-11"}, 
			            	React.createElement("span", null, myUser.name)
			            ), 
			            React.createElement(FormItem, {label: "联系方式：", className: "col-xs-11"}, 
			            	React.createElement("span", null, myUser.mobilephone)
			            ), 
			            React.createElement(FormItem, {label: "电子邮箱：", className: "col-xs-11"}, 
			            	React.createElement("span", null, myUser.email)
			            )
					)
				), 
				React.createElement("div", {className: "panel"}, 
					React.createElement("div", {className: "panel-title fc-red"}, "密码修改"), 
					React.createElement("div", {className: "panel-content"}, 
						React.createElement(Form, {data: {userId: myUser.userId}, config: [{
		                	itemText: "原密码",
		                	itemClassName: "col-xs-11",
		                	panelType: Input,
		                	name: "oldPassword",
		                	type: "password"
		                }, {
							itemText: "用户名",
		                	itemClassName: "col-xs-11",
		                	panelType: Input,
		                	name: "userId",
							hidden:true
		                }, {
							itemText: "新密码",
							itemClassName: "col-xs-11",
							panelType: Input,
							name: "password",
							type: "password"
						}, {
		                	itemText: "确认新密码",
		                	itemClassName: "col-xs-11",
		                	panelType: Input,
		                	name: "newPassword",
		                	type: "password"
		                }], ref: "passwordForm"})
					)
				), 
				React.createElement("div", {className: "btn-panel"}, 
		            React.createElement(Button, {id: "save", buttonType: "bidnow", onClick: this._update}, "修改密码")
		        )
			)
	    );
	 }
});

/***/ }),

/***/ 59:
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(30);
var Left = __webpack_require__(32);
var Top = __webpack_require__(33);
var Home = __webpack_require__(31);
var MessageBox = __webpack_require__(6);
var ChildrenComponent = __webpack_require__(29);
var UserInfo = __webpack_require__(34);

var Root = React.createClass({displayName: "Root",
	getInitialState: function() {//react组件的初始化状态
		return ({
			centerPanel: Home
		});
	},
	_childrenComponent: [],
	componentDidMount: function() {
		resizeContent();// 处理滚动条

		var me = this;
		UcsmyIndex.alert = this.refs.messageBox.alert;
		UcsmyIndex.confirm = this.refs.messageBox.confirm;

		this._childrenComponent[0] = $(".content_component_sign")[0];
		UcsmyIndex.openChildrenPage = function(panel, fn) {
			$(".content_component_sign").hide();
			var component = document.createElement('div');
			component.className = "content_component_sign";
			$("#main_center_content_sign").append(component);
			var childrenComponent = React.createElement(ChildrenComponent, {panel: panel, successFn: fn});
			ReactDOM.render(childrenComponent, component);
			me._childrenComponent.push(component);
		};
		UcsmyIndex.closeChildrenPage = function() {
			UcsmyIndex.resetContentClassName();
			
			if(me._childrenComponent.length < 1)
				return;
			$(me._childrenComponent.pop()).remove();
			$(me._childrenComponent[me._childrenComponent.length - 1]).show();
		};
		UcsmyIndex.returnMainComponent = function() {
			UcsmyIndex.resetContentClassName();
			
			$(".content_component_sign").hide();
			var i = 0;
			var array = [];
			me._childrenComponent.map(function(data) {
				if(i == 0) {
					array[0] = data;
					$(data).show();
				} else {
					$(data).remove();
				}
				i++;
			});
			me._childrenComponent = array;
		};
	},
	_userInfo: function() {
		this.refs.left.select(-1);
		UcsmyIndex.returnMainComponent();
		this.setState({
			centerPanel: UserInfo
		});
	},
	_onClick: function(url, id) {
		var me = this;
		UcsmyIndex.loadComponent(url, function(component) {
			UcsmyIndex.returnMainComponent();
			me.refs.left.select(id);
			me.setState({
				centerPanel: component
			});
		}, function() {
			UcsmyIndex.alert("失败", "加载页面失败");
		});
	},
	render: function() {
		return (
				React.createElement("div", null, 
					React.createElement(Top, {onUserInfo: this._userInfo}), 
					React.createElement(Left, {ref: "left", onClick: this._onClick}), 
					React.createElement("div", {className: "content", id: "main_center_content_sign"}, 
						React.createElement("div", {className: "content_component_sign"}, 
							React.createElement(this.state.centerPanel, null)
						)
					), 
				    React.createElement("div", null, 
				    	React.createElement(MessageBox, {ref: "messageBox"})
				    )
				)
				);
	}
});
ReactDOM.render(React.createElement(Root, null), document.getElementById('main'));

/***/ }),

/***/ 6:
/***/ (function(module, exports) {

var Layer = UcsmyUI.Layer;
var Button = UcsmyUI.Button;
var MessageBox = React.createClass({displayName: "MessageBox",
	getInitialState: function(){
		return {
			isOpen: false,
			cancelButShow: true,
			title: 'title',
			msg: 'msg',
			okFn: function() {
			},
			cancelFn: function() {
			}
		}
	},
	alert: function(title, msg, okFn) {
		if(this.state.isOpen)
			this._layerClose();// 执行关闭操作，防止多次弹出阴影层
		okFn = okFn ? okFn : function(){};
		this.setState({
			isOpen: true,
			cancelButShow: false,
			title: title,
			msg: msg,
			okFn: okFn,
			cancelFn: function(){}
		});
		this._layerOpen();
	},
	confirm: function(title, msg, okFn, cancelFn){
		if(this.state.isOpen)
			this._layerClose();// 执行关闭操作，防止多次弹出阴影层
		okFn = okFn ? okFn : function(){};
		cancelFn = cancelFn ? cancelFn : function(){};
		this.setState({
			isOpen: true,
			cancelButShow: true,
			title: title,
			msg: msg,
			okFn: okFn,
			cancelFn: cancelFn
		});
		this._layerOpen();
	},
	_layerOpen: function() {
		this.refs.layerMessageBox.layerOpen();
		var background = $(".layer-background");
		$(background[background.length - 1]).addClass("layerMessageBoxBackground");
	},
	_layerClose: function() {
		this.setState({
			isOpen: false
		});
		this.refs.layerMessageBox.layerClose();
	},
	_okFn: function() {
		this._layerClose();
		this.state.okFn();
	},
	_cancelFn: function() {
		this._layerClose();
		this.state.cancelFn();
	},
	render: function() {
		return(
			React.createElement("div", null, 
				React.createElement(Layer, {ref: "layerMessageBox", className: "layerMessageBox", title: this.state.title, width: "350", showClose: false}, 
					React.createElement("label", null, this.state.msg), 
					React.createElement("br", null), 
					React.createElement("br", null), 
					React.createElement("div", {className: "ucs-layer-footer"}, 
						React.createElement(Button, {buttonType: "bidnow", onClick: this._okFn}, "确认"), 
		                this.state.cancelButShow ? React.createElement(Button, {buttonType: "cancel", onClick: this._cancelFn}, "取消") : ""
		            )
				)
			)
		)
	}
});

module.exports = MessageBox;

/***/ }),

/***/ 60:
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
                var leftIconReturn = '';
                var rightIconReturn = '';
                if (icons.left) {
                    leftIconReturn = ( React.createElement("i", {className: icons.left}) );
                }
                if (icons.right) {
                    rightIconReturn = ( React.createElement("i", {className: icons.right}) );
                }

//                if (icons) {
//                    var leftIconReturn = ( <i className={icons.left}></i> );
//                    var rightIconReturn = ( <i className={icons.right}></i> );
//                } else {
//                    var leftIconReturn = rightIconReturn = '';
//                }
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


/***/ })

/******/ });