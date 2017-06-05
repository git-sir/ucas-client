--add by liujsh at 20170525

ALTER TABLE `ucas_client_token`
	CHANGE COLUMN `ACCESS_TOKEN` `ACCESS_TOKEN` VARCHAR(100) NULL DEFAULT NULL COMMENT '����token',
	CHANGE COLUMN `REFRESH_TOKEN` `REFRESH_TOKEN` VARCHAR(100) NULL DEFAULT NULL COMMENT 'ˢ������';

ALTER TABLE `ucas_call_ticket`
	CHANGE COLUMN `CALL_TICKET` `CALL_TICKET` VARCHAR(100) NOT NULL COMMENT '����ƾ֤ticket';

ALTER TABLE `ucas_client_token`
	CHANGE COLUMN `CODE` `CODE` VARCHAR(32) NULL DEFAULT NULL COMMENT '��Ȩ��';
	
-- end by liujsh at 20170525
   
   
-- add by leijinmin 2017��5��31�� 10:22:17   ����Ψһ��Լ��
-- ALTER TABLE ucas_resource_group DROP INDEX GROUP_NAME; -- ɾ���û��������˺�ΨһԼ��  
ALTER TABLE ucas_resource_group add unique (`GROUP_NAME`); -- �����ԴȺ���Ⱥ������ΨһԼ��

-- ALTER TABLE ucas_user_account DROP INDEX UCAS_ACCOUNT; -- ɾ���û��������˺�ΨһԼ��
ALTER TABLE ucas_user_account add unique (`UCAS_ACCOUNT`); -- ����û��������˺�ΨһԼ��


-- ALTER TABLE ucas_user_account DROP INDEX MOBILE_ACCOUNT; -- ɾ���û��������˺�ΨһԼ��
ALTER TABLE ucas_user_account add unique  (`MOBILE_ACCOUNT`); -- ����û����ֻ��˺�ΨһԼ��


-- ALTER TABLE ucas_user_account DROP INDEX EMAIL_ACCOUNT; -- ɾ���û��������˺�ΨһԼ��
ALTER TABLE ucas_user_account add unique  (`EMAIL_ACCOUNT`); -- ����û��������˺�ΨһԼ��      

-- end by leijinmin at 2017��5��31�� 10:22:17 --------

-- add by liujsh at 20170602 �޸���־����־��Ϣ�ֶγ���
ALTER TABLE `manage_log_info`
	CHANGE COLUMN `message` `message` TEXT NULL DEFAULT NULL;
	
--end by liujsh at 20170602

-- add by wuchomg at 20170602 ����������������˺����ñ�
create table `ucas_serial_number` (
	`biz_tag` varchar(128) NOT NULL,
	`max_id` bigint (20) NOT NULL DEFAULT '0',
	`step` int (10) NOT NULL,
	`desc` varchar (128),
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`biz_tag`)
); 
	
--end by wuchomg at 20170602

