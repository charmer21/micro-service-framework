## Web 工程快速开始

###### Step 1 新建工程

> 继承 framework-web-starter 的 pom 文件 和 framework-web 框架

```xml
<parent>
  <groupId>io.wang</groupId>
  <artifactId>framework-web-starter</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</parent>
<dependencies>
  <dependency>
    <groupId>io.wang</groupId>
    <artifactId>framework-web</artifactId>
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

> 增加 application-dev.properties 配置文件，增加数据库的链接地址（用于账号及角色、权限数据）

```properties
# Common
jdbc.username=root
jdbc.password=pass@word1

# primary
jdbc.primary.url=jdbc:mysql://localhost:3306/microservice?characterEncoding=UTF-8&tinyInt1isBit=false
```

> 添加 DbConfiguration 实体文件，配置数据库链接地址，与配置文件对应

```java
@Data
@Configuration
public class DbConfiguration {

    @Value(value = "${jdbc.primary.url}")
    private String primaryUrl;
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
    public DataSource getPrimaryDruidDataSource() throws BeanInitializationException{
        DruidDataSource dataSource = druidConfiguration.getDruidDataSource();
        dataSource.setUrl(dbConfiguration.getPrimaryUrl());
        return dataSource;
    }
  
  	/**
     * 配置多数据源
     * @return
     */
	@Bean
    public MultipleDataSource getMultipleDataSource(){
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(getPrimaryDruidDataSource());
        Map<Object, Object> targetDataSource = new ConcurrentHashMap<Object, Object>();
        // 配置多数据源
        targetDataSource.put("primary", getPrimaryDruidDataSource());
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



#### 使用RPC服务

1. 引用接口定义文件，参考 [Service Get Starter的使用RPC服务章节](service-get-starter.md)
2. 增加RPC客户端类

```java
public class DemoClient extends AbstractGrpcClient<DemoGrpc.DemoBlockingStub> {

    public DemoClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected DemoGrpc.DemoBlockingStub getBlockingStub() {
        return DemoGrpc.newBlockingStub(channel);
    }

    public List<DemoEntity> query(int size) {
        QueryRequest request = QueryRequest.newBuilder().setSize(size).build();
        QueryResponse response;
        try {
            response = blockingStub.query(request);
        } catch (StatusRuntimeException e) {
            logger.warn("RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getEntitiesList();
    }
}
```

**需要注意的地方**

+ 客户端必须继承 AbstractGrpcClient<T> 
+ 类型 T 为对应 RPC 服务的 BlockingStub 类
+ 构造函数必须调用父类的构造函数 `super(host, port)`
+ 必须重写 getBlockingStub 方法，获取当前服务对应的 BlockingStub