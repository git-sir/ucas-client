var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var Form = UcsmyUI.Form;
var FormItem = Form.FormItem;
var SelectDropDown = UcsmyUI.SelectDropDown;
var Tooltip=UcsmyUI.Tooltip;
var commonMap = {"0":"否","1":"是"};

//var statusMap = {"0":"正常","1":"停用"};

module.exports = React.createClass({
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
			<div>
				<div className="panel">
					<div className="panel-title fc-red">{this.state.title}</div>
					<div className="panel-content">
						<div className="ucs-form-group">
							<span className="label">应用简称：</span>
							<div className="custom-tooltip">
								<Tooltip title={this.state.clientResource.clientName}>
									{this.state.clientResource.clientName}
								</Tooltip>
							</div>
						</div>
						<div className="ucs-form-group">
							<span className="label">资源组：</span>
							<div className="custom-tooltip">
								<Tooltip title={this.state.clientResource.groupName}>
									{this.state.clientResource.groupName}
								</Tooltip>
							</div>
						</div>
						<div className="ucs-form-group">
							<span className="label">资源描述：</span>
							<div className="custom-tooltip">
								<Tooltip title={this.state.clientResource.descRibe}>
									{this.state.clientResource.descRibe}
								</Tooltip>
							</div>
						</div>
						<div className="ucs-form-group">
							<span className="label">资源URI：</span>
							<div className="custom-tooltip">
								<Tooltip title={this.state.clientResource.resUri}>
									{this.state.clientResource.resUri}
								</Tooltip>
							</div>
						</div>
						<div className="ucs-form-group">
							<span className="label">涉及用户隐私：</span>
							<span>{commonMap[this.state.clientResource.isPrivacy]}</span>
						</div>
						<div className="ucs-form-group">
							<span className="label" style={{width:"120px"}}>资源组必须授权：</span>
							<span>{commonMap[this.state.clientResource.isDefault]}</span>
						</div>
						{/*<div className="ucs-form-group">
							<span className="label">资源状态：</span>
							<span>{statusMap[this.state.clientResource.status]}</span>
						</div>*/}
					</div>
				</div>
				<div className="btn-panel">
					<Button buttonType="cancel" onClick={this._return}>返回</Button>
				</div>
			</div>
		);
	}
});