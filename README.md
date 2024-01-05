# ArtVerse

## 简介

**艺韵**-于诗情画意中邂逅经典，一款汇集了众多名言绝句和世界名画的艺术篇章APP

​    艺韵是一款独特而富有想象力的手机应用，为您提供与经典艺术作品邂逅的机会。无论您是一个文艺爱好者，还是对经典艺术有浓厚兴趣，这款应用都会让您感到愉快和满足。

   通过艺韵，您将能够欣赏到世界各地的经典诗歌、文学作品和名画。无论是浪漫的爱情诗，还是富有哲理的名人名言，都能在这里找到。同时，艺韵还提供了一系列精选的音乐，与您的阅读和欣赏体验相辅相成。

​    除了浏览和欣赏经典艺术作品，艺韵还提供了一个创造性的空间，让用户能够表达个人感受和创作能力。用户可以通过艺韵与其他用户分享自己的作品，与他们交流和互动。这个社区将成为一个无限启发和创造的地方，让用户的艺术创作得到更多人的欣赏和赞赏。

### 学习目标

- 模块化框架搭建
- git多分支开发与合并，以及git多人协作时遇到的问题解法。
- ARouter进行组件跳转
- EventBus进行组件之间的通信
- MVVM架构搭建以及实现
- Room数据库存储信息和数据(看能不能做成一个组件)





## common

- [x] 通用的依赖添加

- [x] 仓库层和数据库层的封装
- [x] 通用资源（color）



## Module_main 

- [x] 主体底部导航栏框架实现
- [x] 点击事件实现

## Module_poem

- [x] 主体ViewPager2的配置
- [x] 诗句和作者的个性字体外部导入和设置
- [x] 分享弹出的底部Dialog实现
- [x] 点赞+1，取消点赞-1，特殊调用变量域
- [x] 评论区实现（后期实现二级评论）//累skr人，多种转换，到处扭，各种小tips。
- [x] Card的上层图片接入API ，ViewModel +LiveData+retrofit+rxjava
- [x] 诗句的调用API，ViewModel +LiveData+retrofit+rxjava
- [x] ViewPager2的切换淡入淡出动效
- [x] 添加刷新头
- [x] toast三方库使用（提醒刷新）
- [ ] 待更新UI



## Func_network

- [x] 通过网络请求返回数据时，进行一个数据解析，得到结果码和错误信息
- [x] 自定义BaseObserver类，继承自rxjava的Observer
- [x] 对实际的网络请求中有很多的异常信息(ExceptionHandle)和错误码的信息处理 （虽然目前没用到）
- [x] 创建网络服务，将OKHttp、Retrofit、RxJava串起来,进行线程调度





## Module_art 艺术画廊

- [x] 图片竖直ViewPager2卡片

- [x] ViewPager2的切换层叠效果动效(耗时很久)
- [x] 点击事件进入艺术画廊的详情(Intent传递序列化后的对象)
- [x] 进入艺术画廊，原图片放大到详情页面的图片大小//`ActivityOptionsCompat.makeScaleUpAnimation()`



## Module_profile

- [x] 个人资料显示
- [x] 点赞的诗句显示(Room)
- [x] 点赞的图画显示(Room)
- [x] 图画列表点击进入详情的事件(附带动画)





## 遇到的问题：

### 樊光辉

- 在实现框架基本跳转流程时，要么跳转不出来，要么闪退。后来发现跳转的组件Activity继承common组件的基类，在基类中使用反射进行binding的初始化，导致出现了我未知的问题  -> 活动不用基类简化binding的初始化了。
- 11月19日，搭好所有框架并且实现了基本功能，进行测试时，出现： `Execution failed for task ':app:compileDebugJavaWithJavac'`错误。终端输入`gradlew compileDebug --stacktrace -info  `命令行后，出现：`gradlew : 无法将“gradlew”项识别为 cmdlet、函数、脚本文件或可运行程序的名称。请检查名称的拼写，如果包括路径，请确保路径正确，然后再试一次。`错误查询网上，用了所有解法都没成功，只得重写整个应用，并更改。(想死)
- ViewPager2闪退：利用不同构造器产生的不同碎片，部分变量未定义，但都设置了监听事件，部分碎片出现空指针异常。
- 实现评论区时，BottomSheetDialog直接new，无法对其中的RecyclerView进行配置 -> 自定义类继承BottomSheetDialog/BottomSheetDialogFragment,我采取后者，在`onCreateDialog()`中，获取view，并找到对应`RecyclerView`，进行配置.
- 评论区点赞后，重新打开，会被重置-> 将new的Dialog提前定义，这样不会每次点击后，才在点击事件中new，这样的话点赞信息未保存 -> bean类增加变量，记录是否点赞或者取消点赞，并更新。
- 实现rxjava+retrofit网络框架，请求随机图片时，因为MainRepository内请求的方式基于观察者模式，在 `applySchedulers()` 未执行完毕就返回了结果，导致没有对LiveData数据进行赋值 ->  采取 `ViewModel` + `LiveData`， 在对应碎片中绑定`CardViewModel` (注意自定义ViewModel的初始化需要在碎片的主view加载完毕才可以，否则会出现错误)，在对碎片中的图片初始化时，调用`CardViewModel`中的`getCardPic()` -> 调用主仓库的`getCardPic()`进行异步请求。然后在主页面中调用`cardViewModel.getCardPic().observe()`进行监听，当收到数据，进行图片的实时加载。//解决完后，成就感非常强烈。
- 选择的API图片4k,加载速度较慢 -> 利用Glide 的`.thumbnail(0.15f)` 加载略缩图，速度明显变快。
- card图片加载不出来，查看日志，`java.net.SocketException: Socket is closed`，多次调试后，发现是图片太大，加载超时,自动关闭套接字（Socket） -> 改成另一个api
- poem页面翻过几个后，再翻回之前的页面会重新进行网络请求，并且重绘 ---联想到ViewPager2和RecyclerView类似，所以缓存复用机制大差不差，应该是默认回收掉里的过远的Fragment，调用`setOffscreenPageLimit(int limit)` ，用于设置页面的缓存数量。问题得以解决。







### 吴时兴

- 