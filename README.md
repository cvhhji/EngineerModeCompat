# 工程模式菜单兼容

这是一个 libxposed API 102 模块，应用包名为 `com.engineermode.cvh`，静态作用域仅为 `com.oplus.engineermode`。

## Android 16 菜单恢复

Android 17 工程模式 APK 仍实现、但被加密状态菜单过滤隐藏的 16 版入口：器件校准状态、读写关键 Log 测试、在线写 key、NFC 安全芯片入口和 Sensor Offset 测试。模块还保留 Android 17 的 `CLEAN_F_STATUS` 入口。

模块不替换工程模式 APK，也不修改系统 Secrecy 服务、硬件支持判断或其他应用。仍存在的旧入口由 Android 17 系统 APK 提供。

旧版部分页面的实现已从 Android 17 APK 删除，包括第二屏测试、旧诊断执行页、`DownloadStatus` 和旧 `OtgTest`/`USBDetectActivity`。模块不会为这些页面创建无实现的菜单项。

## 构建与固定签名

需要 JDK 17 或更高版本及 Android SDK 36。使用仓库提供的 Gradle 9.7.1 Wrapper：

```powershell
.\gradlew.bat :app:assembleRelease
```

Debug 和 Release 构建都使用同一签名配置。签名私钥不得提交到仓库；在当前用户的 Gradle 配置文件中设置 `engineerModeSigningStoreFile`、`engineerModeSigningStorePassword`、`engineerModeSigningKeyAlias`、`engineerModeSigningKeyPassword`。其他构建机必须安全地配置同一 keystore，不能临时生成新密钥。

固定签名证书 SHA-256 指纹：`99:D4:B5:2B:86:E7:04:29:C3:EC:FA:CA:22:1B:75:43:2B:85:B9:7C:59:17:BC:A1:90:1E:CE:63:89:CE:37:03`。v1.1.0 及后续版本必须继续使用对应私钥。

Release APK 输出到 `app/build/outputs/apk/release/app-release.apk`。本版本包名从 `com.cvh.engineermodecompat` 改为 `com.engineermode.cvh`，Android 会将其识别为新应用；启用新模块前应禁用或卸载旧包。

## 启用与操作范围

安装后在 LSPosed 中启用“工程模式菜单兼容”。入口位于“售后手动测试”；横向滑动顶部分类栏，Sensor Offset 在“设备调试”，校准状态、关键 Log、在线 Key 和安全芯片相关入口在“其他”。

模块只恢复入口显示，不会自动执行校准、关键日志读写、在线写 Key 或安全芯片清除；工程模式自身的授权和设备条件仍保留。
