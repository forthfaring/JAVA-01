Homework

### 1、(选做)分析前面作业设计的表，是否可以做垂直拆分。



### 2、(必做)设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。 并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

步骤：

1、创建物理库和表，执行DDL sql

2、修改sharding-proxy配置文件并启动

3、执行代码写入数据

代码文件夹：[sharding-01](sharding-01)

Sharding-proxy配置文件：[sharding-01/src/main/resources/config-sharding.yaml](sharding-01/src/main/resources/config-sharding.yaml)

[sharding-01/src/main/resources/server.yaml](sharding-01/src/main/resources/server.yaml)

分片策略：根据user_id确定库，根据id确定表。id使用proxy雪花算法生成。

增：

​	1、代码插入64条数据。发现策略正确。

​	2、mysqlcli 单独insert一条数据

​	INSERT INTO `sharding_db`.`t_order`( `order_no`, `price`, `order_status`, `pay_status`, `fail_reason`, `created_time`, `updated_time`, `created_id`, `updated_id`, `user_id`) VALUES ( '65NO', 10, 1, 1, '', '2021-02-01 00:00:00', '2021-02-01 00:00:11', 100, 100, 0);

​	发现雪花id=577514740738666497，应该在db0.t_order_1上

​	查询物理库db0：select * from t_order_1;验证正确！

查：

​	select * from t_order where id = 577480684600799232 and user_id = 0;

​	可以根据user_id确定在db0上，id确定在表t_order_0里。执行逻辑库查询，发现proxy自动解析了分片策略，只查询了这一张表。正确。

删：delete from t_order where id = 577514740738666497;

​	发现分别对阵db_0和db_1的t_order_1都执行了删除。正确。

改：update

​	update order_status = 2 where id = 577516364261470208;

​	验证正确。

### 3、(选做)模拟1000万的订单单表数据，迁移到上面作业2的分库分表中。



### 4、(选做)重新搭建一套4个库各64个表的分库分表，将作业2中的数据迁移到新分库







### 1、(选做)列举常见的分布式事务，简单分析其使用场景和优缺点。
### 2、(必做)基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布 式事务应用 demo(二选一)，提交到 Github。





### 3、(选做)基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。 4、(选做)基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。
### 5、(选做☆)设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。
### 6、(选做☆)设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器， 不需要实现全局事务的持久化和恢复、高可用等。
### 7、(选做☆)设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。