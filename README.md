# ArtVerse
 一款汇集了众多名言绝句和世界名画的艺术篇章APP

学习目标

- 模块化框架搭建
- git多分支开发，合并
- ARouter进行组件跳转
- EventBus进行组件之间的通信
- MVVM架构搭建以及实现
- Room数据库存储信息和数据(看能不能做成一个组件)





## Module_main

- [x] 主体底部导航栏框架实现
- [x] 点击事件实现

## Module_poem

- [x] 主体ViewPager2的配置
- [x] 诗句和作者的个性字体外部导入和设置
- [x] 分享弹出的底部Dialog实现
- [x] 点赞+1，取消点赞-1，特殊调用变量域
- [x] 评论区实现（后期实现二级评论）//累skr人，多种转换，到处扭，各种小tips。
- [x] Card的上层图片接入API ，ViewModel +LiveData
- [ ] 



## Func_network

- [x] 通过网络请求返回数据时，进行一个数据解析，得到结果码和错误信息
- [x] 自定义BaseObserver类，继承自rxjava的Observer
- [x] 对实际的网络请求中有很多的异常信息(ExceptionHandle)和错误码的信息处理 （虽然目前没用到）
- [x] 创建网络服务，将OKHttp、Retrofit、RxJava串起来,进行线程调度









## 遇到的问题：

- 在实现框架基本跳转流程时，要么跳转不出来，要么闪退。后来发现跳转的组件Activity继承common组件的基类，在基类中使用反射进行binding的初始化，导致出现了我未知的问题  -> 活动不用基类简化binding的初始化了。
- 11月19日，搭好所有框架并且实现了基本功能，进行测试时，出现： `Execution failed for task ':app:compileDebugJavaWithJavac'`错误。终端输入`gradlew compileDebug --stacktrace -info  `命令行后，出现：`gradlew : 无法将“gradlew”项识别为 cmdlet、函数、脚本文件或可运行程序的名称。请检查名称的拼写，如果包括路径，请确保路径正确，然后再试一次。`错误查询网上，用了所有解法都没成功，只得重写整个应用，并更改。(想死)
- ViewPager2闪退：利用不同构造器产生的不同碎片，部分变量未定义，但都设置了监听事件，部分碎片出现空指针异常。
- 实现评论区时，BottomSheetDialog直接new，无法对其中的RecyclerView进行配置 -> 自定义类继承BottomSheetDialog/BottomSheetDialogFragment,我采取后者，在`onCreateDialog()`中，获取view，并找到对应`RecyclerView`，进行配置.
- 评论区点赞后，重新打开，会被重置-> 将new的Dialog提前定义，这样不会每次点击后，才在点击事件中new，这样的话点赞信息未保存 -> bean类增加变量，记录是否点赞或者取消点赞，并更新。
- 实现rxjava+retrofit网络框架，请求随机图片时，因为MainRepository内请求的方式基于观察者模式，在 `applySchedulers()` 未执行完毕就返回了结果，导致没有对LiveData数据进行赋值 ->  采取 `ViewModel` + `LiveData`， 在对应碎片中绑定`CardViewModel` (注意自定义ViewModel的初始化需要在碎片的主view加载完毕才可以，否则会出现错误)，在对碎片中的图片初始化时，调用`CardViewModel`中的`getCardPic()` -> 调用主仓库的`getCardPic()`进行异步请求。然后在主页面中调用`cardViewModel.getCardPic().observe()`进行监听，当收到数据，进行图片的实时加载。//解决完后，成就感非常强烈。
- 选择的API图片4k,加载速度较慢 -> 利用Glide 的`.thumbnail(0.15f)` 加载略缩图，速度明显变快。