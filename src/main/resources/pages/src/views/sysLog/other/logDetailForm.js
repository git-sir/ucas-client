
var Button = UcsmyUI.Button;
var Tooltip=UcsmyUI.Tooltip;
var Textarea = UcsmyUI.Textarea;
module.exports = React.createClass({
    getInitialState: function () {
        return {
            data: {},
        }
    },

    init: function (data) {
        var me = this;
            me.setState({
                data: data
            });
    },
    _return: function () {
        UcsmyIndex.closeChildrenPage();
    },
    render: function () {
        return (
            <div>
            <div className="panel panel-log">
            <div className="panel-title fc-red">日志信息</div>
         <div className="panel-content">

            <div className="ucs-form-group">
               <span className="label">日间：</span>
               <span>{this.state.data.createTime}</span>
           </div>
        <div className="ucs-form-group">
            <span className="label">IP地址：</span>
        <span>{this.state.data.ipAddress}</span>
        </div>
        <div className="ucs-form-group">
            <span className="label">类型：</span>
        <span>{this.state.data.logLevel}</span>
        </div>
        <div className="ucs-form-group">
            <span className="label">操作人：</span>
        <span>{this.state.data.userName}</span>
        </div>


        <div className="ucs-form-group">
            <span className="label">异常信息：</span>

            {/*<span>{this.state.ucasAccountGroup.orgName}</span>*/}
            {/*            <div className="custom-tooltip">
                <Tooltip title={this.state.data.message}>
                    {this.state.data.message}
                </Tooltip>
            </div>*/}
            <Textarea value={this.state.data.message} disabled="true" rows="10" cols="50">

            </Textarea>
        </div>


        </div>
        </div>
        <div className="btn-panel">

        <Button buttonType="bidnow" onClick={this._return}>返回</Button>
        </div>
        </div>
        )
    }
});