package viewmodel

import AdbTools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import module.DeviceBean

object InstallViewModel {

    // 设备列表
    private val mDeviceList = MutableStateFlow<List<DeviceBean>>(emptyList())
    val deviceList = mDeviceList.asStateFlow()
    private val scope = CoroutineScope(Dispatchers.Main)

    // 自动安装
    private val mAutoInstall = MutableStateFlow(false)
    val autoInstall = mAutoInstall.asStateFlow()

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

    // 选中
    fun selectDevice(index: Int, check: Boolean) {
        scope.launch {
            val list = mutableListOf<DeviceBean>()
            mDeviceList.value.forEach {
                list.add(it.copy())
            }
            list[index].check = check
            mDeviceList.emit(list)
        }
    }

    // 全选
    fun selectAllDevice() {
        scope.launch {
            var isAllSelect = true
            val list = mutableListOf<DeviceBean>()
            mDeviceList.value.forEach {
                if (!it.check) {
                    isAllSelect = false
                }
                list.add(it.copy())
            }
            list.forEach {
                it.check = !isAllSelect
            }
            mDeviceList.emit(list)
        }
    }

    fun install(fileName: String) {
        scope.launch(Dispatchers.IO) {
            mDeviceList.value.forEach {
                if (it.check) {
                    val result = AdbTools.installByDeviceId(
                        deviceId = it.deviceId,
                        pkgName = fileName
                    )
                    MainViewModel.setSnack("设备ID:${it.deviceId} 结果=$result")
                }
            }
        }
    }

    fun autoInstall(isAuto: Boolean) {
        scope.launch {
            mAutoInstall.emit(isAuto)
        }
    }

    fun killAdb(){
        scope.launch(Dispatchers.IO) {
            AdbTools.killAdb()
        }
    }
}