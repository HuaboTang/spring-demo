# Spring Cloud Config 相关Demo

## 意图

- 使用git仓库为spring-config源
- 确认配置优先级
  - 在使用了spring.profiles.active 的情况下，哪个配置优先级更高
  配置可用的情况下，会覆盖配置
    
## Spring Config使用说明

### 服务端

- Maven

```xml
<!-- 包括了starter-web, starter-actuator -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

- 配置

```yaml
spring:
  cloud:
    config:
      server:
        git:
          # git 地址，直接复制仓库页面的地址，不是clone地址
          uri: https://gitee.com/bobot/spring-demo
          # 仓库的目录
          search-paths: spring-cloud-config/spring-cloud-config-configs/src/main/resources
          timeout: 10
```

### Client

- Maven

```xml
<!-- 未集成starter-web等内容，需要手机引入 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```

- 配置

```yaml
spring:
  application:
    # 应用名，默认访问方式http://${sprint.cloud.config.uri}/${spring.application.name}.yml
    name: demo-spring-cloud-config-client

  cloud:
    config:
      enabled: true
      # config server地址
      uri: http://127.0.0.1:8080
```
