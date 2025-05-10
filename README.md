## 一个基于ffmpeg用于合并B站移动视频缓存的程序

> ### 温馨提示！
> 由于Win10起，微软商店安装的B站官方客户端，其视频缓存结构原理与移动端发生了改变，此程序暂不支持Win10版客户端的缓存，如需使用本程序，请查阅如何访问Android/data目录，并将缓存文件夹导出到电脑上

> ### 运行环境：

- Windows 64位操作系统
- [Java 8](https://repo.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-windows-x64.exe)

---

> ### 程序功能：

- 将B站缓存番剧中的音频与视频文件合并
- 提供 英伟达、英特尔 GPU 加速功能
- 支持合并up主发布的视频缓存，并兼容分P的标题

> ### B站移动端缓存的视频文件结构如下(文件夹名称以实际视频为准)

```
s_45570
└── 765277
    ├── 80
    │   ├── audio.m4s
    │   ├── index.json
    │   └── video.m4s
    │
    ├── danmaku.xml
    └── entry.json
```

---

> ### 使用方法(文件夹名称以实际视频为准)：

1. 点击 **[GitHub Releases](https://github.com/Server-WX/BiliBiliVideoMerge/releases)** 下载最新版本
2. 第一次安装建议下载完整压缩包版，下载完成后解压至任意方便的位置，可创建主程序的快捷方式以方便使用
3. 确保缓存文件的完整性，将缓存目录 例如 **_s_45570_** 文件夹拖拽至 **_VideoMerge.exe_**
   图标上即可，确保文件夹结构如上图完整，合并后视频文件将输出至源文件夹同级目录，文件夹名称为当前番剧名称或up主的视频标题

> ### 更新方法：

1. 点击 *[GitHub Releases](https://github.com/Server-WX/BiliBiliVideoMerge/releases)* **查看**最新版本
2. 下载单独程序的(simple)版，只需替换主程序即可完成更新

PS：如非必要，请勿随意替换ffmpeg程序