DELETE FROM ucas_client_resource WHERE RES_UUID='ff808081-5c433b3c-015c-43751391'; -- ɾ��
DELETE FROM ucas_client_resource WHERE RES_UUID='ff808081-5c433b3c-015c-437c8b24'; -- ɾ��

INSERT INTO ucas_client_resource (RES_UUID,RES_URI,RES_GROUP_UUID,DESC_RIBE,IS_PRIVACY,IS_DEFAULT,CREATE_DATE,MODIFY_DATE,STATUS) VALUES ('ff808081-5c433b3c-015c-43751391', '/oauth/resource/authToken', 'fb9d65a3bac341fbb6b8fa68d36b86a1', 'У��token��Ч��', '0', '0', NULL, '2017-5-26 14:39:33', '0');
INSERT INTO ucas_client_resource (RES_UUID,RES_URI,RES_GROUP_UUID,DESC_RIBE,IS_PRIVACY,IS_DEFAULT,CREATE_DATE,MODIFY_DATE,STATUS) VALUES ('ff808081-5c433b3c-015c-437c8b24', '/oauth/resource/getScopes', 'fb9d65a3bac341fbb6b8fa68d36b86a1', '��ȡ������Ȩ��client����Դ��', '0', '0', '2017-5-26 14:40:55', '2017-5-26 14:40:55', '0');



-- add by leijinming at 20170531 10:23:15

DELETE FROM manage_module WHERE module_id='100017052700000003'; -- ɾ����־��������

INSERT INTO manage_module(module_id,description,priority,url,parent_id,name,leaf)
VALUE('100017052700000003','ϵͳ�쳣��־',7,'pages/sysLog/index.js','2','��־����',0); -- �����־��������

-- end by leijinming at 20170531

-- add by wuchong at 20170602 16:33:15

insert into `ucas_serial_number` (`biz_tag`, `max_id`, `step`, `desc`) values('ucas_account','0','1000','������˺�');

-- end by wuchong at 20170602 16:33:15