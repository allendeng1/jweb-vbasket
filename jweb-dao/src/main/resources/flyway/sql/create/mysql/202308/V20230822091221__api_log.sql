CREATE TABLE IF NOT EXISTS api_log  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  server_context_path varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'web服务标识',
  ip_address varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端IP',
  user_id bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  req_url varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求URL',
  req_method varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  begin_time bigint(20) NOT NULL COMMENT '开始时间',
  end_time bigint(20) NOT NULL COMMENT '结束时间',
  cost_time bigint(20) NOT NULL COMMENT '耗时(单位：毫秒)',
  is_attack tinyint(1) NOT NULL COMMENT '系统防攻击检测状态：true-疑似攻击',
  req_param text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  controller_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求处理器名称',
  controller_method varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求处理方法名',
  result_data text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回结果数据',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT 'API日志';

CREATE INDEX apilog_idx_userid ON api_log (user_id);