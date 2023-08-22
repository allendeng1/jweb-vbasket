CREATE TABLE IF NOT EXISTS job_update_log  (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  task_id bigint(20) NOT NULL COMMENT '任务ID',
  task_no varchar(30)  NOT NULL COMMENT '任务编号',
  task_name varchar(30)  NOT NULL COMMENT '任务名',
  type int(2) NOT NULL COMMENT '修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息',
  admin_user_id bigint(20) NOT NULL COMMENT '操作员ID',
  remark varchar(100) NULL COMMENT '备注',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT '任务修改日志';

CREATE INDEX jobupdatelog_idx_taskid ON job_update_log (task_id);