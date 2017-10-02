-----------------------------------------------------
-- Export file for user GVT                     --
-- Created by Administrator on 2017/4/17, 17:51:05 --
-----------------------------------------------------

spool cache_policy_conf_s.log

prompt
prompt Creating table CACHE_POLICY_CONF
prompt ================================
prompt
create table GVT.CACHE_POLICY_CONF
(
  ID            NUMBER not null,
  CACHE_ID      VARCHAR2(255),
  CACHE_GROUP   VARCHAR2(255),
  CACHE_DESC    VARCHAR2(255),
  POLICY_TYPE   VARCHAR2(255),
  POLICY        VARCHAR2(2000),
  POLICY_STATUS NUMBER,
  CREATE_TIME   DATE,
  UPDATE_TIME   DATE,
  DB_TYPE       VARCHAR2(255),
  DB_INFO       VARCHAR2(2000)
)
tablespace TBS_GNT_CIS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table GVT.CACHE_POLICY_CONF
  is '缓存策略配置表';
comment on column GVT.CACHE_POLICY_CONF.ID
  is '主键';
comment on column GVT.CACHE_POLICY_CONF.CACHE_ID
  is '缓存ID';
comment on column GVT.CACHE_POLICY_CONF.CACHE_GROUP
  is '缓存组';
comment on column GVT.CACHE_POLICY_CONF.CACHE_DESC
  is '描述';
comment on column GVT.CACHE_POLICY_CONF.POLICY_TYPE
  is '策略类型：sql,sql_row,table,class';
comment on column GVT.CACHE_POLICY_CONF.POLICY
  is '策略内容';
comment on column GVT.CACHE_POLICY_CONF.POLICY_STATUS
  is '状态：1有效，0无效';
comment on column GVT.CACHE_POLICY_CONF.CREATE_TIME
  is '创建时间';
comment on column GVT.CACHE_POLICY_CONF.UPDATE_TIME
  is '修改时间';
comment on column GVT.CACHE_POLICY_CONF.DB_TYPE
  is '数据库类型：1 mysql，2 oracle';
comment on column GVT.CACHE_POLICY_CONF.DB_INFO
  is '数据库信息：key=value#key=value';
alter table GVT.CACHE_POLICY_CONF
  add constraint GV_PK_POLICY_CONF_ID primary key (ID)
  using index 
  tablespace TBS_GNT_CIS_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


spool off



prompt PL/SQL Developer import file
prompt Created on 2017年4月17日 by Administrator
set feedback off
set define off
prompt Loading CACHE_POLICY_CONF...
insert into CACHE_POLICY_CONF (ID, CACHE_ID, CACHE_GROUP, CACHE_DESC, POLICY_TYPE, POLICY, POLICY_STATUS, CREATE_TIME, UPDATE_TIME, DB_TYPE, DB_INFO)
values (1, 'ZZZ.CACHE_POLICY_CONF', 'CIS', '缓存配置表信息', 'table', 'SELECT * FROM CACHE_POLICY_CONF', 0, to_date('17-04-2017 17:41:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-04-2017 17:41:58', 'dd-mm-yyyy hh24:mi:ss'), '1', 'driver=com.mysql.jdbc.Driver#url=jdbc:mysql://localhost:3306/zzz?useUnicode=true&characterEncoding=UTF-8#username=root#password=admin');
commit;
prompt 1 records loaded
set feedback on
set define on
prompt Done.
