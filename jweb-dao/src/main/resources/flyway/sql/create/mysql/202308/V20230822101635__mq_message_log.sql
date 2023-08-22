CREATE TABLE IF NOT EXISTS mq_message_log (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  message_id bigint(20)  NOT NULL COMMENT '消息ID',
  type varchar(20) NOT NULL COMMENT '类型',
  excute_method  varchar(10)  NOT NULL COMMENT '处理方式', 
  excute_handler varchar(100)  NOT NULL COMMENT '处理器', 
  start_time bigint(20) NOT NULL COMMENT '开始时间',
  end_time bigint(20) NOT NULL COMMENT '结束时间',
  result_desc text NOT NULL COMMENT '处理结果描述',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT 'MQ异步消息消费日志';