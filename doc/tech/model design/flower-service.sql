/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/12/30 20:25:47                          */
/*==============================================================*/


drop table if exists biz_board;

drop table if exists biz_category;

drop table if exists biz_file;

drop table if exists biz_pin;

drop table if exists biz_pin_comment_user;

drop table if exists biz_pins_like_user;

drop table if exists biz_user;

drop table if exists biz_user_follow_board;

drop table if exists biz_user_follow_user;

drop table if exists sys_dictionary;

/*==============================================================*/
/* Table: biz_board                                             */
/*==============================================================*/
create table biz_board
(
   id                   bigint not null auto_increment comment '����',
   title                varchar(100) comment '�û��Զ��廭�����',
   status               int comment '״̬��1�����ڣ�0��ɾ����',
   categoryId           bigint comment '�������',
   userId               bigint comment 'user �����',
   createTime           datetime comment '����ɼ�ʱ��',
   updateTime           timestamp comment '�����޸�ʱ��',
   primary key (id)
);

alter table biz_board comment '����';

/*==============================================================*/
/* Table: biz_category                                          */
/*==============================================================*/
create table biz_category
(
   id                   bigint not null auto_increment comment '����',
   title                varchar(100) comment '�û��Զ��廭�����',
   status               int comment '״̬��1�����ڣ�0��ɾ����',
   createTime           datetime comment '����ɼ�ʱ��',
   updateTime           timestamp comment '�����޸�ʱ��',
   comment              varchar(1000) comment '��ע',
   primary key (id)
);

alter table biz_category comment '�������';

/*==============================================================*/
/* Table: biz_file                                              */
/*==============================================================*/
create table biz_file
(
   id                   bigint not null auto_increment comment '����',
   width                int comment 'ͼƬ���',
   height               bigint comment 'ͼƬ�߶�',
   uuid                 varchar(60) comment 'ͼƬ����Ψһ��ʶ��',
   mime                 varchar(25) comment '�ļ����ͣ��磬image/jpeg; audio/basic; video/mpeg��',
   bucket               varchar(10) comment '�ļ�������������������',
   primary key (id)
);

alter table biz_file comment '�ɼ��ļ�����';

/*==============================================================*/
/* Table: biz_pin                                               */
/*==============================================================*/
create table biz_pin
(
   id                   bigint not null comment '����',
   title                varchar(100) comment '�û��Զ��廭�����',
   status               int comment '״̬��0�����ڣ�1��ɾ����',
   userId               bigint comment 'user �����',
   createTime           datetime comment '����ɼ�ʱ��',
   updateTime           timestamp comment '�����޸�ʱ��',
   boardId              bigint comment '�ɼ���������id',
   media_type           int comment 'ý�����ͣ�ͼƬ����Ƶ�ȣ��ֵ���壩',
   link                 varchar(500) comment '�ɼ���Դ��վҳ���ַ',
   origSource           varchar(500) comment '�ɼ��������Դ��ַ',
   fileId               bigint comment '�ļ�id',
   text                 varchar(250) comment '�ɼ�����',
   viaPinId             bigint comment 'ת��Id�����������ָ��pins���������',
   viaUserId            bigint comment '��˭����ɵģ�user ��id��������Ϣ�������ѯ��',
   primary key (id)
);

alter table biz_pin comment '�ɼ�����';

/*==============================================================*/
/* Table: biz_pin_comment_user                                  */
/*==============================================================*/
create table biz_pin_comment_user
(
   id                   bigint not null auto_increment comment '����',
   pinId                bigint comment '�ɼ�id��pins�����',
   content              varchar(250) comment '��������',
   authorId             bigint comment '������id��user �������',
   replyToUserId        bigint comment '�ظ��û�id',
   createTime           timestamp comment '����ʱ��',
   primary key (id)
);

alter table biz_pin_comment_user comment '�ɼ���������';

/*==============================================================*/
/* Table: biz_pins_like_user                                    */
/*==============================================================*/
create table biz_pins_like_user
(
   id                   bigint not null auto_increment comment '����',
   pinId                bigint comment '�ɼ�id��pins�����',
   authorId             bigint comment '���޲ɼ���������Id��user �������',
   userId               datetime comment '�����ˣ�user �������',
   createTime           timestamp comment '����ʱ��',
   primary key (id)
);

alter table biz_pins_like_user comment '�ɼ�������';

/*==============================================================*/
/* Table: biz_user                                              */
/*==============================================================*/
create table biz_user
(
   id                   bigint not null auto_increment comment '����',
   username             varchar(25) comment '�û�������¼�������������¼��',
   oAuthName            char(10) comment '���ÿ�����Ȩ��¼��ʽ��¼�ĵ�¼�������������ݵ�¼��',
   alias                varchar(25) comment '����',
   password             varchar(32) comment '��¼����',
   cityId               bigint comment '���ڳ��У�int��varchar����',
   about                varchar(250) comment '�����Լ�',
   avatarId             bigint comment 'ͷ���ļ���id',
   urlName              varchar(25) comment '������ַcontext',
   primary key (id)
);

alter table biz_user comment 'ע���û���';

/*==============================================================*/
/* Table: biz_user_follow_board                                 */
/*==============================================================*/
create table biz_user_follow_board
(
   id                   bigint not null auto_increment comment '����',
   biz_id               bigint comment '����',
   followerId           bigint comment '��ע���û�id',
   boardId              bigint comment '����ע����id',
   createTime           timestamp comment '��עʱ��',
   primary key (id)
);

alter table biz_user_follow_board comment '��ע -- ����';

/*==============================================================*/
/* Table: biz_user_follow_user                                  */
/*==============================================================*/
create table biz_user_follow_user
(
   id                   bigint not null auto_increment comment '����',
   followerId           bigint comment '��ע���û�id',
   beFollowerId         bigint comment '����ע���û�id',
   createTime           timestamp comment '��עʱ��',
   primary key (id)
);

alter table biz_user_follow_user comment '��ע -- ˭';

/*==============================================================*/
/* Table: sys_dictionary                                        */
/*==============================================================*/
create table sys_dictionary
(
   id                   bigint not null auto_increment comment '����',
   groupName            varchar(30) comment '�ֵ����ѯ��',
   groupNameDisp        char(10) comment '�ֵ�����ʾ��',
   dictKey              varchar(50) comment '����key',
   dictValue            varchar(100) comment '����value',
   dictDisp             char(10) comment '�ֵ���ʾֵ',
   rank                 int comment '��ʾ���ȼ�',
   status               tinyint comment '��������',
   content              text comment '����',
   pid                  bigint comment '��һ��ID',
   primary key (id)
);

alter table biz_board add constraint FK_board_category foreign key (categoryId)
      references biz_category (id);

alter table biz_board add constraint FK_board_user foreign key (userId)
      references biz_user (id);

alter table biz_pin add constraint FK_file_pin foreign key (fileId)
      references biz_file (id);

alter table biz_pin add constraint FK_pin_board foreign key (boardId)
      references biz_board (id);

alter table biz_pin_comment_user add constraint FK_comment_pin foreign key (pinId)
      references biz_pin (id);

alter table biz_pin_comment_user add constraint FK_comment_user foreign key (authorId)
      references biz_user (id) on delete restrict on update restrict;

alter table biz_pins_like_user add constraint FK_like_pin foreign key (pinId)
      references biz_pin (id);

alter table biz_pins_like_user add constraint FK_like_user foreign key (userId)
      references biz_user (id);

alter table biz_user_follow_board add constraint FK_follow_board_user foreign key (biz_id)
      references biz_user (id);

alter table biz_user_follow_board add constraint FK_followe_board foreign key (id)
      references biz_board (id);

alter table biz_user_follow_user add constraint FK_beFollow_user foreign key (beFollowerId)
      references biz_user (id);

alter table biz_user_follow_user add constraint FK_follow_user foreign key (followerId)
      references biz_user (id);

