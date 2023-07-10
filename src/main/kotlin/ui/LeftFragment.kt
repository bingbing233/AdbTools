package ui

import MainViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import strings.titleFlash
import strings.titleInfo
import strings.titleMain
import strings.titleSetting
import styles.titleTextStyle

/**
 * 左边布局
 */
@Composable
fun LeftFragment(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MainFuncMenu()
        Spacer(modifier = Modifier.height(10.dp))
        MainDeviceInfo()
    }
}

// 左边菜单样式
@Composable
private fun MainFuncMenu() {
    val curFunc = MainViewModel.curFunc.collectAsState()
    val selectFunc = curFunc.value
    LargeCard(verticalPadding = 0.dp) {
        MenuItem(MainViewModel.FUNC_MAIN == selectFunc, Icons.Default.Home, titleMain) {
            MainViewModel.setCurFunc(
                MainViewModel.FUNC_MAIN
            )
        }
        MenuItem(MainViewModel.FUNC_FLASH == selectFunc, Icons.Default.Home, titleFlash) {
            MainViewModel.setCurFunc(
                MainViewModel.FUNC_FLASH
            )
        }
        MenuItem(MainViewModel.FUNC_INFO == selectFunc, Icons.Default.Info, titleInfo) {
            MainViewModel.setCurFunc(
                MainViewModel.FUNC_INFO
            )
        }
        MenuItem(MainViewModel.FUNC_SETTING == selectFunc, Icons.Default.Settings, titleSetting) {
            MainViewModel.setCurFunc(
                MainViewModel.FUNC_SETTING
            )
        }
        MenuItem(false, Icons.Default.Refresh, "刷新设备") {
            MainViewModel.refreshAllInfo()
        }
    }
}

// 设备信息
@Composable
private fun MainDeviceInfo() {
    val info = MainViewModel.deviceInfo.collectAsState()
    val battery = MainViewModel.batteryInfo.collectAsState()
    val cpu = MainViewModel.CPUInfo.collectAsState()
    val showLoading = MainViewModel.isShowInfoLoading.collectAsState()
    val state = rememberScrollState()
    Box(modifier = Modifier.fillMaxHeight()) {

        Column(modifier = Modifier.verticalScroll(state, true)) {
            Spacer(modifier = Modifier.height(8.dp))
            LargeCard(modifier = Modifier.clickable {
                MainViewModel.copyText(info.value)
            }, horizontalPadding = 8.dp) {
                Text(titleInfo, style = titleTextStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(info.value)
            }

            Spacer(modifier = Modifier.height(10.dp))
            LargeCard(
                modifier = Modifier.clickable {
                    MainViewModel.copyText(battery.value)
                },
                horizontalPadding = 8.dp
            ) {
                Text("电池信息", style = titleTextStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(battery.value)
            }

            Spacer(modifier = Modifier.height(10.dp))
            LargeCard(modifier = Modifier.clickable {
                MainViewModel.copyText(cpu.value)
            }, horizontalPadding = 8.dp) {
                Text("CPU信息", style = titleTextStyle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(cpu.value)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        VerticalScrollbar(adapter = rememberScrollbarAdapter(state), modifier = Modifier.align(Alignment.CenterEnd))

        if (showLoading.value) {
            Loading(modifier = Modifier.align(Alignment.Center))
        }
    }

}
