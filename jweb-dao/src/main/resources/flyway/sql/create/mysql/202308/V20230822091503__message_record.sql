CREATE TABLE IF NOT EXISTS message_record  (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  code varchar(4)  NOT NULL COMMENT '模板编号',
  sent_channel varchar(15) NOT NULL COMMENT '发送渠道',
  status int(2) NOT NULL COMMENT '状态：0-待发送，1-发送中，2-发送成功，3-发送失败',
  sender bigint(20) NOT NULL DEFAULT 0 COMMENT '发送者',
  receiver varchar(50) NOT NULL COMMENT '接收者',
  sent_way int(2) NOT NULL DEFAULT 1 COMMENT '发送方式：1-实时发送，2-定时发送',
  timing_time bigint(20) COMMENT '定时发送时间点',
  content text NOT NULL COMMENT '消息内容',
  sent_time bigint(20) COMMENT '发送时间',
  sent_result text COMMENT '发送结果',
  template_code varchar(30) NULL COMMENT '第三方平台模板编号',
  reference varchar(50) COMMENT '第三方消息标识',
  callback_time bigint(20) COMMENT '回调时间',
  callback_result text COMMENT '回调结果',
  is_read tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  is_delete tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT '消息发送记录';

CREATE INDEX msgrec_idx_refe ON message_record (reference);