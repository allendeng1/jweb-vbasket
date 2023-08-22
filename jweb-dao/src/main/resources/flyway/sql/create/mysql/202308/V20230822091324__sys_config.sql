CREATE TABLE IF NOT EXISTS sys_config  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  status int(2) NOT NULL DEFAULT 0 COMMENT '状态：0-使用中，1-已废弃',
  prop_key varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置KEY',
  prop_value text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值',
  remark varchar(100) NULL DEFAULT NULL COMMENT '配置描述说明',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT '系统配置';

CREATE INDEX sysconfig_idx_propkey ON sys_config (prop_key);