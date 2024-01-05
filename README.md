# ArtVerse
 一款汇集了众多名言绝句和世界名画的艺术篇章APP

学习目标

- 模块化框架搭建
- git多分支开发，合并
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





## 遇到的问题：

