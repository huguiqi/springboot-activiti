# springboot-activiti

   spring boot + web +  mybatis + H2



工作流Activiti，在这里不作介绍了。
可以参考这几篇文章：

http://www.cnblogs.com/gcjava/archive/2017/04/19/6734793.html

http://blog.csdn.net/xnf1991/article/details/52610277

[springboot集成](http://bubuko.com/infodetail-1465670.html)


根据官方教程，先写一个demo

[官方教程quick start](https://www.activiti.org/quick-start)

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

[spring官方文档](https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot)

[activiti官方文档](https://www.activiti.org/userguide/#springSpringBoot)
   