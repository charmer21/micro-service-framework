# micro-service-framework
基于SpringBoot的微服务基础框架

## 介绍

> 作为一个微服务框架, 应该包含(支持)的功能

+ 多数据源, 多数据库
+ 统一的日志记录方式
+ 服务状态监控
+ 通讯模块(RPC)



## Get Starter

[Web Get Starter](web-get-starter.md)

[Service Get Starter](service-get-starter.md)



## Druid数据库连接池

[官方网站](https://github.com/alibaba/druid)

###### Maven

```
<dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>druid</artifactId>
     <version>1.0.23</version>
</dependency>
```

###### Get Starter

1. 增加[ DruidDataSource 配置参数 ]((https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE))及 Bean 配置。
2. 增加[ StatViewServlet配置 ](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE)


###### 监控页面地址

[http://localhost:8080/druid](http://localhost:8080/druid)

###### 相关配置

```properties
# 数据库基础配置
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.initialSize=2
jdbc.maxActive=20
jdbc.maxIdle=5
jdbc.minIdle=1
jdbc.validationQuery=select 'x'
jdbc.timeBetweenEvictionRunsMillis=60000
jdbc.minEvictableIdleTimeMillis=300000
jdbc.testWhileIdle=true
jdbc.testOnBorrow=false
jdbc.testOnReturn=false

# druid连接池配置
druid.jdbc.filters=stat
druid.jdbc.maxWait=60000
druid.jdbc.poolPreparedStatements=true
druid.jdbc.maxOpenPreparedStatements=20
```

###### 各环境配置(dev、sit、uat、prod)

```properties
jdbc.username=username
jdbc.password=password

jdbc.db1.url=jdbc:mysql://localhost:3306/dbname?characterEncoding=UTF-8&tinyInt1isBit=false
jdbc.db2.url=jdbc:mysql://localhost:3306/dbname?characterEncoding=UTF-8&tinyInt1isBit=false
```




## 数据操作层

### mybatis3

[官方网站](http://www.mybatis.org/mybatis-3/zh/index.html)

##### 多数据源配置

参考 [Spring+MyBatis多数据源配置实现](http://www.cnblogs.com/lzrabbit/p/3750803.html)



## UI

#### 渲染模板

Thymeleaf

 [官方网站](http://www.thymeleaf.org/index.html)

###### 相关配置

```properties
spring.thymeleaf.prefix=classpath:/templates/	# 模板文件所在目录(resources/templates)
spring.thymeleaf.suffix=.html					# 模板文件后缀
spring.thymeleaf.mode=HTML5						# 模板文件校验格式(严格的HTML5校验)
spring.thymeleaf.encoding=UTF-8					# 模板文件编码
spring.thymeleaf.content-type=text/html			# 模板文件内容类型
spring.thymeleaf.cache=false					# 是否使用模板缓存
```

###### 页面内容权限控制

```html
<div sec:authorize="hasPermission('', 'FUNC')">
  Func Content
</div>
```



整合Spring Security 4 

[官方网站](http://projects.spring.io/spring-security/)

###### 基础配置路径

`com.companyname.framework.security`



## 其他组件

#### lombok

 [官方网站](https://projectlombok.org/)

> 简化实体Getter/Setter的编写

###### sample:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demo {
    private String id;
    private String name;
    private String mobile;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
```

+ @Data - 编译时自动创建Gettter和Setter
+ @AllArgConstructor - 编译时生成所有字段的构造函数
+ @NoArgsConstructor - 编译时生成无参数的构造函数
+ 如果对Getter和Setter有特殊需求，直接编写对应的Getter/Setter函数即可




#### 使用 jetty 替换内置的 tomcat

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```



## 通讯方式

| FROM  | TO      |              | 实现                                 |
| ----- | ------- | ------------ | ---------------------------------- |
| Web   | Service | HTTP Restful | RestTemplate or HttpClient         |
| API网关 | API鉴权站点 | RPC（gRPC）    | proto + gRPC Service + gRPC Client |
| API网关 | Service | HTTP Restful | RestTemplate or HttpClient         |
| 第三方系统 | API网关   | HTTP Restful | RestTemplate or HttpClient         |



#### gRPC

###### maven配置

```xml
<dependencies>
  <!-- gRPC -->
  <dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-netty</artifactId>
    <version>1.0.1</version>
  </dependency>
  <dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-protobuf</artifactId>
    <version>1.0.1</version>
  </dependency>
  <dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>1.0.1</version>
  </dependency>
</dependencies>

<build>
  <extensions>
    <extension>
      <groupId>kr.motd.maven</groupId>
      <artifactId>os-maven-plugin</artifactId>
      <version>1.4.1.Final</version>
    </extension>
  </extensions>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <plugin>
      <groupId>org.xolstice.maven.plugins</groupId>
      <artifactId>protobuf-maven-plugin</artifactId>
      <version>0.5.0</version>
      <configuration>
        <protocArtifact>com.google.protobuf:protoc:3.1.0:exe:${os.detected.classifier}</protocArtifact>
        <pluginId>grpc-java</pluginId>
        <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.0.1:exe:${os.detected.classifier}</pluginArtifact>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>compile</goal>
            <goal>compile-custom</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

###### Example

[helloworld.proto](https://github.com/grpc/grpc-java/blob/master/examples/src/main/proto/helloworld.proto)

[HelloWorldClient.java](https://github.com/grpc/grpc-java/blob/master/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java)

[HelloWorldServer.java](https://github.com/grpc/grpc-java/blob/master/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldServer.java)


