[TOC]

------

对于开发同学来说，Spring 框架熟悉又陌生。 熟悉：开发过程中无时无刻不在使用 Spring 的知识点；陌生：对于基本理论知识疏于整理与记忆。导致很多同学面试时对于 Spring 相关的题目知其答案，但表达不够完整准确。

![](https://upload-images.jianshu.io/upload_images/11474088-f0207c44de625a8e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# **一、基本概念面试题集（ Spring 相关概念梳理）**

1.  谈谈对 Spring IoC 的理解？
IoC(Inversion of Control),即控制反转，它是一种设计思想，一个重要的面向对象编程的法则，它能指导我们如何设计出松耦合、更优良的程序。传统应用程序都是由我们在类内部主动创建依赖对象，
从而导致类与类之间高耦合，难于测试；有了IoC容器后，把创建和查找依赖对象的控制权交给了容器，由容器进行注入组合对象，所以对象与对象之间是 松散耦合，这样也方便测试，利于功能复用，
更重要的是使得程序的整个体系结构变得非常灵活。
对于spring框架来说，就是由spring来负责控制对象的生命周期和对象间的关系。

2.  谈谈对 Spring DI 的理解？
DI—Dependency Injection，即“依赖注入”：组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。
依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。
可以看出IoC是一种设计的思想，而DI就是这种思想的一种具体实现

3.  BeanFactory 接口和 ApplicationContext 接口不同点是什么？
BeanFactory是Spring里面最低层的接口，提供了最简单的容器的功能，只提供了实例化对象和获取对象的功能；
ApplicationContext又叫应用上下文，它继承了BeanFactory接口，它是Spring的一个更高级的容器，提供了更多的有用的功能；
1) 国际化（MessageSource）
2) 访问资源，如URL和文件（ResourceLoader）
3) 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层  
4) 消息发送、响应机制（ApplicationEventPublisher）
5) AOP（拦截器）

4.  请介绍你熟悉的 Spring 核心类，并说明有什么作用？
org.springframework.context.support.AbstractApplicationContext类中refresh方法定义了spring刷新时的模板
org.springframework.beans.factory.support.DefaultListableBeanFactory，保存了所有需要spring管理的类的定义集合beanDefinitionMap
org.springframework.beans.factory.support.DefaultSingletonBeanRegistry 保存了1、2、3级缓存

5.  介绍一下 Spring 的事务的了解？
事务具备ACID四种特性
四种隔离级别
七种事务传播机制（默认required）,以方法A和方法B发生嵌套调用时如何传播事务为例说明:
PROPAGATION_REQUIRED：A如果有事务，B将使用该事务；如果A没有事务，B将创建一个新的事务
PROPAGATION_SUPPORTS：A如果有事务，B将使用该事务；如果A没有事务，B将以非事务执行
PROPAGATION_MANDATORY：A如果有事务，B将使用该事务；如果A没有事务，B将抛异常
PROPAGATION_REQUIRES_NEW：A如果有事务，将A的事务挂起，B创建一个新的事务；如果A没有事务，B创建一个新的事务
PROPAGATION_NOT_SUPPORTED：A如果有事务，将A的事务挂起，B将以非事务执行；如果A没有事务，B将以非事务执行
PROPAGATION_NEVER：A如果有事务，B将抛异常；A如果没有事务，B将以非事务执行
PROPAGATION_NESTED：A和B底层采用保存点机制，形成嵌套事务


6.  介绍一下 Spring 的事务实现方式？
实现方式共有两种：编程式事务和声明式事务管理方式。
编程式事务管理通过TransactionTemplate手动管理事务
声明式事务管理有三种实现方式：基于TransactionProxyFactoryBean的方式、基于AspectJ的XML方式、基于注解的方式

7.  解释 AOP 模块

8.  Spring 的通知类型有哪些，请简单介绍一下？
（1）前置通知（Before advice）：在某连接点（join point）之前执行的通知，但这个通知不能阻止连接点前的执行（除非它抛出一个异常）。
（2）返回后通知（After returning advice）：在某连接点（join point）正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回。
（3）抛出异常后通知（After throwing advice）：在方法抛出异常退出时执行的通知。
（4）后通知（After (finally) advice）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
（5）环绕通知（Around Advice）：包围一个连接点（join point）的通知，如方法调用。这是最强大的一种通知类型。 环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行。 环绕通知是最常用的一种通知类型。大部分基于拦截的AOP框架，例如Nanning和JBoss4，都只提供环绕通知。

9.  Spring 通知类型使用场景分别有哪些？
前置通知    记录日志(方法将被调用) 
环绕通知    控制事务 权限控制
后置通知    记录日志(方法已经成功调用)
异常通知    异常处理 控制事务
最终通知    记录日志(方法已经调用，但不一定成功)

10.  请介绍一下你对 Spring Beans 的理解?

11.  Spring 有哪些优点?
1.控制反转将对象的创建交给了spring,简化了开发，降低了代码之间的耦合性和侵入性。
2.方便对程序进行声明式事物管理，我们只需通过配置就可以完成对事物管理。
3.方便集成各种优秀的框架，spring不排斥各种优秀框架，其内部提供了对各种优秀框架如(struts2，hibernate,mybatis,quartz，jpa)等的直接支。
4.方便对程序进行测试，spring对于Junit4的支持，可通过注解方便测试程序。
5.降低了JavaEE API的使用难度，JDBC,Javamail,远程调用等，spring对它们进行了封装，使这些API的使用难度大大降低。

12.  在Spring中使用hibernate的方法步骤

13.  Spring 和 Struts 的区别？

14.  Spring 框架由那几部分组成？
1.Spring Core
Core封装包是框架的最基础部分，提供IOC和依赖注入的特性。这里的基础概念是BeanFactory，它提供对Factory模式的经典实现来消除对程序性单例模式的需要，并真正地允许你从程序逻辑中分离出依赖关系和配置。
2.Spring Context
构建于Core封装包基础上的Context封装包,提供了一种框架式的对象访问方法，有些像JNDI注册器。Context封装包的特性得自于Beans封装包，并添加了对国际化（I18N）的支持（例如资源绑定），事件传播，资源装载的方式和Context的透明创建，比如说通过Servlet容器。
3.Spring DAO
DAO(Data Access Object)提供了JDBC的抽象层，它可消除冗长的JDBC编码和解析数据库厂商特有的错误代码。并且，JDBC封装包还提供了一种比编程性更好的声明性事务管理方法，不仅仅是实现了特定接口，而且对所有的POJOs（plain old Java objects）都适合。
4.Spring ORM
ORM封装包提供了常用的“对象/关系”映射APIs的集成层。其中包括JPA、JDO、Hibernate 和myiBatis。利用ORM封装包，可以混合使用所有Spring提供的特性进行“对象/关系”映射，如期边提到的简单声明性事务管理。
5.Spring AOP
AOP模块是Spring的AOP库，提供了AOP（拦截器）机制，并提供常用的拦截器，提供用户自定义和配置。
6.Spring Web
WEB模块提供对常见框架如Struts1,WEBWORK（Struts2）,JSF的支持，Spring能够管理这些框架，将Spring的资源注入给框架，也能在这些框架的前后插入拦截器
7.Spring MVC
Spring中的MVC封装包提供了Web应用的Model-View-Controller（MVC）实现。Spring的MVC框架并不是仅仅提供一种传统的实现，它提供了一种清晰的分离模型，在领域模式代码和Web Form 之间。并且，还可以借助Spring框架的其他特性。

15.  谈谈你对 BeanFactory的理解，BeanFactory 实现举例

16.  谈谈对 Spring 中的 Web 模块的理解

17.  BeanFactory 和 Application contexts 有什么区别？

18.  谈谈你对 Spring 依赖注入的理解？

19.  什么是 Bean 装配?
是指在Spring 容器中把bean组装到一起

20.  什么是 Bean 的自动装配？
Spring 容器能够自动装配相互合作的bean，这意味着容器不需要<constructor-arg>和<property>配置，能通过Bean工厂自动处理bean之间的协作

21.  介绍一下自动装配有几种方式？
在Spring中，我们有4种方式可以装配Bean的属性。
1，byName
通过byName方式自动装配属性时，是在定义Bean的时候，在property标签中设置autowire属性为byName，那么Spring会自动寻找一个与该属性名称相同或id相同的Bean，注入进来。
2，byType
通过byType方式自动注入属性时，是在定义Bean的时候，在property标签中设置autowire属性为byType，那么Spring会自动寻找一个与该属性类型相同的Bean，注入进来。
3，constructor
通过构造器自动注入。在定义Bean时，在bean标签中，设置autowire属性为constructor，那么，Spring会寻找与该Bean的构造函数各个参数类型相匹配的Bean，通过构造函数注入进来。
4，autodetect
自动装配。如果想进行自动装配，但不知道使用哪种类型的自动装配，那么就可以使用autodetect，让容器自己决定。这是通过在定义Bean时，设置bean标签的autowire属性为autodetect来实现的。设置为autodetect时，Spring容器会首先尝试构造器注入，然后尝试按类型注入。
默认情况下，Spring是不进行自动装配的。我们可以在xml中，设置beans标签的default-autowire属性为byName，byType等，来设置所有bean都进行自动装配。

22.  什么是基于注解的容器配置?
不使用XML来描述bean装配，开发人员通过在相关的类，方法或字段声明上使用注解将配置移动到组件类本身,例如：Spring 的 Java 配置是通过使用 @Bean 和 @Configuration 来实现。

23.  简述 JdbcTemplate 类的作用

24.  解释 AOP

25.  解释 Aspect 切面

26.  简述 Spring AOP 中的通知

27.  Spring AOP 中的织入你怎样理解？

28.  请详细介绍一下 Spring MVC 的流程？

29.  Spring 配置文件?

30.  @RequestMapping 注解用在类上面有什么作用

31.  怎么样把某个请求映射到特定的方法上面

32.  谈谈 Spring 对 DAO 的支持

> ![阿里Java二面问Spring哑口无言？100道Spring面试考点解析，请查收](https://upload-images.jianshu.io/upload_images/11474088-17e0c578a696032a?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# **二、应用场景面试题集（各知识点不同使用场景选型）**

1.  Spring 配置 Bean 实例化有哪些方式？

2.  Bean 注入属性有哪几种方式

3.  在 Spring 中如何实现时间处理？

4.  Spring 中如何更高效的使用 JDBC ？

5.  请介绍一下设计模式在 Spring 框架中的使用？

6.  讲讲 Spring 框架的优点有哪些？

7.  哪种依赖注入方式你建议使用，构造器注入，还是 Setter 方法注入？

8.  你怎样定义类的作用域?

9.  解释 Spring 支持的几种 Bean 的作用域
singleton：单例模式，在整个Spring IoC容器中，使用 singleton 定义的 bean 只有一个实例
prototype：原型模式，每次通过容器的getbean方法获取 prototype 定义的 bean 时，都产生一个新的 bean 实例
request：每次http请求都会创建一个bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。
session：在一个HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。
global-session：在一个全局的HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。
缺省的Spring bean 的作用域是Singleton.

10.  在 Spring 中如何注入一个 Java 集合？

11.  你可以在 Spring 中注入一个 null 和一个空字符串吗？

12.  什么是基于 Java 的 Spring 注解配置? 给一些注解的例子

13.  你更倾向用那种事务管理类型？

14.  Bean 的调用方式有哪些？

15.  Spring MVC 里面拦截器是怎么写的

16.  当一个方法向 AJAX 返回特殊对象，譬如 Object、List 等，需要做什么处理?

17.  如何使用 Spring MVC 完成 JSON 操作

18.  Spring 如何整合 Hibernate

19.  Spring 如何整合 Struts2 ?

20.  开发中主要使用 Spring 的什么技术 ?

21.  介绍一下 Spring MVC 常用的一些注解

22.  Spring 框架的事务管理有哪些优点

![阿里Java二面问Spring哑口无言？100道Spring面试考点解析，请查收](https://upload-images.jianshu.io/upload_images/11474088-709db97a18fd62aa?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# **三、深度度知识面试题集（底层实现原理详解）**

1.  IoC 控制反转设计原理？

2.  Spring 的生命周期？

3.  Spring 如何处理线程并发问题？

4.  核心容器（应用上下文）模块的理解？

5.  为什么说 Spring 是一个容器？

6.  Spring 的优点？

7.  Spring 框架中的单例 Beans 是线程安全的么？

8.  Spring 框架中有哪些不同类型的事件？

9.  IoC 的优点是什么？

10.  解释 Spring 框架中 Bean 的生命周期

11.  什么是 Spring 的内部 Bean？

12.  自动装配有哪些局限性 ?

13.  Spring 框架的事务管理有哪些优点？

14.  在 Spring AOP 中，关注点和横切关注的区别是什么？

15.  说说 Spring AOP 的底层实现原理？

16.  如何给 Spring 容器提供配置元数据?

17.  哪些是重要的 Bean 生命周期方法？ 你能重载它们吗？

18.  讲下 Spring MVC 的执行流程

19.  Spring MVC 的控制器是不是单例模式,如果是,有什么问题,怎么解决？

20.  Spring 中循环注入的方式？

21.  Spring MVC 比较 Struts2

![阿里Java二面问Spring哑口无言？100道Spring面试考点解析，请查收](https://upload-images.jianshu.io/upload_images/11474088-5bf885a659b297d9?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# **四、拓展内容面试题集（Spring Boot 相关题集）**

1.  什么是 Spring Boot？

2.  Spring Boot 自动配置的原理？

3.  Spring Boot 读取配置文件的方式?

4.  什么是微服务架构？

5.  Ribbon 和 Feign 的区别？

6.  Spring Cloud 断路器的作用?

7.  为什么要用 Spring Boot？

8.  Spring Boot 的核心配置文件有哪几个？它们的区别是什么？

9.  Spring Boot 的配置文件有哪几种格式？它们有什么区别？

10.  Spring Boot 的核心注解是哪个？它主要由哪几个注解组成的？

11.  开启 Spring Boot 特性有哪几种方式？

12.  Spring Boot 需要独立的容器运行吗？

13.  运行 Spring Boot 有哪几种方式？

14.  你如何理解 Spring Boot 中的 Starters？

15.  如何在 Spring Boot 启动的时候运行一些特定的代码？

16.  Spring Boot 有哪几种读取配置的方式？

17.  Spring Boot 实现热部署有哪几种方式？

18.  Spring Boot 多套不同环境如何配置？

19.  Spring Boot 可以兼容老 Spring 项目吗，如何做？

20.  什么是 Spring Cloud？

21.  介绍一下 Spring Cloud 常用的组件？

22.  Spring Cloud 如何实现服务注册的？

23.  什么是负载均衡？有什么作用？

24.  什么是服务熔断？

25.  请介绍一下 Ribbon 的主要作用？
