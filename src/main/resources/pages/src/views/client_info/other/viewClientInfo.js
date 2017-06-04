var Form = require('../../widget/other/form');

var Input = UcsmyUI.Input;
var Button = UcsmyUI.Button;
var FormItem = UcsmyUI.Form.FormItem;
var Tooltip=UcsmyUI.Tooltip;
module.exports = React.createClass({
	getInitialState: function(){
		return {
			client:{},
            showReturn: true,
            showTokenStrategy: true,
            tokenStrategy: {},
            hasTokenStrategy: "0"
		}
	},
	init: function(data, showReturn, showTokenStrategy) {
		/* 是否展示返回按钮 */
		var showRet = true;
		if (showReturn != null) {
            showRet = showReturn;
		}
		/* 是否展示自定义Token策略 */
		var showTokenST = true;
		if (showTokenStrategy != null) {
			showTokenST = showTokenStrategy
		}
		this.setState({
			client: data,
            showReturn: showRet,
            showTokenStrategy: showTokenST
		}, () => {
            this._loadTokenStrategy(data.clientId);
        });
	},
	_return: function(event) {
		UcsmyIndex.closeChildrenPage();
	},
    _loadTokenStrategy: function (clientId) {
        if (clientId == null || clientId == '') {
            return;
        }
        var me = this;
        $.post("tokenStrategy/queryTokenStrategyByClientId", {clientId: clientId}, function(data) {
            if(data.retcode != 0) {
                return;
            }
            var tokenStrategy = {};
            var hasTokenStrategy = "0";
            if (data.data != null && data.data.length > 0) {
                tokenStrategy = data.data[0];
                hasTokenStrategy = "1";
            }
            me.setState({
                tokenStrategy: tokenStrategy,
                hasTokenStrategy: hasTokenStrategy
            });
        }, "json").error(function(xhr, errorText, errorType){
        });
    },
	render: function(){
		var returnBtn = [];
		if (this.state.showReturn != null && this.state.showReturn) {
            returnBtn.push(
				<div className="btn-panel">
					<Button buttonType="bidnow" onClick={this._return}>返回</Button>
				</div>
			)
		}
		var tokenStrategy = [];
		if (this.state.showTokenStrategy != null && this.state.showTokenStrategy && this.state.hasTokenStrategy == "1") {
            tokenStrategy.push(
				<div className="panel">
					<div className="panel-title fc-red">自定义Token策略</div>
					<div className="ucs-form-group">
						<span className="label">最大使用次数：</span>
						<span>{this.state.tokenStrategy.maxTimes}</span>
					</div>
					<div className="ucs-form-group">
						<span className="label" style={{'width': '120px'}}>策略ID：</span>
						<span>{this.state.tokenStrategy.uuid}</span>
					</div>
					<br/>
					<div className="ucs-form-group">
						<span className="label">Token时效：</span>
						<span>{this.state.tokenStrategy.expiryDate}</span>
					</div>
					<div className="ucs-form-group">
						<span className="label" style={{'width': '120px'}}>刷新Token时效：</span>
						<span>{this.state.tokenStrategy.refreshExpiryDate}</span>
					</div>
				</div>
            );
		}
		return (
			<div>
				<div className="panel panel-custom">
					<div className="panel-title fc-red">应用信息</div>
					<div className="ucs-form-group">
						<span className="label">应用简称：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.clientName}>
								{this.state.client.clientName}
							</Tooltip>
						</div>
					</div>
					<div className="ucs-form-group">
						<span className="label">应用ID：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.clientId}>
								{this.state.client.clientId}
							</Tooltip>
						</div>
					</div>
					<br/>
					<div className="ucs-form-group">
						<span className="label">应用密钥：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.clientSecret}>
								{this.state.client.clientSecret}
							</Tooltip>
						</div>
					</div>
					<div className="ucs-form-group">
						<span className="label">授权类型：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.grantType}>
								{this.state.client.grantType}
							</Tooltip>
					  </div>
					</div>
					<br/>
					<div className="ucs-form-group">
						<span className="label">应用组：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.ucasClientGroup?this.state.client.ucasClientGroup.groupName:''}>
								{this.state.client.ucasClientGroup?this.state.client.ucasClientGroup.groupName:''}
							</Tooltip>
						</div>
					</div>
					<div className="ucs-form-group">
						<span className="label">重定向URL：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.clientUrl}>
								{this.state.client.clientUrl}
							</Tooltip>
						</div>
					</div>
					<br/>
					<div className="ucs-form-group">
						<span className="label">应用状态：</span>
						<span>{this.state.client.status == '0' ? '可用' : '停用'}</span>
					</div>
					<div className="ucs-form-group">
						<span className="label">应用描述：</span>
						<div className="custom-tooltip">
							<Tooltip title={this.state.client.descRibe}>
								{this.state.client.descRibe}
							</Tooltip>
						</div>
					</div>
				</div>
				{tokenStrategy}
				{returnBtn}
			</div>
	    );
	 }
});