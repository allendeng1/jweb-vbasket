CREATE TABLE IF NOT EXISTS message_template  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  code varchar(4)  NOT NULL COMMENT '模板编号',
  sent_channel varchar(15) NOT NULL COMMENT '发送渠道',
  template_code varchar(30) NULL COMMENT '第三方平台模板编号',
  content text NOT NULL COMMENT '消息内容',
  is_delete tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT '消息模板';

CREATE INDEX msgtep_idx_code ON message_template (code);