CREATE TABLE IF NOT EXISTS api_log  (
  id bigint NOT NULL PRIMARY KEY,
  server_context_path varchar(30) NOT NULL,
  ip_address varchar(50),
  user_id bigint NULL DEFAULT NULL,
  req_url varchar(255) NOT NULL,
  req_method varchar(8) NOT NULL,
  begin_time bigint NOT NULL,
  end_time bigint NOT NULL,
  cost_time bigint NOT NULL,
  is_attack boolean NOT NULL,
  req_param text,
  controller_name varchar(255)  NOT NULL,
  controller_method varchar(50) NOT NULL,
  result_data text,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
);

COMMENT ON TABLE api_log IS 'APP日志';
COMMENT ON COLUMN api_log.server_context_path IS 'web服务标识';
COMMENT ON COLUMN api_log.ip_address IS '客户端IP';
COMMENT ON COLUMN api_log.user_id IS '用户ID';
COMMENT ON COLUMN api_log.req_url IS '请求URL';
COMMENT ON COLUMN api_log.req_method IS '请求方式';
COMMENT ON COLUMN api_log.begin_time IS '开始时间';
COMMENT ON COLUMN api_log.end_time IS '结束时间';
COMMENT ON COLUMN api_log.cost_time IS '耗时(单位：毫秒)';
COMMENT ON COLUMN api_log.is_attack IS '系统防攻击检测状态：true-疑似攻击';
COMMENT ON COLUMN api_log.req_param IS '请求参数';
COMMENT ON COLUMN api_log.controller_name IS '请求处理器名称';
COMMENT ON COLUMN api_log.controller_method IS '请求处理方法名';
COMMENT ON COLUMN api_log.result_data IS '返回结果数据';
COMMENT ON COLUMN api_log.ctdate IS '创建时间';
COMMENT ON COLUMN api_log.mddate IS '更新时间';

CREATE SEQUENCE if not exists api_log_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table api_log alter column id set default nextval('api_log_id_seq');

CREATE INDEX apilog_idx_userid ON api_log (user_id);