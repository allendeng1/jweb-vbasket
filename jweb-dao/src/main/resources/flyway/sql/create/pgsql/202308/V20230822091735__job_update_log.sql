CREATE TABLE IF NOT EXISTS job_update_log  (
  id bigint NOT NULL PRIMARY KEY,
  task_id bigint NOT NULL,
  task_no varchar(30)  NOT NULL,
  task_name varchar(30)  NOT NULL,
  type int2 NOT NULL,
  admin_user_id bigint NOT NULL,
  remark varchar(100) NULL ,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL

);

COMMENT ON TABLE job_update_log IS '任务修改日志';
COMMENT ON COLUMN job_update_log.task_id IS '任务ID';
COMMENT ON COLUMN job_update_log.task_no IS '任务编号';
COMMENT ON COLUMN job_update_log.task_name IS '任务名';
COMMENT ON COLUMN job_update_log.type IS '修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息';
COMMENT ON COLUMN job_update_log.admin_user_id IS '操作员ID';
COMMENT ON COLUMN job_update_log.remark IS '备注';
COMMENT ON COLUMN job_update_log.ctdate IS '创建时间';
COMMENT ON COLUMN job_update_log.mddate IS '更新时间';

CREATE SEQUENCE if not exists job_update_log_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table job_update_log alter column id set default nextval('job_update_log_id_seq');

CREATE INDEX jobupdatelog_idx_taskid ON job_update_log (task_id);