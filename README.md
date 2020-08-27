# WMagicBotR

## WMagicBot 
原本是一个自己玩的小项目,整合了spring + [PicqBotX](https://github.com/HyDevelop/PicqBotX) 框架（基于酷q）的qq机器人，现在由于酷q不可用，已经凉了谨此纪念...

## WMagicBotR
现在WMagicBotR 基于 [Mirai](https://github.com/mamoe/mirai) 框架重生
集成spring + mybatis + sqlite
- 不用额外配置购买数据库 数据迁移方便(复制数据库文件)
- 部署方便 jar包 直接运行, 只要有java环境就可以 



## 更新记录
### 2020年08月25日
- 最初版本提交 包含 bot框架本体
- 一些bot的小功能,roll点,抽签,加密等
- pcr工会战管理功能（类似yobot，详细的说明后期有在补充）

### 2020年08月27日
- 添加 pcr jjc查询功能,感谢pcrdfans的光佬无偿提供的查询接口
- 项目文件下的 nicknames.txt 为jjc查询时所用的昵称对照表,正常启动会自动去 [pcr-nickname](https://github.com/pcrbot/pcr-nickname) 获取,但是考虑到国内下载github 源文件可能会下载不到，所以可以自己下载后放项目jar相同path下

### 本项目不稳定更新中(有空的时候会更新)

## 无关的话
本人pcr玩家,原本工会战管理使用yobot,但是由于cq的突然离开,原来的yobot突然不可用(现在yobot应该也有基于Mirai的实现),有些不知所措。
所幸遇到了Mirai,于是就想干脆自己写一个。所以本项目pcr相关功能参考了yobot。

## 感谢
- [Mirai](https://github.com/mamoe/mirai) 开发组所有成员
- [yobot](https://github.com/pcrbot/yobot) 开发组所有成员
- [pcrdfans.com](https://github.com/peterli110/pcrdfans.com) 的ヒカリ希卡利大佬
- [pcr-nickname](https://github.com/pcrbot/pcr-nickname) 提供的pcr id-角色对照表

## 注意
### 本项目基于 Mirai
- 本项目使用与Mirai相同协议 (AGPLv3 with Mamoe Exceptions) 开源
- 本项目的所有衍生项目 必须使用相同协议 (AGPLv3 with Mamoe Exceptions) 开源
- 本软件禁止用于一切商业活动
- 本软件禁止收费传递, 或在传递时不提供源代码
