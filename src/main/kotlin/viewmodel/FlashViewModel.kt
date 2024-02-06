package viewmodel

import AdbTools
import java.awt.Desktop
import java.net.URL

object FlashViewModel {

    fun reboot() {
        AdbTools.reboot()
    }

    fun rebootRecovery() {
        AdbTools.rebootRecovery()
    }

    fun rebootFastBoot() {
        AdbTools.rebootFastboot()
    }

    fun openUrl(url:String) {
        Desktop.getDesktop().browse(URL(url).toURI())
    }
}