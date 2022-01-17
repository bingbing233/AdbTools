import java.io.BufferedReader
import java.io.InputStreamReader

object AdbTools {

    private val cmd = ArrayList<String>().apply {
        add("cmd")
        add("/c")
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
        var adbCmd = ""
        val androidVersion = getAndroidVersion().toFloat()
        if (androidVersion >= 8.1) {
            adbCmd = "adb shell dumpsys activity | findstr \"mResume\""

        } else {
            adbCmd = "adb shell dumpsys activity | findstr \"mFocus\""
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
                val bufferedReader = BufferedReader(InputStreamReader(fis, "GB2312"))
                return bufferedReader.readText()
            }.onFailure {
                return "error mgs = ${it.message}"
            }
        }else{
            return "命令为空"
        }
        return ""
    }
}