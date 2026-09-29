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

## 安装

安装 APK 后，在 LSPosed 中启用“工程模式菜单兼容”。入口在“售后手动测试”：Sensor Offset 位于“设备调试”，校准状态、Log、Key 和安全芯片入口位于“其他”。重启工程模式进程后生效。

本版本包名从 `com.cvh.engineermodecompat` 改为 `com.engineermode.cvh`，Android 会把它当作另一个应用。安装新版前，请在 LSPosed 中停用旧模块或卸载旧包。

## 构建

需要 JDK 17 或更高版本、Android SDK 36。仓库带有 Gradle 9.7.1 Wrapper：

```powershell
.\gradlew.bat :app:assembleRelease
```

Release APK 输出到 `app/build/outputs/apk/release/app-release.apk`。

## 固定签名

Debug 和 Release 使用同一 keystore。签名私钥不在仓库中；在当前用户的 Gradle 配置文件里设置以下属性：`engineerModeSigningStoreFile`、`engineerModeSigningStorePassword`、`engineerModeSigningKeyAlias`、`engineerModeSigningKeyPassword`。其他构建机也必须使用同一 keystore，不能另生成一把。

v1.1.0 起固定使用的签名证书 SHA-256 指纹是 `99:D4:B5:2B:86:E7:04:29:C3:EC:FA:CA:22:1B:75:43:2B:85:B9:7C:59:17:BC:A1:90:1E:CE:63:89:CE:37:03`。后续版本沿用对应私钥，否则已安装版本不能直接覆盖升级。
