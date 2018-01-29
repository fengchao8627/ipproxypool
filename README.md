# ipproxypool
爬虫的ip代理池 基于springboot mybatis


# 爬虫ip代理池

本项目是基于爬取代理网站上免费ip 而构建的ip池

## Getting Started 
* 项目是基于spring-boot 和mybatis 搭建而成的。 clone 项目下来， 导入intellij idea 或者是 eclipse(不常用)
* import sql 语句（后期 会用 flyway进行管理 ）
* 启动 intellij idea.          


* 当然可以不建数据库表，都是可以的，做了一个内存版本的，默认都是基于内存版（代理直接存在内存中） 
* clone 下来  mvn clean package , 放到Tomcat ,运行即可。
* 用idea 运行，稍微学习配置 idea。
*爬取过程中，需要等待1至2分钟，才能看到接口中的数据。不用心急。
*每次请求都要随机拿个一个ip port，从而避开代理被封。


### ip代理池思维导图

![alt text](https://github.com/wenchaomartin/ipproxy/blob/master/screenshots/ip%E4%BB%A3%E7%90%86%E6%B1%A0.png)



###  IP代理池的项目架构图

![alt text](https://github.com/wenchaomartin/ipproxy/blob/master/screenshots/%E7%A8%8B%E5%BA%8F%E7%BB%93%E6%9E%84%E7%9A%84%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)



## 技术点

* spring-boot
* mybatis
* mysql
* multi thead
* schedule task
* maven
* etc
