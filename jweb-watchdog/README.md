# Jweb-watchdog

#### 介绍
"看门狗"服务，包含：API防重复提交和API防CC攻击

#### 使用说明

1.  API防重复提交
    1、在需要防重复提交的Controller方法上添加@NoResubmit(timeInterval = 3, submittable = true)
    2、如果接口有token参数，以token+类名+方法名+所有参数作为防重KEY
    3、如果接口没有token参数，以IP+类名+方法名+所有参数作为防重KEY
2.  API防CC攻击
	1、如果接口有token参数，当token对应的IP数量超过阈值，就视为异常请求
	2、当IP的访问量超过阈值，进入异常请求检查，当一秒钟内访问次数超过阈值的秒数占总秒数的占比超过阈值或者一分钟内访问次数超过阈值的分钟数占总分钟数的占比超过阈值，就视为异常请求

