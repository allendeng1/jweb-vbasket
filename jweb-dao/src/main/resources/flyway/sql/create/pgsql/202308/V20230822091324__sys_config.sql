CREATE TABLE IF NOT EXISTS sys_config  (
  id bigint NOT NULL PRIMARY KEY,
  status int2 NOT NULL DEFAULT 0,
  prop_key varchar(30) NOT NULL,
  prop_value text,
  remark varchar(100) NULL DEFAULT NULL,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
);

COMMENT ON TABLE sys_config IS '系统配置';
COMMENT ON COLUMN sys_config.status IS '状态：0-使用中，1-已废弃';
COMMENT ON COLUMN sys_config.prop_key IS '配置KEY';
COMMENT ON COLUMN sys_config.prop_value IS '配置值';
COMMENT ON COLUMN sys_config.remark IS '配置描述说明';

CREATE SEQUENCE if not exists sys_config_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table sys_config alter column id set default nextval('sys_config_id_seq');

CREATE INDEX sysconfig_idx_propkey ON sys_config (prop_key);