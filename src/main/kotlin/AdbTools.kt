import java.io.BufferedReader
import java.io.InputStreamReader

object AdbTools {

    private val cmd = ArrayList<String>().apply {
        add("cmd")
        add("/c")
    }

    fun reboot(): String {
        val adbCmd = "adb reboot"
        return execute(adbCmd)
    }

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

    fun getAndroidVersion(): String {
        val adbCmd = ("adb shell getprop ro.build.version.release")
        return execute(adbCmd)
    }


    /**
     * 执行命令
     */
    fun execute(adbCmd: String): String {
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
        return ""
    }
}