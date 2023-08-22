CREATE TABLE IF NOT EXISTS message_record  (
  id bigint NOT NULL PRIMARY KEY,
  code varchar(4)  NOT NULL,
  sent_channel varchar(15) NOT NULL,
  status int2 NOT NULL,
  sender bigint NOT NULL DEFAULT 0,
  receiver varchar(30) NOT NULL,
  sent_way int2 NOT NULL DEFAULT 1,
  timing_time bigint,
  content text NOT NULL,
  sent_time bigint,
  sent_result text,
  template_code varchar(30) NULL,
  reference varchar(50),
  callback_time bigint,
  callback_result text,
  is_delete boolean NOT NULL DEFAULT false ,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
 
);

COMMENT ON TABLE message_record IS '消息发送记录';
COMMENT ON COLUMN message_record.code IS '模板编号';
COMMENT ON COLUMN message_record.sent_channel IS '发送渠道';
COMMENT ON COLUMN message_record.status IS '状态：0-待发送，1-发送中，2-发送成功，3-发送失败';
COMMENT ON COLUMN message_record.sender IS '发送者';
COMMENT ON COLUMN message_record.receiver IS '接收者';
COMMENT ON COLUMN message_record.sent_way IS '发送方式：1-实时发送，2-定时发送';
COMMENT ON COLUMN message_record.timing_time IS '定时发送时间点';
COMMENT ON COLUMN message_record.content IS '消息内容';
COMMENT ON COLUMN message_record.sent_time IS '发送时间';
COMMENT ON COLUMN message_record.sent_result IS '发送结果';
COMMENT ON COLUMN message_record.template_code IS '第三方平台模板编号';
COMMENT ON COLUMN message_record.reference IS '第三方消息标识';
COMMENT ON COLUMN message_record.callback_time IS '回调时间';
COMMENT ON COLUMN message_record.callback_result IS '回调结果';

CREATE SEQUENCE if not exists message_record_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table message_record alter column id set default nextval('message_record_id_seq');

CREATE INDEX msgrec_idx_refe ON message_record (reference);
