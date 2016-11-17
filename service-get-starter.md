## Service 工程快速开始

###### Step 1 新建工程

> 继承 framework-service-starter 的 pom 文件 和 framework-service 框架

```xml
<parent>
  <groupId>io.wang</groupId>
  <artifactId>framework-service-starter</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</parent>
<dependencies>
  <dependency>
    <groupId>io.wang</groupId>
    <artifactId>framework-service</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>
</dependencies>
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



###### 数据源切换

> 配置好 MultipleDataSource
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



#### 使用RPC服务

1. 新建工程，用于存放接口定义文件，例如：service-demo-def
2. 在 main/proto 目录新增 proto 定义文件 [proto3官网](https://developers.google.com/protocol-buffers/docs/proto3)
3. 编译 并 安装
4. 在 service 工程的 pom 文件中引用该接口定义工程

```xml
<dependency>
  <groupId>io.wang</groupId>
  <artifactId>service-demo-def</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

5. 在 service 工程中创建相对应 RPC接口的实现类

```java
@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Autowired
    private Demo2Service service;

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String message = "";
        List<Demo> demos = service.query();
        if (demos.size() > 0) {
            message = demos.get(0).getName();
        }

        HelloReply reply = HelloReply.newBuilder().setMessage(request.getName() + " " + message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
```

**在实现类上增加 @GrpcService 特性，站点启动时会自动加载该 RPC 服务**