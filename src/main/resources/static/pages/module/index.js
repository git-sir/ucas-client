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
/******/ 	return __webpack_require__(__webpack_require__.s = 62);
/******/ })
/************************************************************************/
/******/ ({

/***/ 35:
/***/ (function(module, exports) {

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var configFormData = {

	    "name": [
	        {type: "required", msg: "菜单名称不能为空"},
	        {type : "maxlength", maxlength : 32, msg : "菜单名称长度不能大于32"}
	    ],
		"url": [
			{type : "maxlength", maxlength : 245, msg : "响应地址长度不能大于245"}
		],
	    "priority": [
	        {type: "required", msg: "优先级不能为空"},
	        {type: "fn", validator: function(value){
                var m = /^\d+$/;
                return m.test(value);
	        }, msg: '优先级必须为大于等于0的整数'
	        },
            {type : "maxlength", maxlength : 9, msg : "优先级长度不能大于9"}
	    ],
	    "description": [
		        {type : "maxlength", maxlength : 256, msg : "描述长度不能大于256"}
		    ],
	   "image": [
		{type : "maxlength", maxlength : 64, msg : "图标长度不能大于64"}
	]

	};

module.exports = React.createClass({displayName: "module.exports",
    getInitialState: function() {
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
            "name", "url", "priority","description","image"
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
			$.post(me.state.url, $('#saveForm').serialize(), function(data) {
                _removeButtonDisabled('save');
				if(data.retcode=='0') {
					UcsmyIndex.alert("成功", data.retmsg);
					me.state.successFn();
					me._return();
				} else {
					UcsmyIndex.alert("失败", data.retmsg);
				}
			}, "json").error(function(){
                _removeButtonDisabled('save');
				UcsmyIndex.alert("失败", "网络异常");
			});

		});
	},
	_return: function() {
		UcsmyIndex.closeChildrenPage();
	},
    render:function(){
    	return(
			React.createElement("div", null, 
				React.createElement("div", {className: "panel"}, 
	                React.createElement("div", {className: "panel-title fc-red"}, this.state.title), 
	                React.createElement("div", {className: "panel-content"}, 
	                
	                
                React.createElement(Form, {ref: "saveForm", formData: configFormData, id: "saveForm"}, 
                    React.createElement("input", {type: "hidden", name: "id", value: this.state.data.id}), 
                    React.createElement("input", {type: "hidden", name: "parentId", value: this.state.data.parentId}), 
                    React.createElement(FormItem, {label: "菜单名称"}, React.createElement(Input, {name: "name", value: this.state.data.name})), 
                    React.createElement(FormItem, {label: "描述"}, React.createElement(Input, {name: "description", value: this.state.data.description})), 
                    React.createElement(FormItem, {label: "优先级"}, React.createElement(Input, {name: "priority", value: this.state.data.priority})), 
                    React.createElement(FormItem, {label: "响应地址"}, React.createElement(Input, {name: "url", value: this.state.data.url})), 
		           React.createElement(FormItem, {label: "指定图标"}, React.createElement(Input, {name: "image", value: this.state.data.image}))
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

/***/ 62:
/***/ (function(module, exports, __webpack_require__) {

var TreeTable = UcsmyUI.TreeTable;
var FormPanel = __webpack_require__(35);
var FormItem = UcsmyUI.Form.FormItem;
myPanel = React.createClass({displayName: "myPanel",
    getInitialState: function () {//react组件的初始化状态
        return {
            column: [
                {name: '名称', field: 'name', headerClass: 'header-blank'},
                {name: '描述', field: 'description', headerClass: 'header-blank'},
                {name: '优先级', field: 'priority', headerClass: 'header-blank'},
                {name: '响应地址', field: 'url', headerClass: 'header-blank'},
                {name: '图标', field: 'image',  headerClass:'header-blank'},
                {name: '操作', field: 'cz', headerClass: 'header-blank', setContent: this.operatorValue}
            ],
            treeData: ""
        };
    },
    componentDidMount: function () {
        this._load();
    },
    operatorValue: function (item) {
        var astr = [];
        astr.push(React.createElement("span", null, React.createElement("a", {href: "Javascript:void(0);", 
                           onClick: this._update.bind(this, item)}, "修改"), "   "));
        astr.push(React.createElement("span", null, React.createElement("a", {href: "Javascript:void(0);", 
                           onClick: this._add.bind(this, item)}, "新增子节点"), "   "));
        astr.push(React.createElement("span", null, React.createElement("a", {href: "Javascript:void(0);", onClick: this._delete.bind(this, item)}, "删除")));
        return astr;
    },
    _update: function (item) {
        var t = this;
        UcsmyIndex.openChildrenPage(FormPanel, function (refPanel) {
            refPanel.open('修改菜单', 'module/update', item, function () {
                t._load();
            });
        });
    },
    _add: function (item) {
        var data = {"parentId": item.id};
        var me = this;
        UcsmyIndex.openChildrenPage(FormPanel, function (refPanel) {
            refPanel.open('添加菜单', 'module/add', data, function () {
                me._load();
            });
        });
    },
    _delete: function (item) {
        var me = this;
        UcsmyIndex.confirm("确定", "是否确定删除该菜单？", function () {
            $.post("module/delete", {"id": item.id}, function (data) {
                if (data.retcode == "0") {
                    UcsmyIndex.alert("消息", data.retmsg);
                    me._load();
                } else {
                    UcsmyIndex.alert("异常消息", data.retmsg);
                }
            }, "json").error(function (xhr, errorText, errorType) {
                UcsmyIndex.alert("失败", "网络加载错误，请稍后再试");
            });
        });
    },
    /**
     * 只能有一个根节点的json树对象
     * 如果有多个根节点或没有数据会报错
     */
    _load: function () {
        var that = this;
        $.post("module/list", function (data) {
            if (data.retcode != '0') {
                UcsmyIndex.alert("失败", data.retmsg);
            } else {
                that.setState({
                    treeData: data.data[0]
                });
            }
        }, "json").error(function (xhr, errorText, errorType) {
            UcsmyIndex.alert("失败", "网络异常");
        });
    },
    render: function () {
        var that = this;
        return (
            React.createElement("div", null, 
                React.createElement("h2", {className: "content-title"}, "菜单管理"), 
                React.createElement("div", {className: "table-panel"}, 
                    React.createElement(TreeTable, {data: that.state.treeData, id: "tableData", column: that.state.column})
                )
            )
        );
    }
});

/***/ })

/******/ });