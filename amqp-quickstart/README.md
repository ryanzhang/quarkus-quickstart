# amqp-quickstart project
参考文档: https://quarkus.io/guides/amqp

## 启动 Artimis
```bash
# podman-compose docker-compose.yml up
```
## 关闭 Artimis
```bash
CTRL +C
# podman-compose docker-compose.yml down
```
例子和kafka很像。 只是后端换成了Artimis, 使用的extension 不一样

创建项目使用命令: 
```bash
$ mvn io.quarkus:quarkus-maven-plugin:1.3.2.Final:create -DprojectGroupId=org.acme -DprojectArtifactId=amqp-quickstart -Dextensions="amqp,resteasy-jsonb,metrics"
```

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## AMQP和Kafka对比
### 配置文件不太相同
  1. AMQP链接的是: mp.messaging.outgoing.generated-price.address=xxx
  kafka链接的是 mp.messaging.outgoing.generated-price.topic=xxx
  amqp可以配置mp.messaging.incoming.prices.durable (怎么连接器也可以指定durable, 这个应该和broker的durable应该不同吧)
  
  2. broker需要在application.properties里面配置用户名和密码， kafka默认情况下没有配置也是
  可以访问的

artimis 可以通过console去访问 查看topic信息

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `amqp-quickstart-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/amqp-quickstart-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/amqp-quickstart-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.