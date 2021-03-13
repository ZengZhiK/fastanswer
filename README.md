# FastAnswe—快答社区

> 本项目GitHub地址：https://github.com/ZengZhiK/fastanswer

## 在线演示地址

~~部署在腾讯云服务器上：http://81.70.197.22:8080/~~ (由于XSS攻击，目前无法访问)

~~希望各位小伙伴能在我的论坛系统上多多发言，帮助我测试一下系统，谢谢各位！~~

具体使用：

- GitHub登录 -> 提问 (Markdown语法、支持图片上传) -> 可在首页看到提问的问题
- GitHub登录 -> 点击任意问题 -> 可以发表回复
- GitHub登录 -> 点击头像 -> 可以查看自己发表的问题和别人的最新回复 (也可以点击小铃铛) 

## 本地运行手册

1、` git clone https://github.com/ZengZhiK/fastanswer.git `把本项目的代码拉取下来，之后右键IntelliJ IDEA 启动，等待maven自动加载包

2、使用MySQL数据库客户端软件（例如SQLyog、Navicat）执行脚本`sql/fastanswer.sql`创建fastanswer数据库

3、重命名`src/main/resources/`目录下的`application-backup.yml`为`application.yml`，其中`**`为需要填写的参数

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/application.yml.png"/>
</div>

4、填写响应`application.yml`参数

- MySQL数据库用户名和密码

- 注册GItHub OAuth Apps

  - 在Github主页找到Setting=>Developer settings=>OAuth Apps=>New OAuth App填写

  <div align="center">
  <img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/New OAuth App.png"/>
  </div>

  - 将Client ID和Client secrets拷贝到`application.yml`对应处

- 注册腾讯云账号，找到对象储存COS（新用户可以免费使用半年），创建储存桶

  - 可按这篇博客 [Spring boot 上传文件到腾讯云对象储存COS（完整步骤流程）](https://cloud.tencent.com/developer/article/1559746) 的步骤创建（注意要选择公有读私有写）
  
  - 在概览找到储存桶名称、所属地域、访问域名拷贝到`application.yml`对应处

  <div align="center">
  <img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/bucket.png"/>
  </div>

  - 在密钥管理找到SecretId和SecretKey拷贝到`application.yml`对应处

5、至此配置完毕，点击`com.zzk.fastanswer.FastanswerApplication`启动，在浏览器地址栏输入` http://localhost:8080/ `访问

## 技术栈

|      | 技术                | 链接                                                         |
| ---- | ------------------- | ------------------------------------------------------------ |
| 后端 | Spring Boot         | https://spring.io/                                           |
|      | MyBatis-Plus        | https://baomidou.com/                                        |
|      | MySql               | https://www.mysql.com/                                       |
|      | GitHub OAuth        | https://docs.github.com/en/free-pro-team@latest/developers/apps/building-oauth-apps |
|      | Hutool              | https://www.hutool.cn/                                       |
|      | fastjson            | https://github.com/alibaba/fastjson                          |
|      | Lombok              | https://www.projectlombok.org/                               |
|      | hibernate-validator | http://hibernate.org/validator/documentation/                |
|      | 腾讯云COS           | https://cloud.tencent.com/document/product/436               |
| 前端 | Bootstrap           | https://v3.bootcss.com/                                      |
|      | editor.md           | https://github.com/pandao/editor.md                          |
|      | jquery-validation   | https://github.com/jquery-validation/jquery-validation       |
|      | jquery.form         | http://malsup.com/jquery/form/                               |
|      | jQuery.scrollTo     | https://github.com/flesler/jquery.scrollTo                   |
|      | jQuery-Tags-Input   | https://github.com/xoxco/jQuery-Tags-Input                   |
|      | jquery-loading      | https://github.com/CarlosBonetti/jquery-loading              |

后端技术栈：

- 后端`MyBatis-Plus`操作数据库，包括代码生成器（代码在`src/test/java/com/zzk/fastanswer/mybatisplus/CodeGenerator.java`），个人感觉比MyBatis更简单好用。
- 社区设计仅支持GitHub登录，使用了`OAuth`协议，并使用`hutool`发起Http请求，`hutool`是一个Java工具包，其功能非常强大，其作者在B站也做过讲解[【Hutool】2020年全网独一份作者亲授使用教程！github上Star10.9K的开源项目工具！](https://www.bilibili.com/video/BV1bQ4y1M7d9?from=search&seid=5765265881528240719)
- 其他的包：`fastjson`用于操作json、`Lombok`用于简化Java Bean的开发、`hibernate-validator`用于参数校验，采用的是JSR 303: Bean Validation规范、腾讯云COS用于搭建图床，上传图片

前端技术栈：

- `Bootstrap`搭建页面（前端比较菜，写不出好看的页面，只是在老师的基础上简单修改了一下）
- `editor.md`Markdown编辑器js插件，比富文本编辑器强太多了
- `jquery-validation`表单校验、`jquery.form`异步提交表单、`jQuery.scrollTo`页面滚动插件、`  jQuery-Tags-Input`标签插件、`  jquery-loading`在页面显示加载状态

## 页面展示

- 首页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/index.png"/>
</div>

- 问题详情页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/question.png"/>
</div>

- 提问页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/publish.png"/>
</div>

提问支持Markdown语法、支持图片上传（腾讯云COS）


- 我的问题页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/my_question.png"/>
</div>

- 最新回复页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/latest_reply.png"/>
</div>

最新回复的个数是通过拦截器实现的，登录后每次请求都会先查询一下未读消息数，效率比较低......

点击未读徽章可以标记该回复为已读

- Error页

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/error.png"/>
</div>

## redis分布式session

- pom.xml引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

- 开启redis分布式session

```java
@Configuration
@EnableRedisHttpSession(redisNamespace = "redis:session")
public class RedisSessionConfig {
}
```

<div align="center">
<img src="https://cdn.jsdelivr.net/gh/ZengZhiK/PicBed/FastAnswe—快答社区/redis.png"/>
</div>