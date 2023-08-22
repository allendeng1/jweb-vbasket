CREATE TABLE IF NOT EXISTS job_run_log  (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  run_no varchar(30)  NOT NULL COMMENT '运行编号',
  task_id bigint(20) NOT NULL COMMENT '任务ID',
  task_no varchar(30)  NOT NULL COMMENT '任务编号',
  task_name varchar(30)  NOT NULL COMMENT '任务名',
  status int(2) NOT NULL COMMENT '状态：0-运行中，1-运行结束',
  run_result int(2) NULL COMMENT '运行结果：0-成功，1-失败',
  start_date bigint(20) NOT NULL COMMENT '开始时间',
  end_date bigint(20) NULL COMMENT '结束时间',
  remark varchar(100) NULL COMMENT '备注',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE,
  unique key (run_no)
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT '任务运行日志';


CREATE INDEX jobrunlog_idx_taskid ON job_run_log (task_id);