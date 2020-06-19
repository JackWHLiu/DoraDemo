#### Github地址：https://github.com/JackWHLiu/dora.git

  fork和star是对我们最好的认可。

   

  **序章**

  在启动页SplashActivity中配置，因为Application中无法申请Android6.0以上的运行时权限。

  CrashConfig.Builder(this)
      .crashReportPolicy(StoragePolicy("DoraMusic/log"))
      .build()

  **第一章 自定义CrashInfo**

  你可以扩展CrashInfo来收集更多的信息上报。

   

  **第二章 自定义Policy**

  通过继承CrashReportPolicyWrapper来自己实现策略，Policy可以自由组合，即你可以选其中几个定义好的Policy使用，这些Policy是串联执行的。它们之间互不影响，A、B和C三个Policy，按顺序执行ABC，A执行失败了、B和C继续上报，前提条件是在子线程执行失败。

   

  **第三章 自定义Filter**

  版本1.9开始支持。自定义过滤器发生在上报前，如果过滤器handle()方法返回false，则后面的Policy流程不会执行。过滤器是链式结构，可以一个，也可以多个。过滤器必须全部不拦截，才会走到后面的Policy流程。

   

  **第四章 自定义Group**

  版本1.9开始支持。设计分组分发的目的在于，让Policy只做上报操作，逻辑控制交给Group。自定义分组可以很好的分发崩溃信息到不同渠道。例如继承BrandGroup得到VivoGroup和OppoGroup，可以把这两个组的崩溃信息分别分配给使用vivo手机和OPPO手机调试的Android开发组成员。Group分组规则一旦定义好，是可以在多个Policy中使用的，这样就做到了复用。分组可以应用到Policy流程。有了分组后，同一类型Policy重复使用就有意义了。比如vivo手机的崩溃信息可以发给正在使用vivo手机调试的开发者，然后oppo手机的崩溃信息可以发给正在使用oppo手机调试的开发者。

   

  **第五章 复合分组**

  使用ComplexGroup类可以组合2个以上的分组，比如将Android6.0的手机分组和vivo手机取并集，就包括所有Android6.0的手机和所有vivo手机。如果使用交集，就是Android6.0的vivo手机。补集，则是除开Android 6.0 vivo系统的手机以外的所有手机。

   

  1.2.1 版本

  介绍：Dora最初的样子，最精简的版本

  功能：Policy

  所有类：Collector、CrashCollector、CrashConfig、CrashInfo、CrashReportPolicy、CrashReportPolicyWrapper、DoraUncaughtExceptionHandler、LogPolicy、StoragePolicy、WebPolicyBase

   

  1.9 版本

  介绍：支持自定义策略、过滤器、分组分发

  功能：Policy、Filter、Group

  所有类：ActivityThreadFilter、AndroidVersionGroup、BrandGroup、Collector、ComplexGroup、CrashCollector、CrashConfig、CrashInfo、CrashReportFilter、CrashReportFilterChain、CrashReportGroup、CrashReportPolicy、CrashReportPolicyWrapper、DefaultFilter、DefaultGroup、DoraUncaughtExceptionHandler、DoraWebPolicy、EmailPolicy、LogPolicy、StoragePolicy、TimeFilter、WebPolicyBase
