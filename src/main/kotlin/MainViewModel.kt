import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel {

    var resultStr = MutableStateFlow("命令输出区")

    fun execute(adbCmd:String){
        resultStr.value = AdbTools.execute(adbCmd)
    }

    fun reboot(){
        resultStr.value = AdbTools.reboot()
    }

    fun getAndroidVersion(){
        resultStr.value = AdbTools.getAndroidVersion()
    }

    fun getCurrentActivity(){
        resultStr.value = AdbTools.getCurrentActivity()
    }

    fun getDevices(){
        resultStr.value = AdbTools.getDevices()
    }

    fun getConnectionState(){
        val str = AdbTools.getConnectionState()
        if (str.contains("device"))
            resultStr.value = "device --> 设备连接正常"
        if(str.contains("offline"))
            resultStr.value = "offline --> 设备离线"
        if(str.contains("unknown"))
            resultStr.value = "unknown --> 无设备"
    }

    fun clearAppData(pkgName:String){
        resultStr.value = AdbTools.clearAppData(pkgName)
    }

    fun uninstall(pkgName: String){
        resultStr.value = AdbTools.uninstall(pkgName)
    }

    fun install(path:String){
        resultStr.value = AdbTools.install(path)
    }

    fun pushFile(path: String){
        resultStr.value = AdbTools.pushFile(path)
    }

    fun getAllPkgName(){
        resultStr.value = AdbTools.getAllPkgName()
    }

    fun screenShot(){
        resultStr.value = AdbTools.screenShot()
    }

    fun getSysPkgName(){
        resultStr.value = AdbTools.getSysAppPkgName()
    }

    fun get3PkgName(){
        resultStr.value = AdbTools.get3AppPkgName()
    }

    fun getDeviceInfo(){
        resultStr.value = AdbTools.getDeviceInfo()
    }

    fun getCPUInfo(){
        resultStr.value =AdbTools.getCPUInfo()
    }

    fun getPkgPath(pkgName:String){
        resultStr.value = AdbTools.getPkgPath(pkgName)
    }
}