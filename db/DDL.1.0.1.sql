--add by liujsh at 20170525

ALTER TABLE `ucas_client_token`
	CHANGE COLUMN `ACCESS_TOKEN` `ACCESS_TOKEN` VARCHAR(100) NULL DEFAULT NULL COMMENT '令牌token',
	CHANGE COLUMN `REFRESH_TOKEN` `REFRESH_TOKEN` VARCHAR(100) NULL DEFAULT NULL COMMENT '刷新令牌';

ALTER TABLE `ucas_call_ticket`
	CHANGE COLUMN `CALL_TICKET` `CALL_TICKET` VARCHAR(100) NOT NULL COMMENT '调用凭证ticket';

ALTER TABLE `ucas_client_token`
	CHANGE COLUMN `CODE` `CODE` VARCHAR(32) NULL DEFAULT NULL COMMENT '授权码';
	
-- end by liujsh at 20170525
   
   
-- add by leijinmin 2017年5月31日 10:22:17   加入唯一性约束
-- ALTER TABLE ucas_resource_group DROP INDEX GROUP_NAME; -- 删除用户表网金账号唯一约束  
ALTER TABLE ucas_resource_group add unique (`GROUP_NAME`); -- 添加资源群组表群组名称唯一约束

-- ALTER TABLE ucas_user_account DROP INDEX UCAS_ACCOUNT; -- 删除用户表网金账号唯一约束
ALTER TABLE ucas_user_account add unique (`UCAS_ACCOUNT`); -- 添加用户表网金账号唯一约束


-- ALTER TABLE ucas_user_account DROP INDEX MOBILE_ACCOUNT; -- 删除用户表邮箱账号唯一约束
ALTER TABLE ucas_user_account add unique  (`MOBILE_ACCOUNT`); -- 添加用户表手机账号唯一约束


-- ALTER TABLE ucas_user_account DROP INDEX EMAIL_ACCOUNT; -- 删除用户表邮箱账号唯一约束
ALTER TABLE ucas_user_account add unique  (`EMAIL_ACCOUNT`); -- 添加用户表邮箱账号唯一约束      

-- end by leijinmin at 2017年5月31日 10:22:17 --------

-- add by liujsh at 20170602 修改日志表日志信息字段长度
ALTER TABLE `manage_log_info`
	CHANGE COLUMN `message` `message` TEXT NULL DEFAULT NULL;
	
--end by liujsh at 20170602

-- add by wuchomg at 20170602 增加生成随机网金账号配置表
create table `ucas_serial_number` (
	`biz_tag` varchar(128) NOT NULL,
	`max_id` bigint (20) NOT NULL DEFAULT '0',
	`step` int (10) NOT NULL,
	`desc` varchar (128),
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`biz_tag`)
); 
	
--end by wuchomg at 20170602

