# 快速使用activiti

## springboot集成activiti

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



**配置数据源**

application.properties:

    spring.datasource.driverClassName = com.mysql.jdbc.Driver
    spring.datasource.url = jdbc:mysql://localhost:3306/activiti2?useUnicode=true&characterEncoding=utf-8
    spring.datasource.username = root
    spring.datasource.password = 123456

    spring.jpa.hibernate.ddl-auto=update


activiti遵循springboot的配置。



## 八大接口使用

![八大接口](002.png)




### RuntimeService


### TaskService


###