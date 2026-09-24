# 工程模式菜单兼容

Android 17 上的 API 102 LSPosed 模块，只作用于 `com.oplus.engineermode`。模块保留手机内置 Android 17 工程模式 APK，不替换它，也不修改系统 Secrecy 配置。

## 已恢复的 Android 16 菜单

Android 17 的加密状态过滤会隐藏以下仍有 Android 17 实现的入口：

- 器件校准状态（key: `deivce_calibration_status`）
- 读写关键 Log 测试（`write_log_test`）
- 在线写 key（`write_key_online`）
- NFC 安全芯片入口（`nfc_clear_se`，手机菜单可能显示为“安全芯片测试”）
- Sensor Offset 测试（`sensoroffset_preference`）

模块同时保留 Android 17 新增的 `CLEAN_F_STATUS` 入口；Android 17 原有菜单、页面和硬件支持判断继续由系统 APK 提供。

入口位置：工程模式 → 售后手动测试。横向滑动顶部分类栏；Sensor Offset 在“设备调试”，校准状态、Log 和 Key 等在“其他”。

## 为什么没有把所有旧名称都加回来

逐项比对了 Android 16 APK 的偏好项、资源和组件清单与手机上的 Android 17 APK。只有上面五项是“新版本仍有实现、但被新增菜单过滤隐藏”的旧入口。其余旧名称并不都属于隐藏项：有些仍按机型或硬件支持条件显示，有些由 Android 17 的新页面替代；还有些旧实现已从 Android 17 APK 删除。例如旧第二屏测试、旧 `DiagnosticExecutorActivity` 诊断页面、`DownloadStatus`、旧 `OtgTest`/`USBDetectActivity`。给这些已删除页面添加菜单字样不会恢复其功能，模块不会生成点开无效的占位项。

## 构建

需要 JDK 17 或更高版本及 Android SDK 36。仓库附带 Gradle 9.7.1 Wrapper：

```powershell
.\gradlew.bat :app:assembleDebug
```

Debug APK 输出在 `app/build/outputs/apk/debug/app-debug.apk`。已发布的设备安装包位于 `release/EngineerModeCompat-1.0.0.apk`。

## 启用

安装 APK 后，在 LSPosed 中启用“工程模式菜单兼容”。静态作用域已限定为 `com.oplus.engineermode`。重启工程模式进程后生效。

## 操作范围

模块只恢复菜单显示，不自动执行校准、关键日志读写、在线写 Key 或安全芯片清除。相关页面自身的授权、Secrecy 和设备条件仍由工程模式保留。
