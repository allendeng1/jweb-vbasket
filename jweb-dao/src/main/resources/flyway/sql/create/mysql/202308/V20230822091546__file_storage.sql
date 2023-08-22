CREATE TABLE IF NOT EXISTS file_storage  (
  id bigint(20) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  storage_site varchar(15)  NOT NULL COMMENT '存储站点',
  owner varchar(30) NOT NULL COMMENT '拥有者',
  type int(4) NOT NULL COMMENT '业务类型',
  storage_path text NOT NULL COMMENT '存储路径',
  file_type varchar(10) NOT NULL COMMENT '文件类型',
  file_name varchar(50) NOT NULL COMMENT '文件名称',
  file_size bigint(20) NOT NULL COMMENT '文件大小',
  is_delete tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  ctdate bigint(20) NOT NULL COMMENT '创建时间',
  mddate bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT '文件存储记录';
