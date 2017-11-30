# springboot-activiti

   spring boot + web +  mybatis + H2



工作流Activiti，在这里不作介绍了。
可以参考这几篇文章：

http://www.cnblogs.com/gcjava/archive/2017/04/19/6734793.html

http://blog.csdn.net/xnf1991/article/details/52610277

[springboot集成](http://bubuko.com/infodetail-1465670.html)


根据官方教程，先写一个demo

[官方教程quick start](https://www.activiti.org/quick-start)
[官方用户手册](https://www.activiti.org/userguide/)

什么？文档写的太烂，很多英文写的不详细？那就看看5.x的中文手册

[Activiti5.6中文手册](http://www.mossle.com/docs/activiti/index.html#demo.setup.one.minute.version)



[Activiti请假例子](http://www.mossle.com/docs/activiti/index.html#configuration)

我配置了一个mysql5.7.20，跑一个actiti引擎会初始化28张表。

分别是：

        act_evt_log, 
        act_ge_bytearray, 
        act_ge_property, 
        act_hi_actinst, 
        act_hi_attachment, 
        act_hi_comment, 
        act_hi_detail, 
        act_hi_identitylink, 
        act_hi_procinst, 
        act_hi_taskinst, 
        act_hi_varinst, 
        act_id_group, 
        act_id_info,
        act_id_membership, 
        act_id_user, 
        act_procdef_info, 
        act_re_deployment, 
        act_re_model, 
        act_re_procdef, 
        act_ru_deadletter_job, 
        act_ru_event_subscr, 
        act_ru_execution, 
        act_ru_identitylink, 
        act_ru_job, 
        act_ru_suspended_job, 
        act_ru_task, 
        act_ru_timer_job, 
        act_ru_variable
        
        
这么多表，看着一脸懵逼。


## 先来了解这些表

Activiti的后台是有数据库的支持，所有的表都以ACT_开头。 第二部分是表示表的用途的两个字母标识。用途也和服务的API对应。
1)       ACT_RE_*: 'RE'表示repository。 这个前缀的表包含了流程定义和流程静态资源（图片，规则，等等）。
2)       ACT_RU_*: 'RU'表示runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Activiti只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
3)       ACT_ID_*: 'ID'表示identity。 这些表包含身份信息，比如用户，组等等。
4)       ACT_HI_*: 'HI'表示history。 这些表包含历史数据，比如历史流程实例，变量，任务等等。
5)       ACT_GE_*: 通用数据， 用于不同场景下。
资源库流程规则表
　　1)      act_re_deployment     部署信息表
　　2)    act_re_model    流程设计模型部署表
　　3)    act_re_procdef       流程定义数据表
运行时数据库表
　　1)    act_ru_execution      运行时流程执行实例表
　　2)    act_ru_identitylink    运行时流程人员表，主要存储任务节点与参与者的相关信息
　　3)    act_ru_task       运行时任务节点表
　　4)    act_ru_variable  运行时流程变量数据表
　　5)    act_ru_timer_job  ？
　　6)    act_ru_suspended_job  ？
　　7)    act_ru_event_subscr  ？
　　8)    act_ru_deadletter_job  ？
　　9)    act_ru_job  ？

历史数据库表
　　1)    act_hi_actinst          历史节点表
　　2)    act_hi_attachment          历史附件表
　　3)    act_hi_comment       历史意见表
　　4)    act_hi_identitylink           历史流程人员表
　　5)    act_hi_detail             历史详情表，提供历史变量的查询
　　6)    act_hi_procinst        历史流程实例表
　　7)    act_hi_taskinst         历史任务实例表
　　8)    act_hi_varinst          历史变量表 
组织机构表
　　1)    act_id_group           用户组信息表
　　2)    act_id_info        用户扩展信息表
　　3)    act_id_membership  用户与用户组对应信息表
　　4)    act_id_user        用户信息表
　　这四张表很常见，基本的组织机构管理，关于用户认证方面建议还是自己开发一套，组件自带的功能太简单，使用中有很多需求难以满足
通用数据表
　　1)    act_ge_bytearray           二进制数据表
　　2)    act_ge_property       属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录

act_procdef_info
act_evt_log


## 以请假流程为例写一个demo

    java\com\example\demo\VacationRequestTest.java
    
   

# activiti5.x VS activiti6.x

想要快速入门，activiti5.x的explorer.war部署后可以看到数据库中的流程与变化


# springboot集成



新建一个project,springboot-activiti

pom:

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.example</groupId>
        <artifactId>activiti-demo2</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <packaging>jar</packaging>
    
        <name>springboot-activiti6</name>
        <description>Demo project for Spring Boot</description>
    
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.6.RELEASE</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
    
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
            <java.version>1.8</java.version>
            <activiti.version>6.0.0</activiti.version>
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-aop</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-basic</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-rest-api</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-actuator</artifactId>
                <version>${activiti.version}</version>
            </dependency>
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-jpa</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
            
            <!-- Use MySQL Connector-J -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.44</version>
            </dependency>
    
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <optional>true</optional>
            </dependency>
    
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.30</version>
            </dependency>
    
            <dependency>
                <groupId>commons-beanutils</groupId>
                <artifactId>commons-beanutils</artifactId>
                <version>1.9.2</version>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>
    
    
    
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    
    
    </project>



## 默认url访问鉴权

其中，activiti-spring-boot-starter-rest-api 默认对RestController的url会进行basic authentication(权限验证),调用post方式的rest地址时需要输入用户名密码，

而此用户名和密码需要在springboot的MyActivitiApp类中加上：

    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {

                Group group = identityService.newGroup("user");
                group.setName("users");
                group.setType("security-role");
                identityService.saveGroup(group);

                User admin = identityService.newUser("admin");
                admin.setPassword("admin");
                identityService.saveUser(admin);

            }
        };
    }



其中，`identityService.newGroup("user");`
是在 `act_id_group`表中插入了一条用户组为user的数据，类型是`security-role`

    User admin = identityService.newUser("admin");
    admin.setPassword("admin");
    identityService.saveUser(admin);`
                 
 是在 `act_id_user` 表中增加一条用户名和密码为admin的用户。
 

用curl访问时:

    curl -u admin:admin -H "Content-Type: application/json" -d '{"userName" : "sam"}' http://localhost:8080/process


## 忽略url访问鉴权


在springboot的启动类的注解上加上如下：

    @SpringBootApplication(exclude = {
            org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
            org.activiti.spring.boot.SecurityAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
    })
    public class MyActivitiApp {
        .....
    }



然后再访问时就不需要加用户和密码了，直接访问(post请求，不指定请求方式的情况下指全部)：

    curl -H "Content-Type: application/json" -d '{"userName" : "sam"}' http://localhost:8080/process




## 配置数据源

application.properties:

    spring.datasource.driverClassName = com.mysql.jdbc.Driver
    spring.datasource.url = jdbc:mysql://localhost:3306/activiti2?useUnicode=true&characterEncoding=utf-8
    spring.datasource.username = root
    spring.datasource.password = 123456
    
    spring.jpa.hibernate.ddl-auto=update


activiti遵循springboot的配置。



## JPA接口
repository需要继承父类JpaRepository，遵循接口泛形定义。

以本例子repository为例：


    public interface PersonRepository extends JpaRepository<Person, Long> {
    
        Person findByUsername(String username);
    
    }


默认是用hibernate实现的持久层。

但是公司项目都是mybatis，而activiti内部就是用ibatis实现的，那么应该对mybatis的支持更好。



## mybatis集成

pom.xml:

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.example</groupId>
        <artifactId>activiti-demo-mybatis</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <packaging>jar</packaging>
    
        <name>springboot-activiti-mybatis</name>
        <description>Demo project for Spring Boot</description>
    
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.6.RELEASE</version>
            <relativePath/> <!-- lookup parent from repository -->
        </parent>
    
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
            <java.version>1.8</java.version>
            <activiti.version>6.0.0</activiti.version>
            <oracle.version>11.2.0.3</oracle.version>
            <!--<oracle.version>11.2.0.4.0-atlassian-hosted</oracle.version>-->
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-aop</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>1.3.0</version>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-basic</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <!--<artifactId>spring-boot-starter-rest-api</artifactId>-->
                <artifactId>activiti-spring-boot-starter-rest-api</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
            <dependency>
                <groupId>org.activiti</groupId>
                <artifactId>activiti-spring-boot-starter-actuator</artifactId>
                <version>${activiti.version}</version>
            </dependency>
    
    
            <dependency>
                <groupId>com.h2database</groupId>
                <artifactId>h2</artifactId>
                <scope>runtime</scope>
            </dependency>
    
            <!-- Use Oracle -->
            <dependency>
                <groupId>com.oracle</groupId>
                <artifactId>ojdbc6</artifactId>
                <version>${oracle.version}</version>
            </dependency>
    
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <optional>true</optional>
            </dependency>
    
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.30</version>
            </dependency>
    
            <dependency>
                <groupId>commons-beanutils</groupId>
                <artifactId>commons-beanutils</artifactId>
                <version>1.9.2</version>
            </dependency>
    
    
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-api</artifactId>
                <version>1.7.21</version>
            </dependency>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-log4j12</artifactId>
                <version>1.7.21</version>
            </dependency>
    
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>
    
    
    
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
    </project>




## 查看审核流程表(png)

一个流程发布后，activiti都会将bpmn文件和生成的png文件存在数据库中，就是`act_ge_bytearray` 这张表。


取的时候就这样：


@Test
    public void getImageById()throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream= processEngine.getRepositoryService()
                .getResourceAsStream("27501", "processes/VacationTest.VocationProcess.png"); // 根据流程部署ID和资源名称获取输入流
        FileUtils.copyInputStreamToFile(inputStream, new File("D:/helloWorld.png"));
    }




## 获取流程图中文乱码问题


Spring Boot是微服务快速开发框架，强调的是零配置，显然不推荐采用XML配置文件的方式解决。不使用Spring Boot的时候，

是通过下面这种方式就可以解决：

* ①原始解决方式：在Spring配置文件中的


    <bean id="processEngineConfiguration" class="org.activiti.spring.SpringProcessEngineConfiguration"> 

中加入两行代码：

    <property name="activityFontName" value="宋体"/>

    <property name="labelFontName" value="宋体"/>

配置如下：

    <bean id="processEngineConfiguration"
    class="org.activiti.spring.SpringProcessEngineConfiguration">
    <property name="activityFontName" value="宋体"/>
    <property name="labelFontName" value="宋体"/>
    <property name="dataSource" ref="dataSource" />
    <property name="transactionManager" ref="transactionManager" />
    <property name="databaseSchemaUpdate" value="true" />
    <property name="mailServerHost" value="localhost" />
    <property name="mailServerPort" value="5025" />
    <property name="jpaHandleTransaction" value="true" />
    <property name="jpaCloseEntityManager" value="true" />
    <property name="jobExecutorActivate" value="false" />
    </bean>


* ②Spring Boot中我采用的解决办法是，生成流程图的时候设置字体和编码信息解决，如下

        
        @Test
            public void genPic() throws Exception {
                ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
                ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().deploymentId("27503").singleResult();
                BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(pi.getProcessDefinitionId());
                List<String> activeIds = processEngine.getRuntimeService().getActiveActivityIds(pi.getId());
                ProcessDiagramGenerator p = new DefaultProcessDiagramGenerator();
                InputStream is = p.generateDiagram(bpmnModel, "png", activeIds, Collections.<String> emptyList(),"", "宋体", "宋体",null,
                         1.0);
        
                File file = new File("D:/process.png");
                OutputStream os = new FileOutputStream(file);
        
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
        
                os.close();
                is.close();
      }



## 数据源换成oracle

因公司用的是oracle数据库，所以现在把数据源改成oracle


### 自增ID的处理方式

oracle:

* mybatis insert时的自增ID需要用 SEQUENCE，而对应的注解需要配置selectKey。

        
        @SelectKey(statement = "SELECT SEQ_PERSON.Nextval as ID from DUAL",resultType = Long.class,before =

mysql:

* mybatis insert时的自增ID需要加Options注解里如下写：
 
    
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "ID")






参考如下文章：

[spring官方文档](https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot)

[activiti官方文档](https://www.activiti.org/userguide/#springSpringBoot)
   
   
[参考](http://www.cnblogs.com/haore147/p/5213643.html)   


[activiti从入门到精通](http://www.kafeitu.me/activiti-in-action.html)


 
1，设计请假流程
1）使用流程设计器完成
2）导出流程xml
2，开始编写流程
3，查询流程状态
4，操作流程
5，集成mybatis
6，将mysql换成oracle11g

7，并发测试，线程安全
8，事务
9，跟踪流程
10，多版本问题【如果 之前没有走完的流程，还是按原来的流程走，新开始的流程就按新发布的走】

 