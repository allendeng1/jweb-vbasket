CREATE TABLE IF NOT EXISTS job_run_log  (
  id bigint NOT NULL PRIMARY KEY,
  run_no varchar(30)  NOT NULL ,
  task_id bigint NOT NULL ,
  task_no varchar(30)  NOT NULL ,
  task_name varchar(30)  NOT NULL ,
  status int2 NOT NULL,
  run_result int2 NULL ,
  start_date bigint NOT NULL ,
  end_date bigint NULL,
  remark varchar(100) NULL,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL

);


COMMENT ON TABLE job_run_log IS '任务运行日志';
COMMENT ON COLUMN job_run_log.run_no IS '运行编号';
COMMENT ON COLUMN job_run_log.task_id IS '任务ID';
COMMENT ON COLUMN job_run_log.task_no IS '任务编号';
COMMENT ON COLUMN job_run_log.task_name IS '任务名';
COMMENT ON COLUMN job_run_log.status IS '状态：0-运行中，1-运行结束';
COMMENT ON COLUMN job_run_log.run_result IS '运行结果：0-成功，1-失败';
COMMENT ON COLUMN job_run_log.start_date IS '开始时间';
COMMENT ON COLUMN job_run_log.end_date IS '结束时间';
COMMENT ON COLUMN job_run_log.remark IS '备注';
COMMENT ON COLUMN job_run_log.ctdate IS '创建时间';
COMMENT ON COLUMN job_run_log.mddate IS '更新时间';

CREATE SEQUENCE if not exists job_run_log_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table job_run_log alter column id set default nextval('job_run_log_id_seq');

CREATE unique INDEX jobrunlog_idx_runno ON job_run_log (run_no);
CREATE INDEX jobrunlog_idx_taskid ON job_run_log (task_id);