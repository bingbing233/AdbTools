# AdbTools
## 基于compose desktop开发的Adb工具，封装常用的Adb命令
### 本项目为compose desktop，使用idea打开
[打包教程](https://github.com/JetBrains/compose-jb/blob/master/tutorials/Native_distributions_and_local_execution/README.md)
[官方教程](https://github.com/JetBrains/compose-jb)   
 - 开发时要把adb三件套放在项目根目录
 - 打包时，先删掉根目录的adb三件套，点击runDistribute，打包结束后，把adb三件套放在D:\data\idea projects\AdbTools\build\compose\binaries\main\app\AdbTools这个目录下，再打成压缩包。