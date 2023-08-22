CREATE TABLE IF NOT EXISTS job_rule  (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  task_no varchar(30)  NOT NULL COMMENT '任务编号',
  task_name varchar(30)  NOT NULL COMMENT '任务名',
  type int(2) NOT NULL DEFAULT 1 COMMENT '使用状态：1-固定任务，2-临时任务',
  task_cron varchar(50) NOT NULL COMMENT '定时表达式',
  status int(2) NOT NULL DEFAULT 0 COMMENT '使用状态：0-使用中，1-已废弃',
  run_status int(2) NOT NULL DEFAULT 0 COMMENT '运行状态：0-未运行，1-运行中，2-已执行',
  handler_name varchar(100) NOT NULL COMMENT '任务执行器类完整包名',
  task_content text NULL COMMENT '任务内容',
  remark varchar(100) NULL COMMENT '备注',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT '任务';