Tars是基于名字服务使用Tars协议的高性能RPC开发框架，同时配套一体化的服务治理平台，帮助个人或者企业快速的以微服务的方式构建自己稳定可靠的分布式应用
Tars的设计思路是采用微服务的思想对服务进行治理，同时对整个系统的各个模块进行抽象分层，将各个层次之间相互解耦或者松耦合
# springboot-tars
Tars：这个名字取自于电影"星际穿越"中的机器人，它是基于名字服务使用Tars协议的高性能RPC开发框架，配套一体化的运营管理平台，并通过伸缩调度，实现运维半托管服务。 Tars是腾讯从2008年到今天一直在使用的后台逻辑层的统一应用框架TAF（Total Application Framework），目前支持C++,Java,PHP,Nodejs,Go语言。该框架为用户提供了涉及到开发、运维、以及测试的一整套解决方案，帮助一个产品或者服务快速开发、部署、测试、上线。 它集可扩展协议编解码、高性能RPC通信框架、名字路由与发现、发布监控、日志统计、配置管理等于一体，通过它可以快速用微服务的方式构建自己的稳定可靠的分布式应用，并实现完整有效的服务治理。2014年开始开源，地址：https://github.com/Tencent/Tars

感觉和SpringCloud有些相似，不过内部有自己的规范，基于rpc实现的服务与服务之间的远程调用，而cloud的远程调用是基于http的

创建完hello.tars文件后,  在项目路径下执行mvn tars:tars2java 命令  ，会自动在pom文件中所配置的servant路径生成一个带注解的接口

```xml
	<!--提供插件编译生成java代码，在tars-maven-plugin添加生成java文件配置-->
			<plugin>
				<groupId>com.tencent.tars</groupId>
				<artifactId>tars-maven-plugin</artifactId>
				<version>1.6.1</version>
				<configuration>
					<tars2JavaConfig>
						<!-- tars文件位置 -->
						<tarsFiles>
							<tarsFile>${basedir}/src/main/resources/tars/hello.tars</tarsFile>
						</tarsFiles>
						<!-- 源文件编码 -->
						<tarsFileCharset>UTF-8</tarsFileCharset>
						<!-- 生成服务端代码 -->
						<servant>true</servant>   <!--true为 服务   false为client-->
						<!-- 生成源代码编码 -->
						<charset>UTF-8</charset>
						<!-- 生成的源代码目录 -->
						<srcPath>${basedir}/src/main/java</srcPath>
						<!-- 生成源代码包前缀 -->
						<packagePrefixName>com.zhoufu.springboottars.tars.server</packagePrefixName> <!--这里是生成servant或者client端代码的包的前缀-->
					</tars2JavaConfig>
				</configuration>
			</plugin>
```
只要注意这里：
```xml
<!-- 生成服务端代码 -->
<servant>true</servant>   <!--true为 服务   false为client-->

<packagePrefixName>com.zhoufu.springboottars.tars.server</packagePrefixName> <!--这里是生成servant或者client端代码的包的前缀-->
```
通过修改路径，然后执行 mvn tars:tars2java 命令; 
则可以生成相应的服务端 接口：
```java
package com.zhoufu.springboottars.tars.server.testapp;

import com.qq.tars.protocol.annotation.*;
import com.qq.tars.protocol.tars.annotation.*;
import com.qq.tars.common.support.Holder;
/**
* 我们发现刚刚tars文件里面写的TestApp变成了报名，生成的接口名为HelloServant
*/
@Servant
public interface HelloServant {
 
	public String hello(int no, String name);
}
```
然后我们编写一个实现类，加上注解@TarsServant,并指定servant的名字为helloObj即可使用服务端接口

生成客户端代码：
```java
@Servant
public interface HelloPrx {
	public String hello(int no, String name);
	public String hello(int no, String name, @TarsContext java.util.Map<String, String> ctx);
	public void async_hello(@TarsCallback HelloPrxCallback callback, int no, String name);
	public void async_hello(@TarsCallback HelloPrxCallback callback, int no, String name, @TarsContext java.util.Map<String, String> ctx);
}

public abstract class HelloPrxCallback extends TarsAbstractCallback {
	public abstract void callback_hello(String ret);
}
```

然后编写controller， 进行对外提供接口：
    （后面的objName是在部署在服务器上面过后的obj全名，直接返回helloworld字符串也可以，
    我是http接口对外成功了的再试试调用tars接口的，这里是使用的客户端的代理对象来进行调用，
    服务与服务之间也可以这样子调用）
```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String testHello(@RequestParam Integer no){
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator();
        HelloPrx helloPrx = communicator.stringToProxy(HelloPrx.class, "Test.TestHelloServer.helloObj");
        return helloPrx.hello(no, "springBoot To tars say hello world");
    }
}
```

编写项目的启动类(这两个注解一定要，@EnableTarsServer保证部署的jar包可以用，@TarsHttpService对外提供http服务)
```java
@SpringBootApplication
//通过注解@EnableTarsServer标识这是一个TARS服务，并开启服务相关功能
//在spring-boot中，需要通过注解开启tars服务相关功能
@EnableTarsServer
@TarsHttpService("httpObj")
public class SpringbootTarsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootTarsApplication.class, args);
	}
}
```

打包发布  mvn 打包工具package



