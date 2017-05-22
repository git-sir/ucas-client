

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