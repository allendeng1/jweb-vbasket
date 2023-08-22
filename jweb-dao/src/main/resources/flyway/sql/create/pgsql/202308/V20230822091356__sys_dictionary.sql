CREATE TABLE IF NOT EXISTS sys_dictionary  (
  id bigint NOT NULL PRIMARY KEY,
  type varchar(20)  NOT NULL,
  region varchar(20)  NOT NULL ,
  name varchar(50) NOT NULL,
  status int2 NOT NULL DEFAULT 1,
  sort_col int2 NOT NULL DEFAULT 0 ,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
 
);

COMMENT ON TABLE sys_dictionary IS '系统字典';
COMMENT ON COLUMN sys_dictionary.type IS '类型';
COMMENT ON COLUMN sys_dictionary.region IS '地区';
COMMENT ON COLUMN sys_dictionary.name IS '名称';
COMMENT ON COLUMN sys_dictionary.status IS '状态：1-正常，2-停用';
COMMENT ON COLUMN sys_dictionary.sort_col IS '排序列';
COMMENT ON COLUMN sys_dictionary.ctdate IS '创建时间';
COMMENT ON COLUMN sys_dictionary.mddate IS '更新时间';

CREATE SEQUENCE if not exists sys_dictionary_id_seq
START WITH 100
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table sys_dictionary alter column id set default nextval('sys_dictionary_id_seq');

CREATE unique INDEX sysdictionary_idx_unique ON sys_dictionary (type, region, name);

