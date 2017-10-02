----------------------------------------------------
-- Export file for user GVT                       --
-- Created by Administrator on 2017/6/7, 14:47:57 --
----------------------------------------------------

spool cache_group_conf_s.log

prompt
prompt Creating table CACHE_GROUP_CONF
prompt ===============================
prompt
create table GVT.CACHE_GROUP_CONF
(
  ID          NUMBER(6) not null,
  GROUP_NAME  VARCHAR2(255),
  ENTRY_SIZE  NUMBER(6),
  MEMORY_SIZE NUMBER(6)
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
comment on table GVT.CACHE_GROUP_CONF
  is '缓存组属性配置';
comment on column GVT.CACHE_GROUP_CONF.ID
  is '主键';
comment on column GVT.CACHE_GROUP_CONF.GROUP_NAME
  is '组名';
comment on column GVT.CACHE_GROUP_CONF.ENTRY_SIZE
  is '条目数量';
comment on column GVT.CACHE_GROUP_CONF.MEMORY_SIZE
  is '内存大小';
alter table GVT.CACHE_GROUP_CONF
  add constraint GV_PK_CACHE_GROUP_CONF_ID primary key (ID)
  using index 
  tablespace CIS_TS
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
