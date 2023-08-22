CREATE TABLE IF NOT EXISTS mq_message (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  channel varchar(30)  NOT NULL COMMENT '消息通道',
  topic_name varchar(30) NULL COMMENT '主题名称',
  queue_name varchar(30) NOT NULL COMMENT '队列名称',
  biz_type varchar(50) NULL COMMENT '业务类型',
  status int(2) NOT NULL DEFAULT 0 COMMENT '状态：0-未处理，1-已发布，2-处理成功，3-处理失败',
  publish_time bigint(20) COMMENT '发布时间',
  content text NOT NULL COMMENT '消息内容',
  is_delete tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  delete_time bigint(20) COMMENT '删除时间',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT 'MQ异步消息';