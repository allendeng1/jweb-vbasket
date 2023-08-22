CREATE TABLE IF NOT EXISTS message_template  (
  id bigint NOT NULL PRIMARY KEY,
  code varchar(4)  NOT NULL,
  sent_channel varchar(15) NOT NULL,
  template_code varchar(30) NULL,
  content text NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL

);

COMMENT ON TABLE message_template IS '消息模板';
COMMENT ON COLUMN message_template.code IS '模板编号';
COMMENT ON COLUMN message_template.sent_channel IS '发送渠道';
COMMENT ON COLUMN message_template.template_code IS '第三方平台模板编号';
COMMENT ON COLUMN message_template.content IS '消息内容';

CREATE SEQUENCE if not exists message_template_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table message_template alter column id set default nextval('message_template_id_seq');

CREATE INDEX msgtep_idx_code ON message_template (code);
