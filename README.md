<p style="text-align: center">
  <img width="128px" height="128px" src="https://virtualfab.top/bloger/logo.png" alt="">
</p>

<h1 style="text-align: center">Bloger</h1>

<p style="text-align: center">
  一个新的现代化博客系统.
</p>

## 🌟 描述
Bloger 是一个前后端分离的博客系统, 这是博客系统的后端, 前端 [在这里](https://github.com/neillee95/bloger-ui). 
后端使用 Kotlin 开发, 并结合了 Spring Boot, Spring WebFlux, Spring Security, Kotlin Coroutines等, 使用 MongoDB 持久化数据. 
它不仅仅是一个博客系统, 也是一个 Kotlin + Spring WebFlux + Kotlin Coroutines 的示例程序. 更多 Spring 对 Kotlin 的支持, 
可以[看这里](https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html).

## 🌟 特性
- 快速开发, 可以通过 Docker Compose 和 Google Jib 快速部署
- 简单使用, UI美观
- 更多先进的语言特性, Kotlin & Coroutines, Spring WebFlux 等

## 🌟 使用
推荐使用 Docker Compose 部署, [deploy](./deploy) 中包含所需的 compose 文件, 另外为了保证安全性, 
在启动前先修改 [application-compose.yml](./deploy/application-compose.yml) 中的 secret,
```yaml
jwt:
  secret: yourcustomsecret -> 修改 yourcustomsecret
```
启动
```bash
docker-compose up -d
```
停止
```bash
docker-compose stop
```
更多 Docker Compose 的使用, 请看[文档](https://docs.docker.com/compose/).

如果你想构建自己的 Docker 镜像, 推荐使用 Google Jib, 项目中已经使用了
Jib Gradle 插件.\
构建本地镜像,
```bash
./gradlew jibDockerBuild --image=IMAGE -xtest
```
构建镜像并推送到 docker hub,
```bash
./gradlew jib --image=IMAGE -x test
```
要注意一点, 通过 `gradle jib` 构建镜像时并不会生成本地镜像, 而是生成 image layer, 这些 layer 在 build/jib-cache中, 
如果要构建本地镜像, 请使用 `gradle jibDockerBuild`. 另外还有一点, 
push 使用的认证方法是 [Docker Credential Helpers](https://github.com/docker/docker-credential-helpers), push 前请先配置 Docker Credential Helpers,
或者修改 [build.gradle.kts](./build.gradle.kts) 认证方式为 auth, 
```kotlin
jib {
  // from
  to {
    // image
    auth {
      username = USERNAME
      password = PASSWORD
    }
  }
}
```
更多 Google Jib 使用, 请参考[Jib](https://github.com/GoogleContainerTools/jib), 
[jib-gradle-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin).

或者也可以不使用 Jib 构建, 这里提供了用来构建镜像的 [Dockerfile](./Dockerfile), 
使用 docker 命令构建并推送到 hub, 
```bash
gradle clean build -xtest
mkdir -p build/dependency && (cd build/dependency || exit; jar -xf ../libs/*.jar)
sudo docker build -t IMAGE .
sudo docker push IMAGE
```

不建议使用 `java` 命令直接启动, 如果是这样, 你需要重新构建前端或者通过代理服务器比如 Nginx 转发请求.

## 🌟 示例
[Lee's Blog](https://blog.virtualfab.top)

## 🌟 开源协议
MIT