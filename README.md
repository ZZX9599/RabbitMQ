# Rabbit的基本使用

- 模块provider和consumer使用SpringAMQP实现
  - 简单模型
  - 发布订阅模型
  - Fanout交换机的使用
  - Direct交换机的使用
  - Topic交换机的使用
  - 消息转换器MessageConverter的使用
- 模块reliable-provider和reliable-consumer实现
  - 消息生产者确认机制
  - ReturnCallback
  - ConfirmCallback
  - 确保消息一定能够发送到队列上去
- 模块durable实现
  - 交换机持久化
  - 队列持久化
  - 消息持久化
  - 保证消息不丢失
- 模块consumer-confirm实现
  - acknowledge-mode的三种模式，一般使用auto
  - 消费失败重试机制
  - 本地重试
  - 本地重试失败的策略
    - 丢弃
    - 重新入队
    - 进入指定交换机
- 模块mq-ttl实现
  - 基于本地重试策略和进入指定交换机加上队列的TTL实现的延迟消息
  - 基于本地重试策略和进入指定交换机加上消息的TTL实现的延迟消息
  - 两者同时存在，以消息的TTL为准
  - 基于RabbitMQ插件的延迟交换机来实现延迟消息