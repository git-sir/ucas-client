var Grid = require('../widget/other/grid');
var PermissionLink = require('../widget/other/permissionLink');
var LogDetailForm = require('./other/logDetailForm');
var DatePicker = UcsmyUI.DatePicker;
var Button = UcsmyUI.Button;
myPanel = React.createClass({
	getInitialState:function () {
		return{
			urgentFlag:'',
			data:{},
		}
	},
	_detail:function(column)
	{

		UcsmyIndex.openChildrenPage(LogDetailForm, function(refPanel) {
			refPanel.init(column);
		});

	},
	formatDate: function(date,fmt) {
		var o = {
			"M+": date.getMonth() + 1, //月份
			"d+": date.getDate(), //日
			"h+": date.getHours(), //小时
			"m+": date.getMinutes(), //分
			"s+": date.getSeconds(), //秒
			"q+": Math.floor((date.getMonth() + 3) / 3), //季度
			"S": date.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	},
	_getDate:function(dataTime)
	{
		var date = new Date(dataTime);
		return date;
	},
	_query:function()
	{
		var startDate = this.refs.startDate.getValue();
		var endDate = this.refs.endDate.getValue();
		if(null!==startDate && ""!==startDate && null!==endDate && ""!==endDate)
		{
              var star = this._getDate(startDate);
			 var end = this._getDate(endDate);
			if(star.getTime()>=end.getTime())
			{
				UcsmyIndex.alert("提示", "开始时间不能大于等于结束时间");
				return;
			}
		}

		this.refs.grid.load({
			startDate: startDate,
			endDate: endDate
		});
	},
	render: function() {
		var me = this;
		return (
			<div>
				<h2 className="content-title">日志管理</h2>
				<div className="panel">
					<div className="panel-title">查询条件</div>
					<div className="panel-content">
                    <DatePicker ref="startDate" name="startDate" format="yyyy-mm-dd" time="true" />
                     至
					<DatePicker ref="endDate" name="endDate" format="yyyy-mm-dd" time="true"/>
					</div>
				</div>
				<div className="btn-panel">
					<Button buttonType="bidnow" onClick={this._query}>查询</Button>
				</div>
				<div className="table-panel">
					<Grid
						url="manageLog/query" ref = "grid"
						isTextOverflowHidden={true}
						columns={[
		                   {
          						name: 'logLevel', header: '类型'
          					},
							{
							name: 'ipAddress', header: 'ip地址'
							},
          					{
          						name: 'userName', header: '操作人'
          					},
         				    {
          						name: 'createTime', header: '时间',content:function(column){
          				    		 return (<span>
          				    		 	{me.formatDate(new Date(column.createTime),"yyyy-MM-dd hh:mm:ss")}
          				    		 </span>)
          						}
          					},
		                   {
          						name: 'cz',
          						header: '操作',
          						content:function(column){
          							return (<span>
				                       <PermissionLink  href="Javascript:void(0);" onClick={me._detail.bind(this, column)}>详情&nbsp;&nbsp;&nbsp;</PermissionLink>
				                   </span>)
          				    	 }
          					}
          				]}
					/>
					<div className="clearfix"></div>
				</div>
			</div>
		);
	}
});