CREATE TABLE IF NOT EXISTS mq_message_log (
  id bigint NOT NULL PRIMARY KEY,
  message_id bigint  NOT NULL,
  type varchar(20) NOT NULL,
  excute_method  varchar(10)  NOT NULL, 
  excute_handler varchar(100)  NOT NULL, 
  start_time bigint NOT NULL,
  end_time bigint NOT NULL,
  result_desc text NOT NULL,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
 
);

COMMENT ON TABLE mq_message_log IS 'MQ异步消息消费日志';
COMMENT ON COLUMN mq_message_log.message_id IS '消息ID';
COMMENT ON COLUMN mq_message_log.type IS '类型';
COMMENT ON COLUMN mq_message_log.excute_method IS '处理方式';
COMMENT ON COLUMN mq_message_log.excute_handler IS '处理器';
COMMENT ON COLUMN mq_message_log.start_time IS '开始时间';
COMMENT ON COLUMN mq_message_log.end_time IS '结束时间';
COMMENT ON COLUMN mq_message_log.result_desc IS '处理结果描述';
COMMENT ON COLUMN mq_message_log.ctdate IS '创建时间';
COMMENT ON COLUMN mq_message_log.mddate IS '更新时间';

CREATE SEQUENCE if not exists mq_message_log_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table mq_message_log alter column id set default nextval('mq_message_log_id_seq');