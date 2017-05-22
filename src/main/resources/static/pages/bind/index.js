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
/******/ 	return __webpack_require__(__webpack_require__.s = 53);
/******/ })
/************************************************************************/
/******/ ({

/***/ 53:
/***/ (function(module, exports) {



//绑定用户
$("#bindAccount").click(function(){
	var username = $("#username").val();
	var password = $("#password").val();
	if(username ==null || username=="")
	{
		alert("用户名不能为空");
		return;
	}
	if(password ==null || password=="")
	{
		alert("密码不能为空");
		return ;
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


					var data = {username: username, password: enPassword,"token_type":"LOCAL"};
					$.post("login", data, function(data) {
						if(data.success == true) {
							window.location.href = ctx + "index";
						} else {
							alert(data.msg);
						}
					}, "json").error(function(xhr, errorText, errorType) {

						alert("异常", "网络异常");

					});
				},
				error:function(XMLHttpRequest, textStatus, errorThrown)
				{

					alert("异常", "网络异常");
					console.log(XMLHttpRequest);
					console.log(textStatus);
					console.log(errorThrown);
				}
			});













});

/***/ })

/******/ });