# activiti&spring 事务

在测试activiti事务的时候，发现Test类中的testMethod只要方法上加上@Trasational注解，所做的任何操作都会被回滚

如：

     @Test
        @Transactional
        public void testTransaction(){
            Person person = personRepository.findByUsername("sam2");
    
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("person", person);
            runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess");
            System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
        }

会看到控制台日志打印如下字眼：

    o.s.t.c.transaction.TransactionContext   : Rolled back transaction for xxxx
    
然后到数据库里一看，一条数据都没有被插入好修改，虽然后台打印了sql，也没有报错。


# activiti 并发性测试

目标：
同时100个不同的人同时发起流程，流程是否会乱？





## springboot-activiti多数据源配置

新增配置

    @Configuration
    public class ActivitiDatasourceConfig extends AbstractProcessEngineAutoConfiguration {
    
        @Bean
        @Primary
        @ConfigurationProperties(prefix = "spring.datasource.activiti")
        public DataSource activitiDataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean
        public SpringProcessEngineConfiguration springProcessEngineConfiguration(
                PlatformTransactionManager transactionManager,
                SpringAsyncExecutor springAsyncExecutor) throws IOException {
    
            return baseSpringProcessEngineConfiguration(
                    activitiDataSource(),
                    transactionManager,
                    springAsyncExecutor);
        }
    
    }

### primary注解

表示该数据源为主要的默认数据源，其他数据源需要自己额外指定事务管理器。




## 关闭job

JobExecutor是管理一系列线程的组件，可以触发定时器（也包含后续的异步消息）。 在单元测试场景下，很难使用多线程。因此API允许查询(ManagementService.createJobQuery)和执行job (ManagementService.executeJob)，所以job可以在单元测试中控制。 要避免与job执行器冲突，可以关闭它。

默认，JobExecutor在流程引擎启动时就会激活。 如果不想在流程引擎启动后自动激活JobExecutor，可以设置：

    spring.activiti.jobExecutorActivate=false


## Spring Boot Actuator

[actuator详情](http://www.jianshu.com/p/af9738634a21)

找到一个activiti官方文档的中文翻译，感谢作者tkjohn：

[activiti官方文档翻译](https://tkjohn.github.io/activiti-userguide/)