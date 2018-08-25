
-- admin/admin@2017
INSERT INTO sysuser(id, username, password, created, updated, accountexpired, accountlocked, credentialsexpired, accountenabled)
VALUES (nextval('user_id_seq'), 'admin', '$2a$10$3CZUTpbr6AGOOjAPgfqwheQA.WRISi7JHCBwSyfSSxVu5RjL6VnHO', NOW(),  NOW(), FALSE, FALSE, FALSE, FALSE);

INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_inquiry', NOW(), NOW(), '问诊管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_hospital', NOW(), NOW(), '医院管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_office', NOW(), NOW(), '科室管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_group', NOW(), NOW(), '小组管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_doctor', NOW(), NOW(), '医生管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_rotation', NOW(), NOW(), '轮播管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_manage', NOW(), NOW(), '管理员管理');
INSERT INTO "role_info" (authority_key, created, updated, label) VALUES ('role_patient', NOW(), NOW(), '患者管理');

-- Index seq
CREATE SEQUENCE const_index_seq  INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1  CACHE 1;
-- table constant
--性别
insert into constant(const_key, label, const_type, const_index) values ('male', '男', 'type_gender', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('female', '女', 'type_gender', nextval('const_index_seq'));

--表单类型
insert into constant(const_key, label, const_type, const_index) values ('form_illness', '病情表单', 'type_form', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('form_followup', '随访表单', 'type_form', nextval('const_index_seq'));

--问诊
insert into constant(const_key, label, const_type, const_index) values ('message', '留言问诊', 'type_inquiry', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('reservation', '电话预约', 'type_inquiry', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('subsequent', '留言复诊', 'type_inquiry', nextval('const_index_seq'));

--问诊状态
insert into constant(const_key, label, const_type, const_index) values ('editing', '正在编辑', 'type_inquiry_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('open', '未处理', 'type_inquiry_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('closed', '医生已回复', 'type_inquiry_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('reject', '客服回复', 'type_inquiry_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('agree', '客服同意', 'type_inquiry_status', nextval('const_index_seq'));
--小组类型
insert into constant(const_key, label, const_type, const_index) values ('commonweal', '公益', 'type_group', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('none_commonweal', '非公益', 'type_group', nextval('const_index_seq'));
--科室类型
insert into constant(const_key, label, const_type, const_index) values ('office_women', '妇科', 'type_office', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('office_child', '儿科', 'type_office', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('office_cardiothoracic', '心胸外科', 'type_office', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('office_followup', '随访', 'type_office', nextval('const_index_seq'));

--职称
insert into constant(const_key, label, const_type, const_index) values ('chief_physician', '主任医师', 'type_duty', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('associate_chief_physician', '副主任医师', 'type_duty', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('visiting_staff', '主治医师', 'type_duty', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('resident_doctor', '住院医师', 'type_duty', nextval('const_index_seq'));

--邀请加入小组状态
insert into constant(const_key, label, const_type, const_index) values ('invited', '已邀请', 'type_join_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('joined', '已加入', 'type_join_status', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('not_joined', '未加入', 'type_join_status', nextval('const_index_seq'));

--是否需要付费
insert into constant(const_key, label, const_type, const_index) values ('need_pay_yes', '需要付费', 'type_need_pay', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('need_pay_no', '不需付费', 'type_need_pay', nextval('const_index_seq'));

--付费状态
insert into constant(const_key, label, const_type, const_index) values ('pay_complete', '已付费', 'type_need_pay', nextval('const_index_seq'));
insert into constant(const_key, label, const_type, const_index) values ('pay_pending', '未付费', 'type_need_pay', nextval('const_index_seq'));