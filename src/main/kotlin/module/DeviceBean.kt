package module

/**
 * @param deviceType 设备型号
 * @param deviceBrand 设备品牌
 * @param deviceStr 设备id
 */
data class DeviceBean(val deviceStr: String, val deviceId: String, var check: Boolean = false)

fun DeviceBean.copy(): DeviceBean {
    return DeviceBean(deviceStr, deviceId, check)
}