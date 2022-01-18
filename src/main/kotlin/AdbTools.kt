import java.io.BufferedReader
import java.io.InputStreamReader

object AdbTools {

    private val cmd = ArrayList<String>().apply {
        add("cmd")
        add("/c")
    }

    /**
     * 执行命令
     */
    fun execute(adbCmd: String): String {
        if (adbCmd.isNotBlank()) {
            kotlin.runCatching {
                val mCmd = ArrayList(cmd)
                mCmd.add(adbCmd)
                val process = Runtime.getRuntime().exec(mCmd.toArray(Array(3) { "" }))
                //拿到输出流
                val fis = process!!.inputStream
                //用缓冲器读行
                //开发时好像可以用gb2312
//                val bufferedReader = BufferedReader(InputStreamReader(fis, "GB2312"))
                //打包时改成gbk(开发时也可用)
                val bufferedReader = BufferedReader(InputStreamReader(fis, "gbk"))
                return bufferedReader.readText()
            }.onFailure {
                return "error mgs = ${it.message}"
            }
        }else{
            return "命令为空"
        }
        return ""
    }

    /**
     * 重启设备
     */
    fun reboot(): String {
        val adbCmd = "adb reboot"
        return execute(adbCmd)
    }

    /**
     * 当前ac
     */
    fun getCurrentActivity(): String {
        val adbCmd:String
        val androidVersion = getAndroidVersion().toFloat()
        adbCmd = if (androidVersion >= 8.1) {
            "adb shell dumpsys activity | findstr \"mResume\""

        } else {
            "adb shell dumpsys activity | findstr \"mFocus\""
        }
        return execute(adbCmd)
    }

    /**
     * Android版本
     */
    fun getAndroidVersion(): String {
        val adbCmd = ("adb shell getprop ro.build.version.release")
        return execute(adbCmd)
    }

    /**
     * 已连接设备
     */
    fun getDevices():String{
        val adbCmd = "adb devices"
        return execute(adbCmd)
    }

    /**
     * 获取设备连接状态
     */
    fun getConnectionState(): String {
        val adbCmd = "adb get-state"
        return execute(adbCmd)
    }

    /**
     * 清除app数据
     */
    fun clearAppData(pkgName:String): String {
        val adbCmd = "adb shell pm clear $pkgName"
        return execute(adbCmd)
    }

    /**
     * 卸载
     */
    fun uninstall(pkgName:String): String {
        val adbCmd = "adb shell pm uninstall $pkgName"
        return execute(adbCmd)
    }

    /**
     * 安装apk  todo:鼠标拖动识别路径
     */
    fun install(path:String):String{
        return if(path.endsWith(".apk")){
            val cmd = "adb install -r $path"
            execute(cmd)
        } else{
            "请选择.apk后缀的文件"
        }
    }

    /**
     * 文件传输
     */
    fun pushFile(path:String):String{
        val fileFormat = path.split(".")
        val filePath = "/sdcard/adb_file_${System.currentTimeMillis()}.${fileFormat[fileFormat.size-1]}"
        val cmd = "adb push $path $filePath"
        return execute(cmd) + "/n"+"file:$filePath"
    }

    /**
     * 查看全部app包名
     */
    fun getAllPkgName(): String {
        val cmd = "adb shell pm list packages"
        return execute(cmd)
    }

    /**
     * 系统app包名
     */
    fun getSysAppPkgName():String{
        val cmd = "adb shell pm list packages -s"
        return execute(cmd)
    }

    /**
     * 第三方app包名
     */
    fun get3AppPkgName():String{
        val cmd = "adb shell pm list packages -3"
        return execute(cmd)
    }

    /**
     * 屏幕截图
     */
    fun screenShot():String{
        val filePath = "/sdcard/adb_screen_shot_${System.currentTimeMillis()}.png"
        val cmd = "adb shell screencap $filePath"
        return execute(cmd)  +"file:$filePath"
    }

    /**
     * 停止adb
     */
    fun killAdb(){
        val cmd = "adb kill-server"
        execute(cmd)
    }

    /**
     * 设备信息
     */
    fun getDeviceInfo(): String {
        val cmdList = arrayOf("adb shell getprop ro.product.brand","adb shell getprop ro.product.model ","adb shell wm size","adb shell wm density")
        var resultStr = ""
        repeat(cmdList.size){
            resultStr += execute(cmdList[it])
        }
        return resultStr
    }

    /**
     * cpu信息
     */
    fun getCPUInfo():String{
        val cmd = "adb shell cat /proc/cpuinfo"
        return execute(cmd)
    }

    /**
     * 查看包名路径
     */
    fun getPkgPath(pkgName: String):String{
        val cmd = "adb shell pm path $pkgName"
        return execute(cmd)
    }


}