# dora

简介：这是一个为Android应用开发者量身定做的专业debug框架，专注于崩溃日志的收集。

优势：轻量级、功能强大、持续更新



开发和调试，是程序员的家常便饭。现在某些手机，有时因为某些原因，Logcat罢工，不能输出崩溃日志了。然而，代码还是要调试，怎么办呢？很多人自然而然的想到了使用腾讯的bugly。我个人不是很推荐腾讯的bugly，原因有以下几点。第一，收集崩溃日志上传到后台有延迟，有时等到花儿都谢了。第二，崩溃日志和用户关联不强，不能一对一解决用户反馈的bug，仅仅根据时间和手机型号往往很难断定就是这个问题导致的。第三，有时开发中的简单手误也上传了，导致bugly崩溃信息过多而很难clear，比如开发中不小心写了一个NullPointException或ClassCastException。抛开bugly，自己实现崩溃信息收集绝对是你提高开发效率和产品质量的最佳选择之一。我个人不太喜欢一大堆废话浪费读者时间，干就完了。举贤不避亲，我推荐自己写的一个崩溃日志收集框架dora，Github地址：https://github.com/JackWHLiu/dora.git。这个框架只需要在启动页中初始化以下配置，即new CrashConfig.Builder().build();即可。上报策略可以使用一个，也可以使用多个，参考CrashReportPolicy类的子类。如果使用多种策略组合，比如CrashReportPolicy policy = new LogPolicy(new StoragePolicy());则最里面的策略先执行，然后往外依次执行，即在收集到崩溃信息后，先存储到SD卡，再用日志输出。当然，LogPolicy仅作为一种测试用的策略，实际生产环境很少使用。除了可以自定义上报策略之外，还可以自定义收集的崩溃信息，即扩展CrashInfo类，然后在CrashConfig中指定你自定义的。本文章基于implementation 'com.github.JackWHLiu:dora:1.2' 。
