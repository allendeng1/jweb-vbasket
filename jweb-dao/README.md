# Jweb-dao

#### 介绍
数据存储服务，支持mysql和pgsql，包含：flyway数据库sql脚本版本管理，redis数据缓存，代码生成（bean、dao、mapper、service、controller、swagger api文档）

#### 使用说明
1、flyway数据库sql脚本版本管理
   在src/main/resources/flyway目录下创建编写sql脚本，web服务启动时自动执行这些脚本维护数据库表结构和数据
   
2、redis数据缓存
   根据key删除的缓存，在一定时间内（几秒）该key的数据不再写入缓存，确保缓存与数据库数据的一致性。
   
3、代码生成
   1.src/main/resources/generatorConfig.xml 配置文件
   2.运行com.jweb.dao.generator.MyGenerator的main方法


