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
/******/ 	return __webpack_require__(__webpack_require__.s = 58);
/******/ })
/************************************************************************/
/******/ ({

/***/ 58:
/***/ (function(module, exports, __webpack_require__) {

var MessageBox = __webpack_require__(6);
var Input=UcsmyUI.Input;
var Button=UcsmyUI.Button;

var baeVerificationUrl = "login.login.pgflow/captcha?random=";
var _login = {
	disabled: false,
	text: '登录'
};

var Root=React.createClass({displayName: "Root",
	getInitialState: function(){
		return {
			login: _login,
			verificationUrl: baeVerificationUrl + Math.random()
		}
	},
	componentDidMount: function(){
		var me = this;
		$(".login").keydown(function(event) { 
			if(window.event.keyCode == 13) {
				me._click();
				$("#messageBox").focus();
			}
		});
	},
	_click:function(){
		var me = this;
		var userName = this.refs.userName.getValue();
		var password = this.refs.password.getValue();
		if(!userName || userName == ""){
			me.refs.messageBox.alert("失败", "用户名不能为空");
			return;
		}

		
		$.ajax({
			url:'getRsa',
			type:'GET',
			success:function(data)
			{
				var rsaKey = new RSAKey();
				//var userTypeValue;
				rsaKey.setPublic(b64tohex(data.data.modulus), b64tohex(data.data.exponent));
				var enPassword = hex2b64(rsaKey.encrypt(password));
				var loginType = data.data.loginType;

				if(loginType!=null&&loginType=="local"){
					var data = {username: userName, password: enPassword,"token_type":"LOCAL"};
//				data["_csrf"] = document.getElementsByTagName('meta')['_csrf'].getAttribute("content");
					me.setState({
						login: {
							disabled: true,
							text: '登录中...'
						}
					});


					$.post("login", data, function(data) {
						me.setState({
							login: _login
						});
						if(data.success == true) {
							window.location.href = ctx + "index";
						} else {
							me.refs.messageBox.alert("失败", data.msg);
						}
					}, "json").error(function(xhr, errorText, errorType) {
						me.setState({
							login: _login
						});
						if(xhr.status == 444) {
							me.refs.messageBox.confirm("登录异常", "登录页面信息失效，是否刷新页面？", function() {
								window.location.reload();
							});
						} else {
							me.refs.messageBox.alert("异常", "网络异常");
						}
					});
				}else {
					var oauth2Url = data.data.oauth2Url;
					if(oauth2Url==null){
						oauth2Url = "http://localhost/oauth/authorize?client_id=rdpdemo&response_type=code&redirect_uri="
					}
					var redirect_uri = ctx + "index";
					window.location.href = oauth2Url+redirect_uri;
				}


				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown)
			{
				
				me.refs.messageBox.alert("异常", "网络异常");
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			}
		});




	},
	testClick:function(){
		var me = this;
		var userName = this.refs.userName.getValue();
		var password = this.refs.password.getValue();
		if(!userName || userName == ""){
			me.refs.messageBox.alert("失败", "用户名不能为空");
			return;
		}
		var data = {username: userName, password: password,"token_type":"LOCAL"};
		$.post("login", data, function(data) {
			me.setState({
				login: _login
			});
			if(data.success == true) {
				window.location.href = ctx + "index";
			} else {
				me.refs.messageBox.alert("失败", data.msg);
			}
		}, "json").error(function(xhr, errorText, errorType) {
			me.setState({
				login: _login
			});
			if(xhr.status == 444) {
				me.refs.messageBox.confirm("登录异常", "登录页面信息失效，是否刷新页面？", function() {
					window.location.reload();
				});
			} else {
				me.refs.messageBox.alert("异常", "网络异常");
			}
		})

	},
	_otherClick:function(){

		var   oauth2Url = "http://localhost/oauth/authorize?client_id=rdpdemo&response_type=code&redirect_uri="
		var redirect_uri = ctx + "login.login.pgflow/index";
		window.location.href = oauth2Url+redirect_uri;
	
},

	handleKeyPress: function(e) {
		console.log(e);
	},
    render:function(){
    	return (
	    React.createElement("div", {className: "login"}, 
	        React.createElement("div", {className: "logo"}, 
	            React.createElement("img", {src: "images/logo-white.png"}), 
	            React.createElement("span", {className: "sub-logo"}, "统一认证平台")
	        ), 
	        React.createElement("div", {className: "box"}, 
	            React.createElement("div", {className: "box-layer"}), 
	            React.createElement("div", {className: "box-outer"}, 
	            	React.createElement("form", null, 
	                React.createElement("div", {className: "box-inner"}, 
	                    React.createElement("div", {className: "face"}, 
	                        React.createElement("div", {className: "wangjin-icon-wrap"}, 
	                            React.createElement("img", {src: "images/wangjin-icon.png"})
	                        )
	                    ), 
	                    React.createElement("dl", null, 
	                        React.createElement("dt", null, 
	                            React.createElement("span", null, "用户登录"), 
	                            React.createElement("i", {className: "l-line"}, " "), 
	                            React.createElement("i", {className: "r-line"}, " ")
	                        ), 
	                        React.createElement("dd", null, 
	                            React.createElement("i", {className: "icon"}, React.createElement("b", {className: "iconfont icon-user-icon"}, " ")), 
	                            React.createElement(Input, {placeholder: "请输入您的用户名", ref: "userName"})
	                        ), 
	                        React.createElement("dd", null, 
	                            React.createElement("i", {className: "icon"}, React.createElement("b", {className: "iconfont icon-lock-icon"}, " ")), 
	                            React.createElement(Input, {type: "password", placeholder: "请输入您的用户密码", ref: "password"})
	                        ), 
	                        React.createElement("dd", null, React.createElement(Button, {onClick: this._click, disabled: this.state.login.disabled}, this.state.login.text))

	                    )
	                    /*<div className="forget"><a href="#">忘记密码？</a></div>*/
	                )
	                )
	            )
	        ), 
	        React.createElement("img", {src: "images/login-bg.png", className: "login-bg"}), 
	        React.createElement(MessageBox, {ref: "messageBox", id: "messageBox"})
	    ));
    }
});
ReactDOM.render(React.createElement(Root, null),document.getElementById('main'));

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

/***/ })

/******/ });