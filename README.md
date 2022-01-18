# AdbTools
## 基于compose desktop开发的Adb工具，封装常用的Adb命令
### 本项目为compose desktop，使用idea打开
[打包教程](https://github.com/JetBrains/compose-jb/blob/master/tutorials/Native_distributions_and_local_execution/README.md)
[官方教程](https://github.com/JetBrains/compose-jb)   
![打包截图](img.png)
![应用截图](img_1.png)
 - 开发时要把adb三件套放在项目根目录
 - 打包时，先删掉根目录的adb三件套，点击runDistribute，打包结束后，把adb三件套放在D:\data\idea projects\AdbTools\build\compose\binaries\main\app\AdbTools这个目录下，再打成压缩包。
 - 不要在根目录放奇怪的东西
 - 首次打包可能会失败，这时候build目录会生成一个wixToolset文件夹，手动下载wix3.zip文件后改名为wix3.zip再放进去，重新打包即可
[wix3.zip下载链接,(下载完记得改名)](https://objects.githubusercontent.com/github-production-release-asset-2e65be/17723789/6aaeda80-da25-11e9-8564-82dd8c5115cd?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20220117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220117T050449Z&X-Amz-Expires=300&X-Amz-Signature=45009bdfff2be15108e6068572d412402dcffa897c60c36587d605412ed2b997&X-Amz-SignedHeaders=host&actor_id=31942159&key_id=0&repo_id=17723789&response-content-disposition=attachment%3B%20filename%3Dwix311-binaries.zip&response-content-type=application%2Foctet-stream)
 - 还是下载不了的话应该就是上网方式有问题了