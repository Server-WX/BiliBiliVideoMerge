> ### 运行环境：

- Windows 64位操作系统
- Java 8

---

> ### 程序功能：

- 将B站缓存视频中的音频与视频文件合并
- 重新编码视频为 **_H.264_** 改善兼容性
- 提供 英伟达、英特尔 GPU 加速功能

> ### B站缓存视频文件结构如下(文件夹名称以实际视频为准)

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
将缓存目录 **_s_45570_** 文件夹拖拽至 **_VideoMerge.exe_** 图标上即可，确保文件夹结构如上图完整，合并后视频文件将输出至源文件夹同级目录，文件夹名称为当前番剧名称
