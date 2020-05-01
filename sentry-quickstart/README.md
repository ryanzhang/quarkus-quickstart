# sentry-quickstart project
参考文档： https://quarkus.io/guides/logging-sentry
## 使用sentry发送错误报告
### 使用sentry saas 服务
1. 登陆 https://sentry.io/
用户名 ryan*@q*
2. 创建一个项目

3. 获取dsn key
Setting -> project -> Client Keys(DSN) -> show deprecated DSN

注意这里用的是deprecated DSN, 如果用默认的会要求设置pubic key 这个在quarkus文档中没有提到


### 安装本地 sentry
https://github.com/getsentry/onpremise
./install.sh

安装脚本需要用到 docker 和docker-compose, 我简单测试了一下，用podman-compose无法使用安装脚本.

### 测试
启动程序 mvn quarkus:dev
curl http://localhost:8080/hell

访问sentry.io的项目主页 应该可以看到错误。 

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `sentry-quickstart-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/sentry-quickstart-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/sentry-quickstart-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.