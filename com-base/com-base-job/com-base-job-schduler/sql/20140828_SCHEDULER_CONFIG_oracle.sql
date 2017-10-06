declare L_X number;       
    begin       
    select count(1) into L_X from user_tables where table_name= 'SCHEDULER_CONFIG';    
    if L_X = 0 then  
      execute immediate '
-- Create table
create table SCHEDULER_CONFIG
(
  JOB_ID           VARCHAR2(64) not null,
  TRIGGER_ID       VARCHAR2(64) not null,
  GROUP_ID         VARCHAR2(64) not null,
  JOB_CLASS        VARCHAR2(256) not null,
  CRON             VARCHAR2(64) not null,
  DES             VARCHAR2(256) ,
  STATUS             CHAR(1) default 1,
  RUN_GROUP          VARCHAR(32) default ''RUN'',
  RUN_PROPS          VARCHAR(4000),
  UPDATE_TIME        DATE,
  INSERT_TIME        DATE
)
tablespace TBS_CONFIG_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  )
';
    end if;
    if L_X = 0 then
      execute immediate '
-- Create/Recreate primary, unique and foreign key constraints 
alter table SCHEDULER_CONFIG
  add constraint PK_SCHEDULER_CONFIG primary key (JOB_ID, TRIGGER_ID, GROUP_ID)
  using index 
  tablespace TBS_CONFIG_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  )
';
    end if;
end;
/

-- Add comments to the table 
comment on table SCHEDULER_CONFIG is '定时任务配置表';
-- Add comments to the columns 
comment on column SCHEDULER_CONFIG.JOB_ID is '定时任务ID';
comment on column SCHEDULER_CONFIG.TRIGGER_ID is '触发ID';
comment on column SCHEDULER_CONFIG.GROUP_ID is '分组ID';
comment on column SCHEDULER_CONFIG.JOB_CLASS is '任务类';
comment on column SCHEDULER_CONFIG.CRON is '任务执行周期，整数或cron格式';
comment on column SCHEDULER_CONFIG.STATUS is '配置状态1有效0无效';
comment on column SCHEDULER_CONFIG.DES is '任务描叙';
comment on column SCHEDULER_CONFIG.INSERT_TIME is '插入时间';
comment on column SCHEDULER_CONFIG.UPDATE_TIME is '更新时间';
comment on column SCHEDULER_CONFIG.RUN_GROUP is '运行分组';
comment on column SCHEDULER_CONFIG.RUN_PROPS is '运行参数';
