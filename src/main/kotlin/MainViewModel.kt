import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File

object MainViewModel {
    // 功能
    const val FUNC_MAIN = "main"
    const val FUNC_SETTING = "device"
    const val FUNC_INFO = "info"

    private val scope = CoroutineScope(Dispatchers.Main)

    // 拖拽文件列表
    private val mFileList = MutableStateFlow<List<File>>(emptyList())
    val fileList = mFileList.asStateFlow()

    // 设备信息
    private val mDeviceInfo = MutableStateFlow<String>("")
    val deviceInfo = mDeviceInfo.asStateFlow()

    // 电池信息
    private val mBatteryInfo = MutableStateFlow<String>("")
    val batteryInfo = mBatteryInfo.asStateFlow()

    // 电池信息
    private val mCPUInfo = MutableStateFlow<String>("")
    val CPUInfo = mCPUInfo.asStateFlow()

    // snack
    private val mSnackText = MutableStateFlow<String?>(null)
    val snackText = mSnackText.asStateFlow()

    // 当前选中功能
    private val mCurFunc = MutableStateFlow<String>("")
    val curFunc = mCurFunc.asStateFlow()

    /**
     * 更新文件列表
     */
    fun updateFiles(files: List<File>) {
        scope.launch {
            mFileList.emit(files)
        }
    }

    /**
     * 清空文件列表
     */
    fun clearFiles() {
        scope.launch {
            mFileList.emit(emptyList())
        }
    }

    /**
     * 更新设备信息
     */
    fun updateDeviceInfo(info:String){
        scope.launch {
            mDeviceInfo.emit(info)
        }
    }

    /**
     * 刷新设置信息
     */
    fun refreshDeviceInfo(){
        // 要展示什么信息呢？
    }

    /**
     * 设置当前选中功能
     * @param func MainViewModel.FUNC_
     */
    fun setCurFunc(func:String){
        scope.launch {
            mCurFunc.emit(func)
        }
    }

    var resultStr = MutableStateFlow("命令输出区")

    fun execute(adbCmd: String) {
        resultStr.value = AdbTools.execute(adbCmd)
    }

    fun reboot() {
        resultStr.value = AdbTools.reboot()
    }

    fun getAndroidVersion() {
        resultStr.value = AdbTools.getAndroidVersion()
    }

    fun getCurrentActivity() {
        resultStr.value = AdbTools.getCurrentActivity()
    }

    fun getDevices() {
        resultStr.value = AdbTools.getDevices()
    }

    fun getConnectionState() {
        val str = AdbTools.getConnectionState()
        if (str.contains("device"))
            resultStr.value = "device --> 设备连接正常"
        if (str.contains("offline"))
            resultStr.value = "offline --> 设备离线"
        if (str.contains("unknown"))
            resultStr.value = "unknown --> 无设备"
    }

    fun clearAppData(pkgName: String) {
        resultStr.value = AdbTools.clearAppData(pkgName)
    }

    fun uninstall(pkgName: String) {
        resultStr.value = AdbTools.uninstall(pkgName)
    }

    fun install(path: String) {
        resultStr.value = AdbTools.install(path)
    }

    fun pushFile(path: String) {
        resultStr.value = AdbTools.pushFile(path)
    }

    fun getAllPkgName() {
        resultStr.value = AdbTools.getAllPkgName()
    }

    fun screenShot() {
        resultStr.value = AdbTools.screenShot()
    }

    fun getSysPkgName() {
        resultStr.value = AdbTools.getSysAppPkgName()
    }

    fun get3PkgName() {
        resultStr.value = AdbTools.get3AppPkgName()
    }

    fun getDeviceInfo() {
        scope.launch {
            val info = AdbTools.getDeviceInfo()
            mDeviceInfo.emit(info)
        }
    }

    fun getBattery(){
        scope.launch {
            val info = AdbTools.getBatteryInfo()
            mBatteryInfo.emit(info)
        }
    }

    fun getCPUInfo() {
        scope.launch {
            val info = AdbTools.getCPUInfo()
            mCPUInfo.emit(info)
        }
    }

    fun getPkgPath(pkgName: String) {
        resultStr.value = AdbTools.getPkgPath(pkgName)
    }

    fun copyText(text:String){
        scope.launch {
            mSnackText.emit("已复制到剪切板")
            val clip = Toolkit.getDefaultToolkit().systemClipboard
            val tText = StringSelection(text)
            clip.setContents(tText,null)
            mSnackText.emit(null)
        }

    }
}