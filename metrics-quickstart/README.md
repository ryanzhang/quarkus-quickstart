# metrics-quickstart project
## Metrics 使用Prometheus
### 启动Prometheus
```bash
./start_prometheus_podman.sh
```
启动Prometheus需要定义个需要监控的应用配置文件
```yaml
scrape_configs:
  - job_name: 'prometheus' 
    static_configs:
    - targets: ['localhost:9090']

  - job_name: 'application' 
    static_configs:
    - targets: ['10.88.0.1:8080']
```

访问: http://localhost:9090/

查看Prometheus监控的目标状态: http://localhost:9090/targets

如果只是查看metrics， 访问: http://localhost:9090/metrics

这里面对于application 不能解析localhost 或者127.0.0.1 因为Prom是运行在容器中， 而application是运行在host主机中。
所以对于application targets的地址 要么填写 host主机的外部ip，要么像我上面填写的是container的路由网关地址 通过进入container 调用ip route得到:
```bash
podman exec -it b195bd79fb52 /bin/sh
/prometheus $ ip route
default via 10.88.0.1 dev eth0 
10.88.0.0/16 dev eth0 scope link  src 10.88.0.25 
```
这里10.88.0.1就是容器的网关，即可以访问到主机的地址.

### 启动应用程序 并产生一些测试数据
```bash
curl http://localhost:8080/629521085409773
```

## OpenTracing using Jaeger
### 启动Jaeger
```bash
cat start_jaeger_podman.sh
#!/bin/bash
sudo podman run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:latest⏎

./start_jaeger_podman.sh
```
访问 JaegerUI: http://localhost:16686

### 配置应用程序
```properties
#配置应用出现在Jaeger中的名字
quarkus.jaeger.service-name=myapplication
quarkus.jaeger.sampler-type=const
#取样比例 0, 1 或者0.5等小数 如果是1表示 完全取样所有请求
quarkus.jaeger.sampler-param=1
#这里是通过日志记录, opentracing的记录信息
quarkus.log.console.format=%d{yyyy-MM-dd HH:mm:ss} %-5p traceId=%X{traceId}, spanId=%X{spanId}， sampled=%X{sampled} [%c{2.}] (%t) %s%e%n}
```
同样这几个参数可以通过命令行参数传入:
```bash
./mvnw compile quarkus:dev -Djvm.args="-DJAEGER_SERVICE_NAME=myservice -DJAEGER_SAMPLER_TYPE=const -DJAEGER_SAMPLER_PARAM=1"
```

启动Postgres
```bash
podman run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name postgresql_quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:11.3
```

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `metrics-quickstart-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/metrics-quickstart-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/metrics-quickstart-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.