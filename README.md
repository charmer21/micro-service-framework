# micro-service-framework
基于SpringBoot的微服务基础框架

## 基础设施

> 作为一个微服务框架, 应该包含(支持)的功能

+ 多数据源, 多数据库
+ 统一的日志记录方式
+ 服务状态监控
+ 通讯模块(RPC)

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

###### 各环境配置

```properties
jdbc.username=username
jdbc.password=password

jdbc.db1.url=jdbc:mysql://localhost:3306/dbname?characterEncoding=UTF-8&tinyInt1isBit=false
jdbc.db2.url=jdbc:mysql://localhost:3306/dbname?characterEncoding=UTF-8&tinyInt1isBit=false
```




## 数据操作层

### spring-data-jpa

[官方网站](http://projects.spring.io/spring-data-jpa/)



### mybatis3

[官方网站](http://www.mybatis.org/mybatis-3/zh/index.html)



##### 切换数据源

> 按照 Get Starter 设置MultipleDataSource
>
> Service 层继承 MicroService，并 Override 属性 getDbKey() 

```java
@Service
public class db2DemoService extends MicroService {

    @Override
    public String getDbKey() {
        return "db2";
    }
}
```





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



## Web工程 Get Starter

整合framework工程

###### Step 1 新建工程

> 继承 framework-webapp-starter 的 pom 文件

```xml
<parent>
  <groupId>io.wang</groupId>
  <artifactId>framework-webapp-starter</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</parent>
```

###### Step 2 添加 main 函数

> 将 Application.java 文件放在 com.companyname 包中，不要放在子包中
>
> 增加 @Import(value = com.companyname.framework.WebConfiguration.class) 注解

```java
@SpringBootApplication
@Import(value = com.companyname.framework.WebConfiguration.class)
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
```

###### Step 3 配置数据源

> 增加 application-dev.properties 配置文件，增加各数据库的链接地址

```properties
# Common
jdbc.username=root
jdbc.password=pass@word1

# db1
jdbc.db1.url=jdbc:mysql://localhost:3306/microservice?characterEncoding=UTF-8&tinyInt1isBit=false

# db2
jdbc.db2.url=jdbc:mysql://localhost:3306/microservice?characterEncoding=UTF-8&tinyInt1isBit=false
```

> 添加 DbConfiguration 实体文件，配置各数据库链接地址，与配置文件对应

```java
@Data
@Configuration
public class DbConfiguration {

    @Value(value = "${jdbc.db1.url}")
    private String db1Url;

    @Value(value = "${jdbc.db2.url}")
    private String db2Url;
}
```

> 添加 WebConfiguration 文件，并增加 DataSource 数据源配置

```java
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private DbConfiguration dbConfiguration;

    @Autowired
    private DruidConfiguration druidConfiguration;

    /**
     * 针对不同数据库的数据源配置
     * @return
     * @throws BeanInitializationException
     */
    public DataSource getDb1DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb1Url());
        return dataSource;
    }

    /**
     * 针对不同数据库的数据源配置
     * @return
     * @throws BeanInitializationException
     */
    public DataSource getDb2DruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getDb2Url());
        return dataSource;
    }
  
  	/**
     * 配置多数据源
     * @return
     */
	@Bean
    public MultipleDataSource getMultipleDataSource(){
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(getDb1DruidDataSource());
        Map<Object, Object> targetDataSource = new ConcurrentHashMap<Object, Object>();
        // 配置多数据源
        targetDataSource.put("db1", getDb1DruidDataSource());
        targetDataSource.put("db2", getDb2DruidDataSource());
        multipleDataSource.setTargetDataSources(targetDataSource);
        return multipleDataSource;
    }
}
```

###### Step 4 将默认配置 copy 到项目对应的目录中

> 静态资源
>
> src/main/resources/static/\*
>
> 页面模板
>
> src/main/resources/templates\*
>
> 程序配置文件
>
> src/main/resources/application.properties
>
> 日志配置文件
>
> src/main/resources/logback.xml

