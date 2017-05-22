/**
 * Created by chenqilin on 2017/5/4.
 */
var Button = UcsmyUI.Button;

module.exports = React.createClass({

    getInitialState: function () {
        return {
            group: {}
        }
    },

    init: function(data) {
        this.setState({
            group: data
        });
    },

    _return: function() {
        UcsmyIndex.closeChildrenPage();
    },

    render: function(){
        return (
            <div>
                <div className="panel">
                    <div className="panel-title fc-red">资源组信息</div>
                    <div className="ucs-form-group">
                        <span className="label">应用简称：</span>
                        <span>{this.state.group.clientName}</span>
                    </div>
                    <div className="ucs-form-group">
                        <span className="label">所属应用id：</span>
                        <span>{this.state.group.clientId}</span>
                    </div>
                    <br/>
                    <div className="ucs-form-group">
                        <span className="label">资源组名称：</span>
                        <span>{this.state.group.groupName}</span>
                    </div>
                    <div className="ucs-form-group">
                        <span className="label">资源组UUID：</span>
                        <span>{this.state.group.resGroupUuid}</span>
                    </div>
                    <br/>
                    <div className="ucs-form-group">
                        <span className="label">资源组描述：</span>
                        <span>{this.state.group.descRibe}</span>
                    </div>
                </div>
                <div className="btn-panel">
                    <Button buttonType="bidnow" onClick={this._return}>返回</Button>
                </div>
            </div>
        )
    }
});