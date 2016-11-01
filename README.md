# micro-service-framework
基于SpringBoot的微服务基础框架

## 基础设施

> 作为一个微服务框架, 应该包含(支持)的功能

+ 多数据源, 多数据库
+ 统一的日志记录方式
+ 服务状态监控

## Druid数据库连接池

[官方网站](https://github.com/alibaba/druid)

### Maven

```
<dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>druid</artifactId>
     <version>1.0.23</version>
</dependency>
```

#### Get Starter

1. 增加[ DruidDataSource 配置参数 ]((https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE))及 Bean 配置。

1. 增加[ StatViewServlet配置 ](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE)



## 数据操作层

### spring-data-jpa

[官方网站](http://projects.spring.io/spring-data-jpa/)

#### Demo

+ 参见 io.wang.jpa-demo 模块

### mybatis

[官方网站](http://www.mybatis.org/mybatis-3/zh/index.html)