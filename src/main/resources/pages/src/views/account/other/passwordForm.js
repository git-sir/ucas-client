var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;

var p;
var configFormData = {

	"password": [
		{type: "required", msg: "密码不能为空"},
		{type: "fn", validator: function(value){
			  p=value;
			  return true;
		}, msg: ''
		}
	],
	"repassword": [
		{type: "required", msg: "确认密码不能为空"},
		{type: "fn", validator: function(value){
			if(p!=value) {

				return false;
			}else return true;
		}, msg: '确认密码不一致'
		}
	]

};

module.exports = React.createClass({
	getInitialState: function () {
		return {
			title: '',
			callback: function(){},
			ucasAccount: {}
		}
	},
	_validate: function (callback) {
		var status = false;
		var validateField = [
			"password","repassword"
		];
		var fn = function (result) {
			if(result) {
				callback();
			}
		};

		this.refs.saveForm.validate(fn, validateField);

		return status;
	},
	_saveOrUpdate: function () {

		var me = this;
		this._validate(function(){
			_addButtonDisabled('save');
			$.post("account/upPassword",
				$('#saveForm').serialize(),
				function (result) {
					if (result && result.retcode && result.retcode == "0") {
						UcsmyIndex.alert("提示", result.retmsg);
						UcsmyIndex.closeChildrenPage();
						me.state.callback();
					} else {
						UcsmyIndex.alert("提示",result.retmsg);
					}
					_removeButtonDisabled('save');
				}).error(function(xhr, errorText, errorType){
				UcsmyIndex.alert("失败", "网络异常");
				_removeButtonDisabled('save');
			});
		});
	},
	init: function (data) {
		var me = this;
		this.setState({
			title: '密码修改',
			ucasAccount: data
		});
		// this.refs.saveForm.setValues(data);
	},
	_return: function (event) {
		UcsmyIndex.closeChildrenPage();
	},
	render: function () {
		return (
			<div>
			<div className="panel">
			<div className="panel-title fc-red">{this.state.title}</div>
				<div className="ucs-form-group">
					<span className="label">网金帐号：</span>
					<span>{this.state.ucasAccount.ucasAccount}</span>
				</div>
		<div className="panel-content">
			<Form ref="saveForm" formData={configFormData} id="saveForm">
			<input type="hidden" name="ucasAccount" value={this.state.ucasAccount.ucasAccount}/>
		<FormItem label="新密码"><Input type="password" name="password" /></FormItem>
		<FormItem label="确认密码"><Input type="password"name="repassword"  /></FormItem>
			</Form>
			</div>
			</div>
			<div className="btn-panel">
			<Button buttonType="bidnow" id="save" onClick={this._saveOrUpdate}>保存</Button>
		<Button buttonType="cancel" onClick={this._return}>取消</Button>
		</div>
		</div>
		)
	}
});