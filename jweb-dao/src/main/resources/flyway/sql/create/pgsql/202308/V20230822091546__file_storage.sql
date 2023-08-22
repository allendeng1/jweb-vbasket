CREATE TABLE IF NOT EXISTS file_storage  (
  id bigint NOT NULL PRIMARY KEY,
  storage_site varchar(15)  NOT NULL,
  owner varchar(30) NOT NULL,
  type int4 NOT NULL,
  storage_path text NOT NULL,
  file_type varchar(10) NOT NULL,
  file_name varchar(50) NOT NULL,
  file_size bigint NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  ctdate bigint NOT NULL,
  mddate bigint NOT NULL
);

COMMENT ON TABLE file_storage IS '文件存储记录';
COMMENT ON COLUMN file_storage.storage_site IS '存储站点';
COMMENT ON COLUMN file_storage.owner IS '拥有者';
COMMENT ON COLUMN file_storage.type IS '业务类型';
COMMENT ON COLUMN file_storage.storage_path IS '存储路径';
COMMENT ON COLUMN file_storage.file_type IS '文件类型';
COMMENT ON COLUMN file_storage.file_name IS '文件名称';
COMMENT ON COLUMN file_storage.file_size IS '文件大小';

CREATE SEQUENCE if not exists file_storage_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
alter table file_storage alter column id set default nextval('file_storage_id_seq');
