尝试重现com.netflix.client.ClientException: Load balancer does not have available server for client错误

怎么重现？

待解决问题：

- [x] 依赖的服务退出之后，并且没有服务起来，就会出这个错误
- [ ] eureka 跟 ribbon 是怎么结合起来工作的
- [ ] ribbon PollingServerListUpdater的定时任务在干啥？

# 推测

# Eureka 核心

- region, zone
  region：地区，zone：机房
- 注册之后，会定时上报心跳
- 正常退出的时候会发cancel消息 com/netflix/discovery/DiscoveryClient.java:923

# 阅读代码

```puml
@startuml
left to right direction

class EurekaClientAutoConfiguration <<入口>>

class EurekaClientConfigBean
class EurekaClientConfig

EurekaClientConfigBean  --|> EurekaClientConfig

class CloudEurekaClient
class com.netflix.discovery.DiscoveryClient
note top

end note

EurekaClientAutoConfiguration --> CloudEurekaClient
CloudEurekaClient --o com.netflix.discovery.DiscoveryClient
com.netflix.discovery.DiscoveryClient --|> com.netflix.discovery.EurekaClient

class EurekaInstanceConfig
note right of EurekaInstanceConfig
实例注册到eureka所需要的配置信息
end note 

class EurekaInstanceConfigBean <<eureka配置>>
EurekaClientAutoConfiguration --> EurekaInstanceConfigBean
EurekaInstanceConfigBean --|> EurekaInstanceConfig
 
class EurekaDiscoveryClient
EurekaClientAutoConfiguration --> EurekaDiscoveryClient
EurekaDiscoveryClient --|> org.springframework.cloud.client.discovery.DiscoveryClient 

class EurekaServiceRegistry
EurekaClientAutoConfiguration --> EurekaServiceRegistry 

@enduml
```

```puml
@startmindmap
+ RibbonEurekaAutoConfiguration
@endmindmap
```

```puml
@startmindmap
+ EurekaDiscoveryClientConfiguration
@endmindmap
```

DiscoveryClient

- EurekaTransport 跟Eureka服务通信的类
- AzToRegionMapper 包含映射可用区到区域映射的契约的接口。 一个实现总是事先知道将从映射器查询哪个区域到区域的映射，这将有助于事先缓存这些信息。

```text
这个类，是有助于与Eureka Server交互的类。Eureka Client用于处理：
a. 注册Eureka Server实例
b. 周期更新Eureka Server的lease
c. 在关闭时释放Eureka Server的lease
d. 查询Eureka Server上service/实例列表

cacheRefreshExecutor 定时同步eureka register
com.netflix.discovery.DiscoveryClient#refreshRegistry 同步eureka register实际执行方法
```

```puml
@startuml
class RefreshableEurekaClientConfiguration <<spring-cloud-starter>>

class DiscoveryClient
note top: eureka核心初始化类

class CloudEurekaClient
note top : 继承包装DiscoveryClient 

class Appliclations <<com.netflix.discovery.shared>>
note top of Applications
包装所有从eureka server返回的registry信息。
所有的Registry信息都是从EurekaClientConfig.getRegistryFetchIntegerValSeconds()中定义的eureka server中获取。
拉取到Registry之后，会打乱顺序，根据EurekaClientConfig.shouldFilterOnlyUpInstances()配置和InstanceStatus.UP状态来过滤实例。
end note

class ThresholdLevelsMetric
note top of ThresholdLevelsMetric
// todo
end note

RefreshableEurekaClientConfiguration -> CloudEurekaClient
CloudEurekaClient -> DiscoveryClient
DiscoveryClient -> Appliclations
@enduml
```

### 同步eureka register流程
```puml
@startuml

== 正常 ==

service -> eureka: 注册
client -> client:定时30秒（默认配置，下同）
activate client
client -> eureka: pull registry
client -> client: 拉取到service
activate client
deactivate client
deactivate client

user -> client
activate client
client -> user: success: 200
deactivate client

== connection refuse错误 ==

service -> eureka: 下线
user -> client
activate client
client -> user: Connection refuse
deactivate client

== no availabel server ==

client -> client:定时30秒
activate client
client -> eureka: 拉取所有registry
client -> client: service 已经下线
activate client
deactivate client
deactivate client

user -> client
activate client
client -> user: no available server
deactivate client

== online, recover ==

service -> eureka: 上线
client -> client:定时30秒
activate client
client -> eureka: pull registry
client -> client: service online
activate client
deactivate client
deactivate client

user -> client
activate client
client -> user: success: 200
deactivate client

@enduml
```

# Ribbon

- ribbon.eureka.enabled 默认为true，设置false时是为了在不使用eureka的场景使用ribbon，比如目前测试环境，没有eureka
  如果在有eureka时设置，会出现not have available server问题
- PollingServerListUpdater在访问时，会初始化，并启动定时任务
  - com.netflix.loadbalancer.DynamicServerListLoadBalancer.updateListOfServers
  - org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList
  - com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList