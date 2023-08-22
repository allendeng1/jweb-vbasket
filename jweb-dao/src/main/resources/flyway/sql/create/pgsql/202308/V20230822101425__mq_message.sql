CREATE TABLE IF NOT EXISTS mq_message (
  id bigint NOT NULL PRIMARY KEY,
  channel varchar(30)  NOT NULL,
  topic_name varchar(30) NULL,
  queue_name varchar(30) NOT NULL ,
  biz_type varchar(50) NULL,
  status int2 NOT NULL DEFAULT 0 ,
  publish_time bigint,
  content text NOT NULL ,
  is_delete boolean NOT NULL DEFAULT false ,
  delete_time bigint,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL

);


COMMENT ON TABLE mq_message IS 'MQ异步消息';
COMMENT ON COLUMN mq_message.channel IS '消息通道';
COMMENT ON COLUMN mq_message.topic_name IS '主题名称';
COMMENT ON COLUMN mq_message.queue_name IS '队列名称';
COMMENT ON COLUMN mq_message.biz_type IS '业务类型';
COMMENT ON COLUMN mq_message.status IS '状态：0-未处理，1-已发布，2-处理成功，3-处理失败';
COMMENT ON COLUMN mq_message.publish_time IS '发布时间';
COMMENT ON COLUMN mq_message.content IS '消息内容';
COMMENT ON COLUMN mq_message.is_delete IS '是否删除';
COMMENT ON COLUMN mq_message.delete_time IS '删除时间';
COMMENT ON COLUMN mq_message.ctdate IS '创建时间';
COMMENT ON COLUMN mq_message.mddate IS '更新时间';

CREATE SEQUENCE if not exists mq_message_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table mq_message alter column id set default nextval('mq_message_id_seq');