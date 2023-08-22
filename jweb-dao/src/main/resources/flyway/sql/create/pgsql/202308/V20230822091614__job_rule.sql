CREATE TABLE IF NOT EXISTS job_rule  (
  id bigint NOT NULL PRIMARY KEY,
  task_no varchar(30)  NOT NULL,
  task_name varchar(30)  NOT NULL,
  type int2 NOT NULL DEFAULT 1,
  task_cron varchar(50) NOT NULL ,
  status int2 NOT NULL DEFAULT 0 ,
  run_status int2 NOT NULL DEFAULT 0 ,
  handler_name varchar(100) NOT NULL,
  task_content text NULL,
  remark varchar(100) NULL,
  ctdate bigint NOT NULL ,
  mddate bigint NOT NULL
  
) ;

COMMENT ON TABLE job_rule IS '任务';
COMMENT ON COLUMN job_rule.task_no IS '任务编号';
COMMENT ON COLUMN job_rule.task_name IS '任务名';
COMMENT ON COLUMN job_rule.type IS '使用状态：1-固定任务，2-临时任务';
COMMENT ON COLUMN job_rule.task_cron IS '定时表达式';
COMMENT ON COLUMN job_rule.status IS '使用状态：0-使用中，1-已废弃';
COMMENT ON COLUMN job_rule.run_status IS '运行状态：0-未运行，1-运行中，2-已执行';
COMMENT ON COLUMN job_rule.handler_name IS '任务执行器类完整包名';
COMMENT ON COLUMN job_rule.task_content IS '任务内容';
COMMENT ON COLUMN job_rule.remark IS '备注';
COMMENT ON COLUMN job_rule.ctdate IS '创建时间';
COMMENT ON COLUMN job_rule.mddate IS '更新时间';

CREATE SEQUENCE if not exists job_rule_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table job_rule alter column id set default nextval('job_rule_id_seq');

