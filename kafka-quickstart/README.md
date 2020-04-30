# kafka-quickstart project
## quakus kafka例子

## 启动kafka
```bash
# podman-compose docker-compose.yml up
```
这里有个坑， 就是如何在docker-compose.yml中使用变量, compose.yml中定义的environment是给
container里面用的, yml中的变量需要额外在OS层面定义变量。于是我干脆就直接去掉变量

## 操作kafka
1. 登入容器，使用容器里面的客户端脚本
```bash
podman exec container_id /usr/bin/bash
#例举所有的topic
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
#列取某一个topic中的消息
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic generated-name --from-beginning
```

2. 也可以下载 kafka程序到本机 
https://apache.claz.org/kafka/2.5.0/kafka_2.12-2.5.0.tgz

3. 如果是openshift4中，就可以通过catalog里面的Operator
* 先安装kafka cluster operator
* 再安装topic operator

## 关闭 kafka
```bash
CTRL +C
# podman-compose docker-compose.yml down
```

## 程序中的kafka配置 
这里另一个主要注意事项 是配置application.properties,详情请看该文件的注解.

## dev模式下，访问的kafka和 prod模式下的kafka 区别
可以使用%dev.mp.messaging %prod.mp.messaging来设定

## 如果消息队列里面 保存对象，就要使用Serializer & Deserializer
Serializer就是 内存对象 -> byte 也就是磁盘存储格式， 这个可以使用通用的类 不需要自己定制。 
但是Deserializer就需要 根据你的业务对象去定制.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `kafka-quickstart-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/kafka-quickstart-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/kafka-quickstart-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.