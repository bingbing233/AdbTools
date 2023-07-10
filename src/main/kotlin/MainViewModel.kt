import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File

object MainViewModel {
    // 功能
    const val FUNC_MAIN = "main"
    const val FUNC_SETTING = "device"
    const val FUNC_INFO = "info"
    const val FUNC_FLASH = "flash"

    private val scope = CoroutineScope(Dispatchers.Main)

    // 拖拽文件列表
    private val mFileList = MutableStateFlow<List<File>>(emptyList())
    val fileList = mFileList.asStateFlow()

    // 设备信息
    private val mDeviceInfo = MutableStateFlow("")
    val deviceInfo = mDeviceInfo.asStateFlow()

    // 电池信息
    private val mBatteryInfo = MutableStateFlow("")
    val batteryInfo = mBatteryInfo.asStateFlow()

    // 电池信息
    private val mCPUInfo = MutableStateFlow("")
    val CPUInfo = mCPUInfo.asStateFlow()

    // snack
    private val mSnackText = MutableStateFlow<String?>(null)
    val snackText = mSnackText.asStateFlow()

    // 当前选中功能
    private val mCurFunc = MutableStateFlow(FUNC_MAIN)
    val curFunc = mCurFunc.asStateFlow()

    // info loading
    private val mIsShowInfoLoading = MutableStateFlow(false)
    val isShowInfoLoading = mIsShowInfoLoading.asStateFlow()

    // 命令日志
    private val mLog = MutableStateFlow<String>("")
    val log = mLog.asStateFlow()

    // auto install
    private val mAutoInstall = MutableStateFlow<Boolean>(false)
    val autoInstall = mAutoInstall.asStateFlow()

    /**
     * 更新文件列表
     */
    fun updateFiles(files: List<File>) {
        scope.launch {
            mFileList.emit(files)
        }
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

    /**
     * 更新log
     */
    fun setLog(log:String){
        scope.launch {
            mLog.emit(log)
        }
    }

    private fun showInfoLoading(show:Boolean){
        scope.launch {
            mIsShowInfoLoading.emit(show)
        }
    }

    // 是否自动安装
    fun setAutoInstall(auto:Boolean){
        scope.launch {
            installApk()
        }
    }

    // --------------- 命令相关方法 ---------------
    //刷新全部信息 一般更换的设备的时候会调用
    fun refreshAllInfo(){
        showInfoLoading(true)
        getDeviceInfo()
        getBattery()
        getCPUInfo()
        showInfoLoading(false)
    }

    // 设备信息
    fun getDeviceInfo() {
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getDeviceInfo()
            mDeviceInfo.emit(info)
        }
    }

    // 电池信息
    fun getBattery(){
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getBatteryInfo()
            mBatteryInfo.emit(info)
        }
    }

    // cpu信息
    fun getCPUInfo() {
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getCPUInfo()
            mCPUInfo.emit(info)
        }
    }

    // 安装应用
    fun installApk(){
        scope.launch {
            var totalLog = ""
            setLog(totalLog)
            if(fileList.value.isNotEmpty()){
                withContext(Dispatchers.IO){
                    fileList.value.forEach{
                        if (it.path.endsWith(".apk")){
                            val msg = AdbTools.install(it.path)
                            totalLog += "\n $msg"
                        }else{
                            totalLog += "\n 请选择.apk后缀的文件"
                        }
                    }
                }
                setLog(totalLog)
            }else {
                setLog("文件为空！")
            }
            setLog(totalLog)
        }
    }

    // 复制到剪切板
    fun copyText(text:String){
        scope.launch(Dispatchers.IO) {
            mSnackText.emit("已复制到剪切板")
            val clip = Toolkit.getDefaultToolkit().systemClipboard
            val tText = StringSelection(text)
            clip.setContents(tText,null)
        }
    }
    // --------------- 命令相关方法 ---------------

}