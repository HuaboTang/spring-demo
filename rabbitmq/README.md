# RabbitMQ特性

- producer不直接将消息发送到queue，甚至producer常常不知道消息是发往哪个queue的？而是通过exchanges?
- 默认轮流分配至producer
- 消息应答
- 消息持久化

# 生产消费模式

## 最简单Queue模式

自动应答消息。消息将循环轮流(round-robin)分配至consumers

```puml
@startuml

usecase producers
usecase exchange as e1
usecase exchange as e2
agent "channel" as cp
queue queue
agent "channel" as cc
usecase consumers

producers -> cp
cp -> e1
e1 -> queue
queue -> e2
e2 -> cc
cc -> consumers

@enduml
```

## Work Queues

## publish/subscribe

一个消息，多个消费者

对比Queue模式，增加消息应答

# 关于消息处理应答

- 增加非自动ack和ack处理。对比：消息不会丟，处理完了才会将消息放入channel
- 使用channel basicQos来设置响应前，通道接收的消息数量
- channel.basicConsumer.autoAck = false 屏蔽自动ack
- channel.basicAck 来响应消息处理成功
- <font color=red>如果设置了不自动ack，但未调用ack方法，会引起严重后果。RabbitMQ会不断吃内存。</font>

# 关于消息持久化

- channel.queueDeclare(xxx, true, ...)
- <font color=red>durable参数首次创建queue时设置，后续无法更改</font>
- 在生产和消费都需要设置为true
- MessageProperties.PERSISTENT_TEXT_PLAIN 指定持久化方式
  Channel.basicPublish(exchange, queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, ...)

# 关于消息分配
- 默认，消息会轮询进行分配（round-robin），例会有两个消费者，那永远一个消费偶数个一个消费奇数个
- 使用channel.basicQos方法的prefetchCount=1，来设置同时只放置一个到channel，处理完才放置下一个
