# Jweb-common

#### 介绍
公共工具服务，包含：参数校验、多种加密方式、文件（excel导入导出、图片处理、人脸检测、存储等）、JWT token加盐、国际化处理等。

#### 使用说明

1、参数校验<br/><br/>
  在需要校验参数的方法上加com.jweb.common.annotation.param.ParameterCheck注解，完成方法参数的规则校验，避免在方法内部编写大量的参数校验代码
  <br/><br/>
2、文件存储<br/><br/>
  包含4种存储方式：aliyunOSS、FTP存储、本地存储、jweb-filestorage存储
  <br/><br/>
3、JWT token加盐<br/><br/>
  对生成的JWT token加盐加密处理，避免外部解析token获取敏感信息


