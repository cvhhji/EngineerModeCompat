# 工程模式兼容模块

这是一个 libxposed API 102 模块，用来恢复 Android 17 工程模式中被菜单过滤隐藏、但系统 APK 仍保留实现的入口。模块包名是 `com.engineermode.cvh`，作用范围限定为 `com.oplus.engineermode`，不会替换手机里的工程模式 APK。

## 菜单入口

模块重新显示这五项：

- 器件校准状态
- 读写关键 Log 测试
- 在线写 Key
- NFC 安全芯片入口
- Sensor Offset 测试

Android 17 原有的 `CLEAN_F_STATUS` 也会保留。模块只把入口放回菜单，不会替你执行器件校准、关键 Log 读写、在线写 Key 或清除安全芯片。页面能否使用仍由工程模式本身的权限和设备条件决定。

Android 16 的部分旧页面在 Android 17 APK 中已经没有对应实现，例如第二屏测试、旧诊断执行页、`DownloadStatus`、`OtgTest`/`USBDetectActivity`。只加菜单项无法恢复这些页面，所以这里没有放无效入口。
