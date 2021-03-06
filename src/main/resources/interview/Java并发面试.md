[TOC]

------

众所周知，在Java的知识体系中，并发编程是非常重要的一环，也是面试的必问题，一个好的Java程序员是必须对并发编程这块有所了解的。

然而不论是哪个国家，什么背景的 Java 开发者，都对自己写的并发程序相当自信，但也会在出问题时表现得很诧异甚至一筹莫展。

可见，Java 并发编程显然不是一件能速成的能力，基础搭得越好，越全面，在实践中才会有更深刻的理解。

因此，大家不难发现 Java 并发问题一直是各个大厂面试的重点之一。我在平时的面试中，**也发现很多候选人对一些基本的并发概念表示没听过，或原理不理解，可能知道一些却又讲不清楚，最终导致面试失败。**

**本文会结合实际中接触到的一些面试题，重点来聊一聊 Java 并发中的相关知识点。**



![](https://upload-images.jianshu.io/upload_images/11474088-c58e23306dc23c2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# **Synchronized 相关问题**

1.  Synchronized 用过吗，其原理是什么？
作用:Synchronized是java里的关键字,用于Java实现方法或者代码块的同步
原理：作用于方法时，JVM通过ACC_SYNCHRONIZED标记实现同步，该标记表示在进入方法时JVM需要进行monitorenter操作，而在退出该方法时JVM需要进行monitorexit操作
作用于代码块时JVM通过monitorenter和monitorexit两个指令来实现同步，他们都是通过monitor来实现同步
对于方法，monitorenter和monitorexit操作的锁对象都是隐式的，实例方法对应的锁对象是this，静态方法对应的锁对象是方法所在类的Class实例

2.  你刚才提到获取对象的锁，这个“锁”到底是什么？如何确定对象的锁？
实例对象里有对象头，对象头里面有Mark Word, Mark Word包含了锁标致位、是否偏向

3.  什么是可重入性，为什么说 Synchronized 是可重入锁？
可重入锁就是持有锁的线程再次加锁时，可直接获得锁。
当进行加锁操作时，JVM会判断是否已经是重量级锁，如果不是，它会在当前线程的当前栈桢中划出一块空间，作为该锁的锁记录，并且将锁对象的标记字段复制到该锁记录中，然后JVM会尝试CAS替换锁对象的标记字段，
JVM会判断该字段值是否为X...X01，是则替换为刚才分配的锁记录的地址

4.  JVM 对 Java 的原生锁做了哪些优化？
针对没有竞争锁的情况引入了轻量级锁
针对大多时候只有一个线程次有某个锁的情况引入了偏向锁

5.  为什么说 Synchronized 是非公平锁？
在锁释刚放时，新的线程可以通过CAS获取到锁

6.  什么是锁消除和锁粗化？
锁消除:虚拟机即时编译器在运行时，对一些代码上要求同步，但是被检测到不可能存在共享数据竞争的锁进行消除。
锁粗化：将多个连续的加锁、解锁操作连接在一起，扩展成一个范围更大的锁。

7.  为什么说 Synchronized 是一个悲观锁？乐观锁的实现原理又是什么？什么是

8.  乐观锁一定就是好的吗？

# **可重入锁 ReentrantLock 及其他显式锁相关问题**

1.  跟 Synchronized 相比，可重入锁 ReentrantLock 其实现原理有什么不同？
synchronized是Java语言的关键字，基于JVM实现。而ReentrantLock是基于JDK的API层面实现的，核心技术AQS、CAS park()、unpark()

2.  那么请谈谈 AQS 框架是怎么回事儿？
AQS就是同步阻塞队列，利用一个双端队列存在阻塞的线程

3.  请尽可能详尽地对比下 Synchronized 和 ReentrantLock 的异同。
「锁的实现：」** synchronized是Java语言的关键字，基于JVM实现。而ReentrantLock是基于JDK的API层面实现的（一般是lock()和unlock()方法配合try/finally 语句块来完成。）
「性能：」** 在JDK1.6锁优化以前，synchronized的性能比ReenTrantLock差很多。但是JDK6开始，增加了适应性自旋、锁消除等，两者性能就差不多了。
「功能特点：」** ReentrantLock 比 synchronized 增加了一些高级功能，如等待可中断、可实现公平锁、可实现选择性通知。

4.  ReentrantLock 是如何实现可重入性的？
在调用lock方法时，会检查AQS的state是否为大于0，也就是锁被占用，如果被占用这个时候会比较当前线程是否就是持有锁的线程，是则state+1并返回true，表示重入成功

5.  除了 ReetrantLock，你还接触过 JUC 中的哪些并发工具？
CyclicBarrier 和 CountDownLatch

6.  请谈谈 ReadWriteLock 和 StampedLock。

7.  如何让 Java 的线程彼此同步？你了解过哪些同步器？请分别介绍下。

8.  CyclicBarrier 和 CountDownLatch 看起来很相似，请对比下呢？
CountDownLatch：一个或者多个线程，等待其他多个线程完成某件事情之后才能执行;
CyclicBarrier：多个线程互相等待，直到到达同一个同步点，再继续一起执行。

# **Java 线程池相关问题**

1.  Java 中的线程池是如何实现的？
线程池底层的存储结构其实就是一个HashSet
线程池原理关键技术：锁(lock,cas)、阻塞队列、hashSet(资源池)

2.  创建线程池的几个核心构造参数？
corePoolSize: 核心线程数量，可以类比正式员工数量，常驻线程数量。
maximumPoolSize: 最大的线程数量，公司最多雇佣员工数量。常驻+临时线程数量。
workQueue：多余任务等待队列，再多的人都处理不过来了，需要等着，在这个地方等。
keepAliveTime：非核心线程空闲时间，就是外包人员等了多久，如果还没有活干，解雇了。
threadFactory: 创建线程的工厂，在这个地方可以统一处理创建的线程的属性。每个公司对员工的要求不一样，恩，在这里设置员工的属性。
handler：线程池拒绝策略，什么意思呢?就是当任务实在是太多，人也不够，需求池也排满了，还有任务咋办?默认是不处理，抛出异常告诉任务提交者，我这忙不过来了。

3.  线程池中的线程是怎么创建的？是一开始就随着线程池的启动创建好的吗？

4.  既然提到可以通过配置不同参数创建出不同的线程池，那么 Java 中默认实现好的线程池又有哪些呢？请比较它们的异同。

5.  如何在 Java 线程池中提交线程？
execute(Runnable r)

# **Java 内存模型相关问题**

1.  什么是 Java 的内存模型，Java 中各个线程是怎么彼此看到对方的变量的？
Java内存模型规定了所有的变量都存储在主内存中，没条线程还有自己的工作内存，线程的工作内存保存了被该线程使用到的限量的主内存的副本，
线程对所有变量的操作都必须在工作内存中进行而不能直接读取主内存中的变量，不同的线程之间也无法直接访问对方工作内存中的变量，线程间的
变量值得传递均需要通过主内存来完成

2.  请谈谈 volatile 有什么特点，为什么它能保证变量对所有线程的可见性？
两个作用：可见性和禁止指令重排
volatile变量写的时候会立马回写到主内存，每次会读取最新的值到工作内存

3.  既然 volatile 能够保证线程间的变量可见性，是不是就意味着基于 volatile 变量的运算就是并发安全的？
不是，对volatile字段进行“++”这样的读写操作不会被当做原子操作执行

4.  请对比下 volatile 对比 Synchronized 的异同。
只需要保证共享资源的可见性的时候可以使用volatile替代，synchronized保证可操作的原子性一致性和可见性。volatile适用于新值不依赖于就值的情形。

5.  请谈谈 ThreadLocal 是怎么解决并发安全的？

6.  很多人都说要慎用 ThreadLocal，谈谈你的理解，使用 ThreadLocal 需要注意些什么？

# **并发队列相关问题**

1.  谈下对基于链表的非阻塞无界队列 ConcurrentLinkedQueue 原理的理解？

2.  ConcurrentLinkedQueue 内部是如何使用 CAS 非阻塞算法来保证多线程下入队出队操作的线程安全？

3.  基于链表的阻塞队列 LinkedBlockingQueue 原理。

4.  阻塞队列LinkedBlockingQueue 内部是如何使用两个独占锁 ReentrantLock 以及对应的条件变量保证多线程先入队出队操作的线程安全？

5.  为什么不使用一把锁，使用两把为何能提高并发度？

6.  基于数组的阻塞队列 ArrayBlockingQueue 原理。

7.  ArrayBlockingQueue 内部如何基于一把独占锁以及对应的两个条件变量实现出入队操作的线程安全？

8.  谈谈对无界优先级队列 PriorityBlockingQueue 原理？

9.  PriorityBlockingQueue 内部使用堆算法保证每次出队都是优先级最高的元素，元素入队时候是如何建堆的，元素出队后如何调整堆的平衡的？

# **如何学习并发编程**

学习java并发就像进入了另外一个学习领域，就像学习一门新的编程语言，或者是学习一套新的语言概念，要理解并发编程，其难度跟理解面向对象编程难度差不多。你花一点功夫，就可以理解它的基本机制，但是要想真正掌握它的本质，就需要深入的学习与理解。**最后在分享一个并发编程知识的学习导图给大家，为了方便大家能看的清楚我把Xmind图缩略了，欢迎关注我的公众号：Java架构师联盟 后台发送【架构】拿下载链接，已经完善更新）：**

![](http://p3.pstatp.com/large/pgc-image/ecbdbccf387348aa8f392b31bfd6af21)
