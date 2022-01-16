import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel {

    var resultStr = MutableStateFlow("命令输出区")

    fun reboot(){
        resultStr.value = AdbTools.reboot()
    }

    fun getAndroidVersion(){
        resultStr.value = AdbTools.getAndroidVersion()
    }

    fun getCurrentActivity(){
        resultStr.value = AdbTools.getCurrentActivity()
    }

    fun execute(adbCmd:String){
        resultStr.value = AdbTools.execute(adbCmd)
    }
}