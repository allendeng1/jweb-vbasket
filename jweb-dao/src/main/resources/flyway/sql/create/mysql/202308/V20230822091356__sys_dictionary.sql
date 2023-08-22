CREATE TABLE IF NOT EXISTS sys_dictionary  (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  type varchar(20)  NOT NULL COMMENT '类型',
  region varchar(20)  NOT NULL COMMENT '地区',
  name varchar(50) NOT NULL COMMENT '名称',
  status int(2) NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-停用',
  sort_col int(2) NOT NULL DEFAULT 0 COMMENT '排序列',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
  unique key (type, region, name)
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT '系统字典';
