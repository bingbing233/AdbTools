package viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import module.DeviceBean

object InfoViewModel {
    private val scope = CoroutineScope(Dispatchers.Main)

    // 设备信息
    private val mDeviceInfo = MutableStateFlow("")
    val deviceInfo = mDeviceInfo.asStateFlow()

    // 电池信息
    private val mBatteryInfo = MutableStateFlow("")
    val batteryInfo = mBatteryInfo.asStateFlow()

    // 电池信息
    private val mCPUInfo = MutableStateFlow("")
    val CPUInfo = mCPUInfo.asStateFlow()

    // info loading
    private val mIsShowInfoLoading = MutableStateFlow(false)
    val isShowInfoLoading = mIsShowInfoLoading.asStateFlow()

    // 设备列表
    private val mDeviceList = MutableStateFlow<List<DeviceBean>>(emptyList())
    val deviceList = mDeviceList.asStateFlow()

    init {
        scope.launch {
            deviceList.collect {
                if (it.isNotEmpty()) {
                    selectDevice(it.first().deviceId)
                }
            }
        }
    }

    // --------------- 命令相关方法 ---------------

    // 设备信息
    fun getDeviceInfo(deviceId: String) {
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getDeviceInfo(deviceId)
            mDeviceInfo.emit(info)
        }
    }

    // 电池信息
    fun getBattery(deviceId: String) {
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getBatteryInfo(deviceId)
            mBatteryInfo.emit(info)
        }
    }

    // cpu信息
    fun getCPUInfo(deviceId: String) {
        scope.launch(Dispatchers.IO) {
            val info = AdbTools.getCPUInfo(deviceId)
            mCPUInfo.emit(info)
        }
    }

    private fun showInfoLoading(show: Boolean) {
        scope.launch {
            mIsShowInfoLoading.emit(show)
        }
    }

    /**
     * 获取设备列表
     */
    fun getDeviceList() {
        scope.launch(Dispatchers.IO) {
            val devicesStr = AdbTools.getDeviceList()
            println(devicesStr)
            val deviceArr = devicesStr.split("\n")
            val list = mutableListOf<DeviceBean>()
            if (deviceArr.size > 1) { // 有设备
                for (i in 1 until deviceArr.size) {
                    val item = deviceArr[i]
                    if (item.trim().isNotEmpty()) {
                        val deviceId = item.split(" ").first()
                        list.add(DeviceBean(deviceArr[i], deviceId))
                    }
                }
            }
            mDeviceList.emit(list)
        }
    }

    fun selectDevice(deviceId: String) {
        getDeviceInfo(deviceId)
        getCPUInfo(deviceId)
        getBattery(deviceId)
    }
}